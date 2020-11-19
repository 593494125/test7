package com.springboot.service.impl.purchase;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.purchase.YwCgDdhzMapper;
import com.springboot.model.goods.DaSpSpDaJson;
import com.springboot.model.goods.DaSpSpdaMx;
import com.springboot.model.purchase.TInitImportCgywTxt;
import com.springboot.model.purchase.YwCgDdhz;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.purchase.TInitImportCgywTxtService;
import com.springboot.service.purchase.YwCgDdhzService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * (采购退货申请清单)采购清单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwCgDdhzServiceImpl extends ServiceImpl<YwCgDdhzMapper, YwCgDdhz> implements YwCgDdhzService {

    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private YwCgDdhzMapper ywCgDdhzMapper;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Resource
    private TInitImportCgywTxtService tInitImportCgywTxtService;

    @Override
    public Page<YwCgDdhz> getPage(String sixCode, Page<YwCgDdhz> page, Map<String, String> param) {
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        param.put("xxly","IPAD");
        List<YwCgDdhz> list=ywCgDdhzMapper.getList(param);
        List<YwCgDdhz> newList=new ArrayList<YwCgDdhz>();
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                YwCgDdhz bean=list.get(i);
                Date kdrq=bean.getKdrq();
                String newdate=dateFormat.format(kdrq);
                bean.setNewkdrq(newdate);
                if(StringUtils.isNotEmpty(bean.getCgfs())&&"1".equals(bean.getCgfs())){
                    bean.setCgfs("采购订单");
                }
                if(StringUtils.isNotEmpty(bean.getDjlx())&&"1".equals(bean.getDjlx())){
                    bean.setDjlx("采购入库单");
                }
                newList.add(bean);
            }
        }
        page.setRecords(newList);
        page.setCurrent(Integer.valueOf(param.get("pageNo")));
        page.setSize(Integer.valueOf(param.get("pageSize")));
        return page;
    }

    @Override
    public Boolean saveBean(String sixCode, YwCgDdhz ywCgDdhz) {
        Boolean returnFlag=false;
        List<TInitImportCgywTxt> list=new ArrayList<TInitImportCgywTxt>();
        //先删除原来临时数据
        boolean deleteFlag=tInitImportCgywTxtService.deleteOldData(sixCode,ywCgDdhz.getKdr(),SystemConstant.CG_TYPE);
        log.info("删除采购临时表状态："+deleteFlag);
//        if(deleteFlag==true){
            String tgflag=ywCgDdhz.getKdr()+JBDate.getNowDate(new Date().getTime());
            //临时表中批量插入数据
            String[] tmbhAndslArray=ywCgDdhz.getTmbhAndsl();
            if(tmbhAndslArray!=null&&tmbhAndslArray.length>0){
                int k=tmbhAndslArray.length;
                for (int i = 0; i < k; i++) {
                    int j=i+1;
                    String tmbhAndsl=tmbhAndslArray[i];
                    String[] array=tmbhAndsl.split(";");
                    if(array!=null&&array.length>0){
                        String tmbh=array[0];
                        String sl=array[1];
                        this.getTInitImportCgywList(sixCode,tmbh,sl,j,tgflag,ywCgDdhz,list);
                    }
                }
                //批量操作
                if(list!=null&&list.size()>0){
                    boolean flag=tInitImportCgywTxtService.saveBatch(sixCode,list);
                    if(flag==true){
                        //调用存储过程增加明细和汇总等其他表数据
//                        String msg=ywCgDdmxService.initCgDdmx(sixCode, SystemConstant.CG_TYPE,tgflag);
//                        if(StringUtils.isNotEmpty(msg)&&msg.startsWith("OK")){
//                            //修改采购汇总信息来源
//                            log.info("返回的pzh是："+msg);
//                            String[] pzhArray=msg.split("#");
//                            String pzh=pzhArray[1];
//                            if(StringUtils.isNotEmpty(pzh)){
//                                YwCgDdhz queryYwCgDdhz=new YwCgDdhz();
//                                queryYwCgDdhz.setXxly("IPAD");
//                                Integer count=ywCgDdhzMapper.update(queryYwCgDdhz,new EntityWrapper<YwCgDdhz>().eq("pzh",pzh));
//                                if(count!=null&&count>0){
//                                    returnFlag=true;
//                                }
//                            }
//                        }
                        log.info("返回的状态是："+returnFlag);
                    }
                }
            }
//        }

        return returnFlag;
    }
    public void getTInitImportCgywList(String sixCode,String tmbh,String sl,int j,String tgflag,YwCgDdhz ywCgDdhz,List<TInitImportCgywTxt> list){
        DaSpSpDaJson bean=null;
        String oldDaSpSpDaJson=redisUtil.get(sixCode,sixCode+"_"+tmbh+"_daspmx");
        if(StringUtils.isNotEmpty(oldDaSpSpDaJson)){
            bean= JSONObject.parseObject(oldDaSpSpDaJson, DaSpSpDaJson.class);
        }else{
            //通过条码编号查询商品明细
            DaSpSpdaMx queryDaSpSpdaMx=new DaSpSpdaMx();
            queryDaSpSpdaMx.setTmbh(tmbh);
            bean= daSpSpdaMxService.newfindByTmbh(sixCode,"0",queryDaSpSpdaMx);
        }

        if(bean!=null){
            TInitImportCgywTxt tInitImportCgyw=new TInitImportCgywTxt();
            tInitImportCgyw.setSpkh(bean.getSpkh());
            tInitImportCgyw.setKsmc(bean.getSpmc());
            tInitImportCgyw.setJldw(bean.getJldw());
            tInitImportCgyw.setYslsh(bean.getYslsh());
            tInitImportCgyw.setYsbh(bean.getYsbh());
            tInitImportCgyw.setYsmc(bean.getYsmc());
            tInitImportCgyw.setBxbh(StringUtils.isNotEmpty(bean.getBxbh())?bean.getBxbh():"-");

            tInitImportCgyw.setBxmc(bean.getBxmc());
            tInitImportCgyw.setYsmc(bean.getYsmc());
            tInitImportCgyw.setBxmc(StringUtils.isNotEmpty(bean.getBxmc())?bean.getBxmc():"-");
            tInitImportCgyw.setCmzbh(bean.getCmzbm());
            this.setCm(bean.getCmdmlwz(),sl,tInitImportCgyw);
            tInitImportCgyw.setHanghao(j);
            tInitImportCgyw.setTgflag(tgflag);
            tInitImportCgyw.setGysbh(ywCgDdhz.getGysbh());
            tInitImportCgyw.setYhbh(ywCgDdhz.getKdr());
            tInitImportCgyw.setYwflag(SystemConstant.CG_TYPE);
            tInitImportCgyw.setBz1(ywCgDdhz.getBmbh());
            tInitImportCgyw.setBz2(JBDate.getNowDate());
            tInitImportCgyw.setBz3("1");
            tInitImportCgyw.setLsj(Double.valueOf(bean.getLsjg()));
            tInitImportCgyw.setDpj(Double.valueOf(bean.getDpjg()));
            tInitImportCgyw.setJj(Double.valueOf(bean.getJhjg()));
            tInitImportCgyw.setZkl(Double.valueOf(bean.getJjzk()));
            tInitImportCgyw.setTmbh(tmbh);
            tInitImportCgyw.setSl(1);
            tInitImportCgyw.setTmfa("");
            list.add(tInitImportCgyw);
        }
    }
    public void setCm(String cm,String sl,TInitImportCgywTxt tInitImportCgyw){

        if(StringUtils.isNotEmpty(cm)&&"CM1".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm1(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm1(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM2".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm2(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm2(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM3".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm3(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm3(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM4".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm4(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm4(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM5".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm5(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm5(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM6".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm6(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm6(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM7".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm7(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm7(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM8".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm8(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm8(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM9".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm9(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm9(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM10".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm10(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm10(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM11".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm11(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm11(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM12".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm12(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm12(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM13".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm13(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm13(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM14".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm14(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm14(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM15".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm15(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm15(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM16".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm16(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm16(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM17".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm17(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm17(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM18".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm18(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm18(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM19".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm19(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm19(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM20".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm20(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm20(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM21".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm21(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm21(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM22".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm22(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm22(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM23".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm23(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm23(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM24".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm24(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm24(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM25".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm25(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm25(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM26".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm26(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm26(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM27".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm27(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm27(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM28".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm28(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm28(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM29".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm29(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm29(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM30".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm30(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm30(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM31".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm31(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm31(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM32".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm32(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm32(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM33".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm33(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm33(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM34".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm34(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm34(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM35".equalsIgnoreCase(cm)){
            tInitImportCgyw.setCm35(Integer.parseInt(sl));
        }else{
            tInitImportCgyw.setCm35(0);
        }

    }
//    @Override
//    public Boolean save(String sixCode, YwCgDdhz ywCgDdhz) {
//        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        Boolean flag=false;
//        String pzh=ywCgDdmxService.getCgPzh(sixCode,ywCgDdhz.getBmbh());//获取凭证号
//        //保存采购汇总明细数据
//        List<YwCgDdmx> list=ywCgDdhz.getYwCgDdmxList();
//        if(list!=null&&list.size()>0){
//            int k=list.size();
//            for (int i = 0; i <k ; i++) {
//                int j=i+1;
//                YwCgDdmx ywCgDdmx=list.get(i);
//                ywCgDdmx.setPzh(pzh);
//                ywCgDdmx.setMxdh(pzh+"_"+this.getNumber(j));
//                //初始化尺码代码值
//                this.setCmValue(ywCgDdmx);
//                this.setbxValue(ywCgDdmx);
//            }
//            //执行批量操作
//            flag=ywCgDdmxService.saveBatch(sixCode,list);
//            //统一更新价格体系
//            ywCgDdmxService.updateCgJg(sixCode,pzh);
//            //保存采购汇总数据
//            ywCgDdhz.setPzh(pzh);
//            try {
//                ywCgDdhz.setKdrq(DateFormat.parse(JBDate.getNowDate(new Date().getTime())));
//                ywCgDdhz.setXgsj(DateFormat.parse(JBDate.getNowDate(new Date().getTime())));
//                ywCgDdhz.setKdsj(DateFormat.parse(JBDate.getNowDate(new Date().getTime())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            //进价数量是所有采购明细数量加一起
//            Double jjnumber=ywCgDdmxService.getJjTotal(sixCode,pzh);
//            ywCgDdhz.setZjsl(jjnumber);
//            //总计金额zjje(所有明细金额之和)
//            Double zjje=ywCgDdmxService.getZjje(sixCode,pzh);
//            ywCgDdhz.setZjje(zjje);
//            //采购方式
//            if(StringUtils.isEmpty(ywCgDdhz.getCgfs())){
//                ywCgDdhz.setCgfs("1");
//            }
//            //单据类型
//            if(StringUtils.isEmpty(ywCgDdhz.getDjlx())){
//                ywCgDdhz.setDjlx("1");
//            }
//
//            ywCgDdhzMapper.insert(ywCgDdhz);
//            //保存采购清单状态数据（F：保存T：审核）
//            YwCgDdhzDjzt ywCgDdhzDjzt=new YwCgDdhzDjzt();
//            ywCgDdhzDjzt.setPzh(pzh);
//            ywCgDdhzDjzt.setDqzt("F");
//            ywCgDdhzDjzt.setCzr(ywCgDdhz.getKdr());
//            try {
//                ywCgDdhzDjzt.setCzsj(DateFormat.parse(JBDate.getNowDate(new Date().getTime())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            ywCgDdhzDjztService.save(sixCode,ywCgDdhzDjzt);
//        }
//        return flag;
//    }


//    public String getNumber(int j){
//        int newNumber = Integer.parseInt("000000") +j;
//        String number = String.format("%06d", newNumber);
//        return number;
//    }

//    public void setCmValue(YwCgDdmx ywCgDdmx){
//        if(ywCgDdmx.getCm1()==null){
//            ywCgDdmx.setCm1((double) 0);
//        }
//        if(ywCgDdmx.getCm2()==null){
//            ywCgDdmx.setCm2((double) 0);
//        }
//        if(ywCgDdmx.getCm3()==null){
//            ywCgDdmx.setCm3((double) 0);
//        }
//        if(ywCgDdmx.getCm4()==null){
//            ywCgDdmx.setCm4((double) 0);
//        }
//        if(ywCgDdmx.getCm5()==null){
//            ywCgDdmx.setCm5((double) 0);
//        }
//        if(ywCgDdmx.getCm6()==null){
//            ywCgDdmx.setCm6((double) 0);
//        }
//        if(ywCgDdmx.getCm7()==null){
//            ywCgDdmx.setCm7((double) 0);
//        }
//        if(ywCgDdmx.getCm8()==null){
//            ywCgDdmx.setCm8((double) 0);
//        }
//        if(ywCgDdmx.getCm9()==null){
//            ywCgDdmx.setCm9((double) 0);
//        }
//        if(ywCgDdmx.getCm10()==null){
//            ywCgDdmx.setCm10((double) 0);
//        }
//        if(ywCgDdmx.getCm11()==null){
//            ywCgDdmx.setCm11((double) 0);
//        }
//        if(ywCgDdmx.getCm12()==null){
//            ywCgDdmx.setCm12((double) 0);
//        }
//        if(ywCgDdmx.getCm13()==null){
//            ywCgDdmx.setCm13((double) 0);
//        }
//        if(ywCgDdmx.getCm14()==null){
//            ywCgDdmx.setCm14((double) 0);
//        }
//        if(ywCgDdmx.getCm15()==null){
//            ywCgDdmx.setCm15((double) 0);
//        }
//        if(ywCgDdmx.getCm16()==null){
//            ywCgDdmx.setCm16((double) 0);
//        }
//        if(ywCgDdmx.getCm17()==null){
//            ywCgDdmx.setCm17((double) 0);
//        }
//        if(ywCgDdmx.getCm18()==null){
//            ywCgDdmx.setCm18((double) 0);
//        }
//        if(ywCgDdmx.getCm19()==null){
//            ywCgDdmx.setCm19((double) 0);
//        }
//        if(ywCgDdmx.getCm20()==null){
//            ywCgDdmx.setCm20((double) 0);
//        }
//        if(ywCgDdmx.getCm21()==null){
//            ywCgDdmx.setCm21((double) 0);
//        }
//        if(ywCgDdmx.getCm22()==null){
//            ywCgDdmx.setCm22((double) 0);
//        }
//        if(ywCgDdmx.getCm23()==null){
//            ywCgDdmx.setCm23((double) 0);
//        }
//        if(ywCgDdmx.getCm24()==null){
//            ywCgDdmx.setCm24((double) 0);
//        }
//        if(ywCgDdmx.getCm25()==null){
//            ywCgDdmx.setCm25((double) 0);
//        }
//        if(ywCgDdmx.getCm26()==null){
//            ywCgDdmx.setCm26((double) 0);
//        }
//        if(ywCgDdmx.getCm27()==null){
//            ywCgDdmx.setCm27((double) 0);
//        }
//        if(ywCgDdmx.getCm28()==null){
//            ywCgDdmx.setCm28((double) 0);
//        }
//        if(ywCgDdmx.getCm29()==null){
//            ywCgDdmx.setCm29((double) 0);
//        }
//        if(ywCgDdmx.getCm30()==null){
//            ywCgDdmx.setCm30((double) 0);
//        }
//        if(ywCgDdmx.getCm31()==null){
//            ywCgDdmx.setCm31((double) 0);
//        }
//        if(ywCgDdmx.getCm32()==null){
//            ywCgDdmx.setCm32((double) 0);
//        }
//        if(ywCgDdmx.getCm33()==null){
//            ywCgDdmx.setCm33((double) 0);
//        }
//        if(ywCgDdmx.getCm34()==null){
//            ywCgDdmx.setCm34((double) 0);
//        }
//        if(ywCgDdmx.getCm35()==null){
//            ywCgDdmx.setCm35((double) 0);
//        }
//        if(ywCgDdmx.getCm1()==null){
//            ywCgDdmx.setCm1((double) 0);
//        }
//
//    }
//    public void setbxValue(YwCgDdmx ywCgDdmx){
//        if(StringUtils.isEmpty(ywCgDdmx.getBxbh())){
//            ywCgDdmx.setBxbh("-");
//        }
//        if(StringUtils.isEmpty(ywCgDdmx.getBxmc())){
//            ywCgDdmx.setBxmc("-");
//        }
//    }
}
