package com.springboot.service.impl.transfer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisClient;
import com.springboot.common.SystemConstant;
import com.springboot.dao.transfer.YwDbRkdhzMapper;
import com.springboot.model.AddReturnJson;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.goods.DaSpSpDaJson;
import com.springboot.model.transfer.TInitImportDbywTxt;
import com.springboot.model.transfer.YwDbRkdhz;
import com.springboot.model.transfer.YwDbRkdhzQueryJson;
import com.springboot.service.department.DaBmdaService;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.error.TImportErrorListService;
import com.springboot.service.purchase.YwCgDdmxService;
import com.springboot.service.transfer.TInitImportDbywTxtService;
import com.springboot.service.transfer.YwDbDdhzDjztService;
import com.springboot.service.transfer.YwDbRkdhzService;
import com.springboot.service.transfer.YwDbRkdmxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
@Service
@Slf4j
public class YwDbRkdhzServiceImpl extends ServiceImpl<YwDbRkdhzMapper, YwDbRkdhz> implements YwDbRkdhzService {


    @Autowired
    private RedisClient redisClient;
    @Resource
    private YwDbRkdhzMapper ywDbRkdhzMapper;
    @Resource
    private DaYgdaService daYgdaService;
    @Resource
    private DaBmdaService daBmdaService;
    @Resource
    private TInitImportDbywTxtService tInitImportDbywTxtService;
    @Resource
    private YwDbRkdmxService ywDbRkdmxService;
    @Resource
    private YwDbDdhzDjztService ywDbDdhzDjztService;
    @Resource
    private YwCgDdmxService ywCgDdmxService;
    @Resource
    private TImportErrorListService tImportErrorListService;

