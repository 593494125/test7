package com.springboot.service.impl.stock;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisClient;
import com.springboot.common.SystemConstant;
import com.springboot.dao.stock.YwKcKcpcdHzMapper;
import com.springboot.model.AddReturnJson;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.goods.DaSpSpDaJson;
import com.springboot.model.stock.TInitImportTxtKcyw;
import com.springboot.model.stock.YwKcKcpcdHz;
import com.springboot.model.stock.YwKcKctzdHzQueryJson;
import com.springboot.service.department.DaBmdaService;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.error.TImportErrorListService;
import com.springboot.service.purchase.YwCgDdmxService;
import com.springboot.service.stock.TInitImportTxtKcywService;
import com.springboot.service.stock.YwKcDjztPcService;
import com.springboot.service.stock.YwKcKcpcdHzService;
import com.springboot.service.stock.YwKcKcpcdMxService;
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
 * @since 2020-05-18
 */
@Service
@Slf4j
public class YwKcKcpcdHzServiceImpl extends ServiceImpl<YwKcKcpcdHzMapper, YwKcKcpcdHz> implements YwKcKcpcdHzService {

    @Autowired
    private RedisClient redisClient;
    @Resource
    private YwKcKcpcdHzMapper ywKcKcpcdHzMapper;
    @Resource
    private DaYgdaService daYgdaService;
    @Resource
    private DaBmdaService daBmdaService;
    @Resource
    private TInitImportTxtKcywService tInitImportTxtKcywService;
    @Resource
    private YwCgDdmxService ywCgDdmxService;
    @Resource
    private YwKcKcpcdMxService ywKcKcpcdMxService;
    @Resource
    private YwKcDjztPcService ywKcDjztPcService;
    @Autowired
    private TImportErrorListService tImportErrorListService;

