package com.springboot.service.impl.purchase;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisClient;
import com.springboot.common.SystemConstant;
import com.springboot.dao.purchase.YwCgRkdhzMapper;
import com.springboot.model.AddReturnJson;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.goods.DaSpSpDaJson;
import com.springboot.model.purchase.TInitImportCgywTxt;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.model.purchase.YwCgRkdhzQueryJson;
import com.springboot.model.supplier.DaGysdaJson;
import com.springboot.service.department.DaBmdaService;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.error.TImportErrorListService;
import com.springboot.service.purchase.*;
import com.springboot.service.supplier.DaGysdaService;
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
 * @since 2020-05-06
 * @since SUPPORTS.REQUIRES_NEW.REQUIRED.NOT_SUPPORTED.NEVER.NESTED.MANDATORY
 */
@Service
//@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwCgRkdhzServiceImpl extends ServiceImpl<YwCgRkdhzMapper, YwCgRkdhz> implements YwCgRkdhzService {

    @Autowired
    private RedisClient redisClient;
    @Resource
    private YwCgRkdhzMapper ywCgRkdhzMapper;
    @Resource
    private YwCgDdmxService ywCgDdmxService;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private YwCgDdhzDjztService ywCgDdhzDjztService;
    @Resource
    private TInitImportCgywTxtService tInitImportCgywTxtService;
    @Resource
    private DaYgdaService daYgdaService;
    @Resource
    private DaBmdaService daBmdaService;
    @Resource
    private DaGysdaService daGysdaService;
    @Resource
    private TImportErrorListService tImportErrorListService;
    @Override
    public Page<YwCgRkdhz> getPage(String sixCode, Page<YwCgRkdhz> page, YwCgRkdhz ywCgRkdhz,Map<String,String> param) {

        Integer pageNo=ywCgRkdhz.getPageNo();
        Integer pageSize=ywCgRkdhz.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywCgRkdhz.getDjlx();
        List<YwCgRkdhz> list=ywCgRkdhzMapper.getList(SystemConstant.XXLY,yhbh,djlx,pageNo,pageSize);
        List<YwCgRkdhz> newList=this.changeList(sixCode,yhbh,list);

        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }
    public List<YwCgRkdhz> changeList(String sixCode,String yhbh,List<YwCgRkdhz> list){
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        List<YwCgRkdhz> newList=new ArrayList<YwCgRkdhz>();
        if(list!=null&&list.size()>0){
            //获取系统下所有用户
            List<DaYgdaJson> daYgdaList= daYgdaService.findCgy(sixCode);
            //获取部门
            List<DaBmdaJson> daBmdaList= daBmdaService.findCgBmda(sixCode,yhbh);
            //获取供应商
            List<DaGysdaJson> daGysdaList= daGysdaService.findByDaGysda(sixCode,yhbh);
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                YwCgRkdhz bean=list.get(i);
                Date kdrq=bean.getKdrq();
                Date kdsj=bean.getKdsj();
                String newkdrq=dateFormat1.format(kdrq);
                String newkdsj=dateFormat.format(kdsj);
                bean.setNewkdrq(newkdrq);
                bean.setNewkdsj(newkdsj);
                if(StringUtils.isNotEmpty(bean.getCgfs())&&"7".equals(bean.getCgfs())){
                    bean.setCgfs("采购入库");
                }else if(StringUtils.isNotEmpty(bean.getCgfs())&&"5".equals(bean.getCgfs())){
                    bean.setCgfs("采购退库");
                }
                if(StringUtils.isNotEmpty(bean.getDjlx())&&"1".equals(bean.getDjlx())){
                    bean.setDjlx("采购入库单");
                }else if(StringUtils.isNotEmpty(bean.getDjlx())&&"2".equals(bean.getDjlx())){
                    bean.setDjlx("采购退货单");
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
                if(daBmdaList!=null&&daBmdaList.size()>0){
                    int b=daBmdaList.size();
                    for (int j = 0; j <b ; j++) {
                        DaBmdaJson daBmdaJson=daBmdaList.get(j);
                        if(daBmdaJson.getBmbh().equals(bean.getBmbh())){
                            bean.setBmmc(daBmdaJson.getBmmc());
                            break;
                        }
                    }
                }
                if(daGysdaList!=null&&daGysdaList.size()>0){
                    int b=daGysdaList.size();
                    for (int j = 0; j <b ; j++) {
                        DaGysdaJson daGysdaJson=daGysdaList.get(j);
                        if(daGysdaJson.getGysbh().equals(bean.getGysbh())){
                            bean.setGysmc(daGysdaJson.getGysmc());
                            break;
                        }
                    }
                }
                if(StringUtils.isBlank(bean.getJzbz())){
                    bean.setJzbz("0");
                }
                newList.add(bean);
            }
        }
        return newList;
    }
    @Override
    public Page<YwCgRkdhz> getQueryPage(String sixCode, Page<YwCgRkdhz> page, YwCgRkdhzQueryJson ywCgRkdhzQueryJson, Map<String, String> param) {
        Integer pageNo=ywCgRkdhzQueryJson.getPageNo();
        Integer pageSize=ywCgRkdhzQueryJson.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywCgRkdhzQueryJson.getDjlx();
        List<YwCgRkdhz> list=ywCgRkdhzMapper.getQueryPage(SystemConstant.XXLY,yhbh,ywCgRkdhzQueryJson.getStartTime(),ywCgRkdhzQueryJson.getEndTime(),
                ywCgRkdhzQueryJson.getBmbh(),ywCgRkdhzQueryJson.getGysbh(),ywCgRkdhzQueryJson.getCgy(),ywCgRkdhzQueryJson.getPzh(),djlx,pageNo,pageSize);

        List<YwCgRkdhz> newList=this.changeList(sixCode,yhbh,list);
        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }

    @Override
    public AddReturnJson saveBean(String sixCode, YwCgRkdhz ywCgRkdhz){
        Map<String,List<TImportErrorList>> map=new HashMap<String,List<TImportErrorList>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AddReturnJson addReturnJson=new AddReturnJson();

        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        Integer returnFlag=0;
        Boolean deleteFlag=false;
        List<TInitImportCgywTxt> list=new ArrayList<TInitImportCgywTxt>();
        //先删除原来临时数据
        if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"1".equals(ywCgRkdhz.getDjlx())){
             deleteFlag=tInitImportCgywTxtService.deleteOldData(sixCode,ywCgRkdhz.getCgy(),SystemConstant.CR_TYPE);
        }else if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"2".equals(ywCgRkdhz.getDjlx())){
             deleteFlag=tInitImportCgywTxtService.deleteOldData(sixCode,ywCgRkdhz.getCgy(),SystemConstant.CT_TYPE);
        }

        log.info("删除采购临时表状态："+deleteFlag);
        long endd = System.currentTimeMillis();
        log.info("删除采购临时表状态，耗时：" + (endd - start) + "毫秒");
        String tgflag=ywCgRkdhz.getCgy()+ JBDate.getNowDate(new Date().getTime());
        //临时表中批量插入数据
        String[] tmbhAndslArray=ywCgRkdhz.getTmbhAndsl();
        //获取缓存数据

        if(tmbhAndslArray!=null&&tmbhAndslArray.length>0){
            int k=tmbhAndslArray.length;
            TInitImportCgywTxt tInitImportCgyw1=new TInitImportCgywTxt();

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
                    getTInitImportCgywList(tmbh,sl,j,tgflag,ywCgRkdhz,list,tInitImportCgyw1,dateFormat,bean);


                }
            }
            long endq = System.currentTimeMillis();
            log.info("构建实体类，耗时：" + (endq - endd) + "毫秒");
            //批量操作
            if(list!=null&&list.size()>0){
                boolean flag=tInitImportCgywTxtService.saveBatch(sixCode,list);
                long end = System.currentTimeMillis();
                log.info("完成保存，耗时：" + (end - endq) + "毫秒");
                if(flag==true){
                    //调用存储过程增加明细和汇总等其他表数据
                    //执行存储过程的时候取消当前的事务管理器
                    String msg="";
                    if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"1".equals(ywCgRkdhz.getDjlx())){
                        msg=ywCgDdmxService.initCgDdmx(sixCode,SystemConstant.CR_TYPE,tgflag);
                    }else if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"2".equals(ywCgRkdhz.getDjlx())){
                        msg=ywCgDdmxService.initCgDdmx(sixCode,SystemConstant.CT_TYPE,tgflag);
                    }
                    log.info("执行存储过程返回的msg：" + msg);
                    long end1 = System.currentTimeMillis();
                    log.info("执行存储过程，耗时：" + (end1 - end) + "毫秒");
                    if(StringUtils.isNotEmpty(msg)&&msg.startsWith("OK")){
                        //修改采购汇总信息来源
                        log.info("返回的pzh是："+msg);
                        String[] pzhArray=msg.split("#");
                        String pzh=pzhArray[1];
                        if(StringUtils.isNotEmpty(pzh)){
                            YwCgRkdhz queryYwCgRkdhz=new YwCgRkdhz();
                            queryYwCgRkdhz.setXxly(SystemConstant.XXLY);
                            queryYwCgRkdhz.setBzxx(ywCgRkdhz.getBzxx());
                            Integer count=ywCgRkdhzMapper.update(queryYwCgRkdhz,new EntityWrapper<YwCgRkdhz>().eq("pzh",pzh));
                            long end2 = System.currentTimeMillis();
                            log.info("执行修改，耗时：" + (end2 - end1) + "毫秒");
                            if(count!=null&&count>0){
                                returnFlag=1;//1:保存成功
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
                    log.info("返回的状态是："+returnFlag+"=====>"+msg);
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
            tImportError.setMsg("条码和数量参数参数不正确!");
            tImportErrorList.add(tImportError);
            map.put("msgList",tImportErrorList);
            addReturnJson.setMsgList(map);
        }
        long end3 = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end3 - start) + "毫秒");
        return addReturnJson;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPzh(String sixCode, String pzh) {

        Integer flag=0;
        Integer flag1=0;
        Integer flag2=0;
        //删除采购入库单汇总同时删除关联表
        flag=ywCgRkdhzMapper.delete(pzh);
        log.info("删除汇总："+flag);
        //批量删除采购入库单明细
        if(flag>0) {
            flag1 = ywCgRkdmxService.batchDeleteByPzh(sixCode,pzh);
            log.info("删除明细：" + flag1);
            //删除采购入库单状态表
            flag2 = ywCgDdhzDjztService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除状态：" + flag2);
        }
        return flag;
    }
    //该接口供测试使用
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPzh1(String sixCode, String pzh) {

        Integer flag=0;
        Integer flag1=0;
        Integer flag2=0;
        //删除采购入库单汇总同时删除关联表
        flag=ywCgRkdhzMapper.delete(pzh);
        log.info("删除汇总："+flag);
        //批量删除采购入库单明细
        if(flag>0) {
            flag1 = ywCgRkdmxService.batchDeleteByPzh(sixCode,pzh);
            log.info("删除明细：" + flag1);
            //删除采购入库单状态表
            flag2 = ywCgDdhzDjztService.batchDeleteByPzh1(sixCode,pzh);
            log.info("删除状态：" + flag2);
        }
        return flag;
    }

    @Override
    public List<String> getPzh(String sixCode, Map<String, String> param) {
        String yhbh=param.get("yhbh");
        String djlx=param.get("djlx");
        List<String> list=ywCgRkdhzMapper.getPzh(SystemConstant.XXLY,yhbh,djlx);
        return list;
    }

    @Override
    public YwCgRkdhz findByPzh(String sixCode, String pzh) {
        YwCgRkdhz bean =ywCgRkdhzMapper.findByPzh(pzh);
        return bean;
    }

    public void getTInitImportCgywList(String tmbh,String sl,int j,String tgflag,YwCgRkdhz ywCgRkdhz,List<TInitImportCgywTxt> list,TInitImportCgywTxt tInitImportCgywTxt1,SimpleDateFormat dateFormat,DaSpSpDaJson bean){

        if(bean!=null){
            TInitImportCgywTxt tInitImportCgyw= (TInitImportCgywTxt) tInitImportCgywTxt1.clone();
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
            tInitImportCgyw.setGysbh(ywCgRkdhz.getGysbh());
            tInitImportCgyw.setYhbh(ywCgRkdhz.getCgy());
            if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"1".equals(ywCgRkdhz.getDjlx())){
                tInitImportCgyw.setYwflag(SystemConstant.CR_TYPE);
                tInitImportCgyw.setBz3("7");
            }else if(StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&"2".equals(ywCgRkdhz.getDjlx())){
                tInitImportCgyw.setYwflag(SystemConstant.CT_TYPE);
                tInitImportCgyw.setBz3("5");
            }
            tInitImportCgyw.setBz1(ywCgRkdhz.getBmbh());
            tInitImportCgyw.setBz2(dateFormat.format(ywCgRkdhz.getKdrq()));

            tInitImportCgyw.setLsj(bean.getLsjg()!=null?Double.valueOf(bean.getLsjg()):0.00);
            tInitImportCgyw.setDpj(bean.getDpjg()!=null?Double.valueOf(bean.getDpjg()):0.00);
            tInitImportCgyw.setJj(bean.getJhjg()!=null?Double.valueOf(bean.getJhjg()):0.00);
            tInitImportCgyw.setZkl(Double.valueOf("0.00"));
            tInitImportCgyw.setTmbh(tmbh);
            tInitImportCgyw.setSl(Integer.parseInt(sl));
            tInitImportCgyw.setTmfa("");
            tInitImportCgyw.setBzxx(ywCgRkdhz.getBzxx());
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
}