    @Override
    public Page<YwDbRkdhz> getPage(String sixCode, Page<YwDbRkdhz> page, YwDbRkdhz ywDbRkdhz, Map<String, String> param) {
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Integer pageNo=ywDbRkdhz.getPageNo();
        Integer pageSize=ywDbRkdhz.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywDbRkdhz.getDjlx();
        List<YwDbRkdhz> list=ywDbRkdhzMapper.getList(SystemConstant.XXLY,yhbh,djlx,pageNo,pageSize);
        List<YwDbRkdhz> newList=this.changeList(sixCode,yhbh,list);


        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }
    public List<YwDbRkdhz> changeList(String sixCode, String yhbh, List<YwDbRkdhz> list){
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        List<YwDbRkdhz> newList=new ArrayList<YwDbRkdhz>();
        if(list!=null&&list.size()>0){
            //获取系统下所有用户
            List<DaYgdaJson> daYgdaList= daYgdaService.findCgy(sixCode);
            //获取调拨出部门
            List<DaBmdaJson> dacBmdaList= daBmdaService.findDbcBmda(sixCode,yhbh);
            //获取调拨入部门
            List<DaBmdaJson> darBmdaList= daBmdaService.findDbrBmda(sixCode,yhbh);
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                YwDbRkdhz bean=list.get(i);
                Date kdrq=bean.getKdrq();
                Date kdsj=bean.getKdsj();
                String newkdrq=dateFormat1.format(kdrq);
                String newkdsj=dateFormat.format(kdsj);
                bean.setNewkdrq(newkdrq);
                bean.setNewkdsj(newkdsj);
                if(StringUtils.isNotEmpty(bean.getCgfs())&&"CKDBCK".equals(bean.getCgfs())){
                    bean.setCgfs("仓库调拨出库");
                }
                if(StringUtils.isNotEmpty(bean.getDjlx())&&"1".equals(bean.getDjlx())){
                    bean.setDjlx("仓库调拨出库单");
                }
                if(daYgdaList!=null&&daYgdaList.size()>0){
                    int n=daYgdaList.size();
                    for (int j = 0; j <n ; j++) {
                        DaYgdaJson daYgdaJson=daYgdaList.get(j);
                        if(daYgdaJson.getYgbh().equals(bean.getCgy())){
                            bean.setCgymc(daYgdaJson.getYgmc());
                            break;
                        }
                    }
                }
                if(darBmdaList!=null&&darBmdaList.size()>0){
                    int b=darBmdaList.size();
                    for (int j = 0; j <b ; j++) {
                        DaBmdaJson daBmdaJson=darBmdaList.get(j);
                        if(daBmdaJson.getBmbh().equals(bean.getBmbh1())){
                            bean.setBmbh1mc(daBmdaJson.getBmmc());
                            break;
                        }
                    }
                }
                if(dacBmdaList!=null&&dacBmdaList.size()>0){
                    int b=dacBmdaList.size();
                    for (int j = 0; j <b ; j++) {
                        DaBmdaJson daBmdaJson=dacBmdaList.get(j);
                        if(daBmdaJson.getBmbh().equals(bean.getBmbh2())){
                            bean.setBmbh2mc(daBmdaJson.getBmmc());
                            break;
                        }
                    }

                }
                newList.add(bean);
            }
        }
        return newList;
    }
    @Override
    public Page<YwDbRkdhz> getQueryPage(String sixCode, Page<YwDbRkdhz> page, YwDbRkdhzQueryJson ywDbRkdhzQueryJson, Map<String, String> param) {
        Integer pageNo=ywDbRkdhzQueryJson.getPageNo();
        Integer pageSize=ywDbRkdhzQueryJson.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywDbRkdhzQueryJson.getDjlx();
        List<YwDbRkdhz> list=ywDbRkdhzMapper.getQueryPage(SystemConstant.XXLY,yhbh,ywDbRkdhzQueryJson.getStartTime(),ywDbRkdhzQueryJson.getEndTime(),
                ywDbRkdhzQueryJson.getBmbh1(),ywDbRkdhzQueryJson.getBmbh2(),ywDbRkdhzQueryJson.getCgy(),ywDbRkdhzQueryJson.getPzh(),djlx,pageNo,pageSize);

        List<YwDbRkdhz> newList=this.changeList(sixCode,yhbh,list);
        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }

    @Override
    public AddReturnJson saveBean(String sixCode, YwDbRkdhz ywDbRkdhz) {
        Map<String,List<TImportErrorList>> map=new HashMap<String,List<TImportErrorList>>();
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        Integer returnFlag=0;
        Boolean deleteFlag=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AddReturnJson addReturnJson=new AddReturnJson();
        List<TInitImportDbywTxt> list=new ArrayList<TInitImportDbywTxt>();
        //先删除原来临时数据
        if(StringUtils.isNotEmpty(ywDbRkdhz.getDjlx())&&"1".equals(ywDbRkdhz.getDjlx())){
            deleteFlag=tInitImportDbywTxtService.deleteOldData(sixCode,ywDbRkdhz.getCgy(),SystemConstant.DC_TYPE);
        }
        log.info("删除采购临时表状态："+deleteFlag);
        long endd = System.currentTimeMillis();
        log.info("删除采购临时表状态，耗时：" + (endd - start) + "毫秒");
        String tgflag=ywDbRkdhz.getCgy()+ JBDate.getNowDate(new Date().getTime());
        //临时表中批量插入数据
        String[] tmbhAndslArray=ywDbRkdhz.getTmbhAndsl();
        if(tmbhAndslArray!=null&&tmbhAndslArray.length>0){
            int k=tmbhAndslArray.length;
            TInitImportDbywTxt tInitImportDbyw1=new TInitImportDbywTxt();
            for (int i = 0; i < k; i++) {
                int j=i+1;
                String tmbhAndsl=tmbhAndslArray[i];
                String[] array=tmbhAndsl.split(";");
                if(array!=null&&array.length>0){
                    String tmbh=array[0];
                    String sl=array[1];
                    //通过管道获取
                    String key = sixCode+"_"+tmbh+"_daspmx";
                    DaSpSpDaJson bean=redisClient.get(sixCode,key,DaSpSpDaJson.class);
                    getTInitImportDbywList(tmbh,sl,j,tgflag,ywDbRkdhz,list,tInitImportDbyw1,dateFormat,bean);


                }
            }
            long endq = System.currentTimeMillis();
            log.info("构建实体类，耗时：" + (endq - endd) + "毫秒");
            //批量操作
            if(list!=null&&list.size()>0){
                boolean flag=tInitImportDbywTxtService.saveBatch(sixCode,list);
                long end = System.currentTimeMillis();
                log.info("完成保存，耗时：" + (end - endq) + "毫秒");
                if(flag==true){
                    //调用存储过程增加明细和汇总等其他表数据
                    String msg= ywCgDdmxService.initCgDdmx(sixCode,SystemConstant.DC_TYPE,tgflag);
                    log.info("执行存储过程返回的msg：" + msg);
                    long end1 = System.currentTimeMillis();
                    log.info("执行存储过程，耗时：" + (end1 - end) + "毫秒");
                    if(StringUtils.isNotEmpty(msg)&&msg.startsWith("OK")){
                        //修改采购汇总信息来源
                        log.info("返回的pzh是："+msg);
                        String[] pzhArray=msg.split("#");
                        String pzh=pzhArray[1];
                        if(StringUtils.isNotEmpty(pzh)){
                            YwDbRkdhz queryYwDbRkdhz=new YwDbRkdhz();
                            queryYwDbRkdhz.setXxly(SystemConstant.XXLY);
                            queryYwDbRkdhz.setBzxx(ywDbRkdhz.getBzxx());
                            Integer count=ywDbRkdhzMapper.update(queryYwDbRkdhz,new EntityWrapper<YwDbRkdhz>().eq("pzh",pzh));
                            if(count!=null&&count>0){
                                returnFlag=1;
                                addReturnJson.setFlag(1);
                                addReturnJson.setMsg(msg);
                            }
                        }else{
                            addReturnJson.setFlag(2);
                            List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                            TImportErrorList tImportError=new TImportErrorList();
                            tImportError.setMsg("凭证号为空！");
                            tImportErrorList.add(tImportError);
                            map.put("msgList",tImportErrorList);
                            addReturnJson.setMsgList(map);
                        }
                    }else{
                        addReturnJson.setFlag(2);
                        //查询错误记录
                        List<TImportErrorList> tImportErrorList=tImportErrorListService.getList(sixCode,tgflag);
                        map.put("msgList",tImportErrorList);
                        addReturnJson.setMsgList(map);
                    }
                    log.info("返回的状态是："+returnFlag);
                }else{
                    addReturnJson.setFlag(2);
                    List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                    TImportErrorList tImportError=new TImportErrorList();
                    tImportError.setMsg("临时表保存失败!");
                    tImportErrorList.add(tImportError);
                    map.put("msgList",tImportErrorList);
                }
            }else{
                addReturnJson.setFlag(2);
                List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                TImportErrorList tImportError=new TImportErrorList();
                tImportError.setMsg("条码查询不到!");
                tImportErrorList.add(tImportError);
                map.put("msgList",tImportErrorList);
            }
        }else{
            addReturnJson.setFlag(2);
            List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
            TImportErrorList tImportError=new TImportErrorList();
            tImportError.setMsg("条码和数量参数参数不正确!");
            tImportErrorList.add(tImportError);
            map.put("msgList",tImportErrorList);
        }
        long end3 = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end3 - start) + "毫秒");


        return addReturnJson;
    }
    public void getTInitImportDbywList(String tmbh,String sl,int j,String tgflag,YwDbRkdhz ywDbRkdhz,List<TInitImportDbywTxt> list,TInitImportDbywTxt tInitImportDbywTxt1,SimpleDateFormat dateFormat,DaSpSpDaJson bean){

        if(bean!=null){
            TInitImportDbywTxt tInitImportDbyw= (TInitImportDbywTxt) tInitImportDbywTxt1.clone();
            tInitImportDbyw.setSpkh(bean.getSpkh());
            tInitImportDbyw.setKsmc(bean.getSpmc());
            tInitImportDbyw.setJldw(bean.getJldw());
            tInitImportDbyw.setYslsh(bean.getYslsh());
            tInitImportDbyw.setYsbh(bean.getYsbh());
            tInitImportDbyw.setYsmc(bean.getYsmc());
            tInitImportDbyw.setBxbh(StringUtils.isNotEmpty(bean.getBxbh())?bean.getBxbh():"-");

            tInitImportDbyw.setBxmc(bean.getBxmc());
            tInitImportDbyw.setYsmc(bean.getYsmc());
            tInitImportDbyw.setBxmc(StringUtils.isNotEmpty(bean.getBxmc())?bean.getBxmc():"-");
            tInitImportDbyw.setCmzbh(bean.getCmzbm());
            this.setCm(bean.getCmdmlwz(),sl,tInitImportDbyw);
            tInitImportDbyw.setHanghao(j);
            tInitImportDbyw.setTgflag(tgflag);
            tInitImportDbyw.setGysbh(ywDbRkdhz.getBmbh1());
            tInitImportDbyw.setYhbh(ywDbRkdhz.getCgy());
            if(StringUtils.isNotEmpty(ywDbRkdhz.getDjlx())&&"1".equals(ywDbRkdhz.getDjlx())){
                tInitImportDbyw.setYwflag(SystemConstant.DC_TYPE);
                tInitImportDbyw.setBz3("CKDBCK");
            }
            tInitImportDbyw.setBz1(ywDbRkdhz.getBmbh2());
            tInitImportDbyw.setBz2(dateFormat.format(ywDbRkdhz.getKdrq()));

            tInitImportDbyw.setLsj(Double.valueOf(bean.getLsjg()));
            tInitImportDbyw.setDpj(Double.valueOf(bean.getDpjg()));
            tInitImportDbyw.setJj(Double.valueOf(bean.getJhjg()));
            tInitImportDbyw.setZkl(Double.valueOf("0.00"));
            tInitImportDbyw.setTmbh(tmbh);
            tInitImportDbyw.setSl(Integer.parseInt(sl));
            tInitImportDbyw.setTmfa("");
            tInitImportDbyw.setBzxx(ywDbRkdhz.getBzxx());
            list.add(tInitImportDbyw);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPzh(String sixCode, String pzh) {
        Integer flag=0;
        Integer flag1=0;
        Integer flag2=0;
        //删除采购入库单汇总同时删除关联表
        flag=ywDbRkdhzMapper.delete(pzh);
        log.info("删除汇总："+flag);
        //批量删除采购入库单明细
        if(flag>0) {
            flag1 = ywDbRkdmxService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除明细：" + flag1);
            //删除采购入库单状态表
            flag2 = ywDbDdhzDjztService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除状态：" + flag2);
        }
        return flag;
    }

    @Override
    public List<String> getPzh(String sixCode, Map<String, String> param) {
        String yhbh=param.get("yhbh");
        String djlx=param.get("djlx");
        List<String> list=ywDbRkdhzMapper.getPzh(SystemConstant.XXLY,yhbh,djlx);
        return list;
    }

    @Override
    public YwDbRkdhz findByPzh(String sixCode, String pzh) {
        YwDbRkdhz bean =ywDbRkdhzMapper.findByPzh(pzh);
        return bean;
    }

    public void setCm(String cm, String sl, TInitImportDbywTxt tInitImportDbyw){

        if(StringUtils.isNotEmpty(cm)&&"CM1".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm1(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm1(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM2".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm2(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm2(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM3".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm3(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm3(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM4".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm4(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm4(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM5".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm5(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm5(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM6".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm6(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm6(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM7".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm7(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm7(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM8".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm8(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm8(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM9".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm9(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm9(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM10".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm10(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm10(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM11".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm11(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm11(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM12".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm12(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm12(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM13".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm13(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm13(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM14".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm14(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm14(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM15".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm15(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm15(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM16".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm16(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm16(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM17".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm17(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm17(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM18".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm18(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm18(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM19".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm19(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm19(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM20".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm20(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm20(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM21".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm21(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm21(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM22".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm22(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm22(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM23".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm23(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm23(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM24".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm24(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm24(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM25".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm25(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm25(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM26".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm26(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm26(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM27".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm27(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm27(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM28".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm28(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm28(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM29".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm29(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm29(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM30".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm30(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm30(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM31".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm31(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm31(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM32".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm32(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm32(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM33".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm33(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm33(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM34".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm34(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm34(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM35".equalsIgnoreCase(cm)){
            tInitImportDbyw.setCm35(Integer.parseInt(sl));
        }else{
            tInitImportDbyw.setCm35(0);
        }

    }
}