    @Override
    public Page<YwKcKcpcdHz> getPage(String sixCode, Page<YwKcKcpcdHz> page, YwKcKcpcdHz ywKcKcpcdHz, Map<String, String> param) {
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Integer pageNo=ywKcKcpcdHz.getPageNo();
        Integer pageSize=ywKcKcpcdHz.getPageSize();
        String yhbh=param.get("yhbh");
//        String djlx=ywKcKctzdHz.getDjlx();
        List<YwKcKcpcdHz> list=ywKcKcpcdHzMapper.getList(SystemConstant.XXLY,yhbh,"",pageNo,pageSize);
        List<YwKcKcpcdHz> newList=this.changeList(sixCode,yhbh,list);


        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }
    public List<YwKcKcpcdHz> changeList(String sixCode, String yhbh, List<YwKcKcpcdHz> list){
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        List<YwKcKcpcdHz> newList=new ArrayList<YwKcKcpcdHz>();
        if(list!=null&&list.size()>0){
            //获取系统下所有用户
            List<DaYgdaJson> daYgdaList= daYgdaService.findCgy(sixCode);
            //获取部门
            List<DaBmdaJson> daBmdaList= daBmdaService.findKcBmda(sixCode,yhbh);
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                YwKcKcpcdHz bean=list.get(i);
                Date tzrq=bean.getTzrq();
                Date kdsj=bean.getKdsj();
                String newtzrq=dateFormat1.format(tzrq);
                String newkdsj=dateFormat.format(kdsj);
                bean.setNewtzrq(newtzrq);
                bean.setNewkdsj(newkdsj);
                if(StringUtils.isNotEmpty(bean.getTzfs())&&"phtz".equals(bean.getTzfs())){
                    bean.setTzfs("盘货调整");
                }
                if(daYgdaList!=null&&daYgdaList.size()>0){
                    int n=daYgdaList.size();
                    for (int j = 0; j <n ; j++) {
                        DaYgdaJson daYgdaJson=daYgdaList.get(j);
                        if(daYgdaJson.getYgbh().equals(bean.getCzy())){
                            bean.setCgymc(daYgdaJson.getYgmc());
                            break;
                        }
                    }
                }
                if(daBmdaList!=null&&daBmdaList.size()>0){
                    int b=daBmdaList.size();
                    for (int j = 0; j <b ; j++) {
                        DaBmdaJson daBmdaJson=daBmdaList.get(j);
                        if(daBmdaJson.getBmbh().equals(bean.getTzbm())){
                            bean.setBmmc(daBmdaJson.getBmmc());
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
    public Page<YwKcKcpcdHz> getQueryPage(String sixCode, Page<YwKcKcpcdHz> page, YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson, Map<String, String> param) {
        Integer pageNo=ywKcKctzdHzQueryJson.getPageNo();
        Integer pageSize=ywKcKctzdHzQueryJson.getPageSize();
        String yhbh=param.get("yhbh");
//        String djlx=ywKcKctzdHzQueryJson.getDjlx();
        List<YwKcKcpcdHz> list=ywKcKcpcdHzMapper.getQueryPage(SystemConstant.XXLY,yhbh,ywKcKctzdHzQueryJson.getStartTime(),ywKcKctzdHzQueryJson.getEndTime(),
                ywKcKctzdHzQueryJson.getBmbh(),ywKcKctzdHzQueryJson.getCgy(),ywKcKctzdHzQueryJson.getPzh(),ywKcKctzdHzQueryJson.getSgdj(),pageNo,pageSize);

        List<YwKcKcpcdHz> newList=this.changeList(sixCode,yhbh,list);
        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }

    @Override
    public AddReturnJson saveBean(String sixCode, YwKcKcpcdHz ywKcKcpcdHz) {
        Map<String,List<TImportErrorList>> map=new HashMap<String,List<TImportErrorList>>();
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        Integer returnFlag=0;
        Boolean deleteFlag=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AddReturnJson addReturnJson=new AddReturnJson();
        List<TInitImportTxtKcyw> list=new ArrayList<TInitImportTxtKcyw>();
        //先删除原来临时数据
        deleteFlag= tInitImportTxtKcywService.deleteOldData(sixCode,ywKcKcpcdHz.getCzy(),SystemConstant.KT_TYPE);
        log.info("删除采购临时表状态："+deleteFlag);

        long endd = System.currentTimeMillis();
        log.info("删除采购临时表状态，耗时：" + (endd - start) + "毫秒");
        String tgflag=ywKcKcpcdHz.getCzy()+ JBDate.getNowDate(new Date().getTime());
        //临时表中批量插入数据
        String[] tmbhAndslArray=ywKcKcpcdHz.getTmbhAndsl();
        if(tmbhAndslArray!=null&&tmbhAndslArray.length>0){
            int k=tmbhAndslArray.length;
            TInitImportTxtKcyw tInitImportKcyw1 = new TInitImportTxtKcyw();
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
                    getTInitImportKcywList(tmbh, sl, j, tgflag, ywKcKcpcdHz, list, tInitImportKcyw1, dateFormat, bean);


                }
            }

            long endq = System.currentTimeMillis();
            log.info("构建实体类，耗时：" + (endq - endd) + "毫秒");
            //批量操作
            if(list!=null&&list.size()>0){
                boolean flag=tInitImportTxtKcywService.saveBatch(sixCode,list);
                long end = System.currentTimeMillis();
                log.info("完成保存，耗时：" + (end - endq) + "毫秒");
                if(flag==true){
                    //调用存储过程增加明细和汇总等其他表数据
                    String msg= ywCgDdmxService.initCgDdmx(sixCode,SystemConstant.KP_TYPE,tgflag);
                    log.info("执行存储过程返回的msg：" + msg);
                    long end1 = System.currentTimeMillis();
                    log.info("执行存储过程，耗时：" + (end1 - end) + "毫秒");
                    if(StringUtils.isNotEmpty(msg)&&msg.startsWith("OK")){
                        //修改采购汇总信息来源
                        log.info("返回的pzh是："+msg);
                        String[] pzhArray=msg.split("#");
                        String pzh=pzhArray[1];
                        if(StringUtils.isNotEmpty(pzh)){
                            YwKcKcpcdHz queryYwKcKcpcdHz=new YwKcKcpcdHz();
                            queryYwKcKcpcdHz.setXxly(SystemConstant.XXLY);
                            queryYwKcKcpcdHz.setBz(ywKcKcpcdHz.getBz());
                            Integer count=ywKcKcpcdHzMapper.update(queryYwKcKcpcdHz,new EntityWrapper<YwKcKcpcdHz>().eq("pzh",pzh));
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
                    addReturnJson.setMsgList(map);
                }
            }else{
                addReturnJson.setFlag(2);
                List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                TImportErrorList tImportError=new TImportErrorList();
                tImportError.setMsg("条码查询不到!");
                tImportErrorList.add(tImportError);
                map.put("msgList",tImportErrorList);
                addReturnJson.setMsgList(map);
            }
        }else{
            addReturnJson.setFlag(2);
            List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
            TImportErrorList tImportError=new TImportErrorList();
            tImportError.setMsg("条码和数量参数不正确!");
            tImportErrorList.add(tImportError);
            map.put("msgList",tImportErrorList);
            addReturnJson.setMsgList(map);
        }
        long end3 = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end3 - start) + "毫秒");
        return addReturnJson;
    }
    public void getTInitImportKcywList(String tmbh,String sl,int j,String tgflag,YwKcKcpcdHz ywKcKcpcdHz,List<TInitImportTxtKcyw> list, TInitImportTxtKcyw tInitImportKcyw1, SimpleDateFormat dateFormat, DaSpSpDaJson bean){

        if(bean!=null){
            TInitImportTxtKcyw tInitImportKcyw=(TInitImportTxtKcyw) tInitImportKcyw1.clone();
            tInitImportKcyw.setSpkh(bean.getSpkh());
            tInitImportKcyw.setKsmc(bean.getSpmc());
            tInitImportKcyw.setJldw(bean.getJldw());
            tInitImportKcyw.setYslsh(bean.getYslsh());
            tInitImportKcyw.setYsbh(bean.getYsbh());
            tInitImportKcyw.setYsmc(bean.getYsmc());
            tInitImportKcyw.setBxbh(StringUtils.isNotEmpty(bean.getBxbh())?bean.getBxbh():"-");

            tInitImportKcyw.setBxmc(bean.getBxmc());
            tInitImportKcyw.setYsmc(bean.getYsmc());
            tInitImportKcyw.setBxmc(StringUtils.isNotEmpty(bean.getBxmc())?bean.getBxmc():"-");
            tInitImportKcyw.setCmzbh(bean.getCmzbm());
            this.setCm(bean.getCmdmlwz(),sl,tInitImportKcyw);
            tInitImportKcyw.setHanghao(j);
            tInitImportKcyw.setTgflag(tgflag);
            tInitImportKcyw.setGysbh(ywKcKcpcdHz.getSgdj()+"#"+ywKcKcpcdHz.getCzy());
            tInitImportKcyw.setYhbh(ywKcKcpcdHz.getCzy());

            tInitImportKcyw.setYwflag(SystemConstant.KP_TYPE);
            tInitImportKcyw.setBz3("phtz");

            tInitImportKcyw.setBz4("");
            tInitImportKcyw.setBz1(ywKcKcpcdHz.getTzbm());
            tInitImportKcyw.setBz2(dateFormat.format(ywKcKcpcdHz.getTzrq()));

            tInitImportKcyw.setLsj(Double.valueOf(bean.getLsjg()));
            tInitImportKcyw.setDpj(Double.valueOf(bean.getDpjg()));
            tInitImportKcyw.setJj(Double.valueOf(bean.getJhjg()));
            tInitImportKcyw.setZkl(Double.valueOf("0.00"));
            tInitImportKcyw.setTmbh(tmbh);
            tInitImportKcyw.setSl(Integer.parseInt(sl));
            tInitImportKcyw.setBzxx(ywKcKcpcdHz.getBz());
            list.add(tInitImportKcyw);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPzh(String sixCode, String pzh) {
        Integer flag=0;
        Integer flag1=0;
        Integer flag2=0;
        //删除采购入库单汇总同时删除关联表
        flag=ywKcKcpcdHzMapper.delete(pzh);
        log.info("删除汇总："+flag);
        //批量删除采购入库单明细
        if(flag>0) {
            flag1 = ywKcKcpcdMxService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除明细：" + flag1);
            //删除采购入库单状态表
            flag2 = ywKcDjztPcService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除状态：" + flag2);
        }
        return flag;
    }

    @Override
    public List<String> getPzh(String sixCode, Map<String, String> param) {
        String yhbh=param.get("yhbh");
        String djlx=param.get("djlx");
        List<String> list=ywKcKcpcdHzMapper.getPzh(SystemConstant.XXLY,yhbh,djlx);
        return list;
    }

    @Override
    public YwKcKcpcdHz findByPzh(String sixCode, String pzh,String sgdj) {
        YwKcKcpcdHz bean =ywKcKcpcdHzMapper.findByPzh(pzh,sgdj);
        return bean;
    }
    @Override
    public YwKcKcpcdHz findSyByPzh(String sixCode, String pzh) {
        YwKcKcpcdHz bean =ywKcKcpcdHzMapper.findSyByPzh(pzh);
        return bean;
    }

    public void setCm(String cm, String sl, TInitImportTxtKcyw tInitImportKcyw){

        if(StringUtils.isNotEmpty(cm)&&"CM1".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm1(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm1(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM2".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm2(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm2(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM3".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm3(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm3(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM4".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm4(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm4(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM5".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm5(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm5(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM6".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm6(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm6(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM7".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm7(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm7(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM8".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm8(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm8(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM9".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm9(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm9(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM10".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm10(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm10(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM11".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm11(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm11(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM12".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm12(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm12(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM13".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm13(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm13(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM14".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm14(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm14(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM15".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm15(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm15(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM16".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm16(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm16(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM17".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm17(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm17(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM18".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm18(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm18(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM19".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm19(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm19(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM20".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm20(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm20(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM21".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm21(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm21(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM22".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm22(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm22(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM23".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm23(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm23(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM24".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm24(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm24(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM25".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm25(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm25(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM26".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm26(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm26(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM27".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm27(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm27(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM28".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm28(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm28(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM29".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm29(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm29(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM30".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm30(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm30(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM31".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm31(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm31(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM32".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm32(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm32(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM33".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm33(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm33(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM34".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm34(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm34(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM35".equalsIgnoreCase(cm)){
            tInitImportKcyw.setCm35(Integer.parseInt(sl));
        }else{
            tInitImportKcyw.setCm35(0);
        }

    }
}
