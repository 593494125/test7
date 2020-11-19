package com.springboot.service.impl.wholesale;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.JBDate;
import com.springboot.common.RedisClient;
import com.springboot.common.SystemConstant;
import com.springboot.dao.wholesale.YwPfRkdhzMapper;
import com.springboot.model.AddReturnJson;
import com.springboot.model.customer.DaKhdaJson;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.goods.DaSpSpDaJson;
import com.springboot.model.wholesale.TInitImportPfywTxt;
import com.springboot.model.wholesale.YwPfRkdhz;
import com.springboot.model.wholesale.YwPfRkdhzQueryJson;
import com.springboot.service.customer.DaKhdaService;
import com.springboot.service.department.DaBmdaService;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.error.TImportErrorListService;
import com.springboot.service.purchase.YwCgDdmxService;
import com.springboot.service.wholesale.TInitImportPfywTxtService;
import com.springboot.service.wholesale.YwPfDdhzDjztService;
import com.springboot.service.wholesale.YwPfRkdhzService;
import com.springboot.service.wholesale.YwPfRkdmxService;
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
 * @since 2020-05-13
 */
@Service
@Slf4j
public class YwPfRkdhzServiceImpl extends ServiceImpl<YwPfRkdhzMapper, YwPfRkdhz> implements YwPfRkdhzService {
    @Autowired
    private RedisClient redisClient;
    @Resource
    private YwPfRkdhzMapper ywPfRkdhzMapper;
    @Resource
    private DaYgdaService daYgdaService;
    @Resource
    private DaBmdaService daBmdaService;
    @Resource
    private TInitImportPfywTxtService tInitImportPfywTxtService;
    @Resource
    private YwPfRkdmxService ywPfRkdmxService;
    @Resource
    private YwPfDdhzDjztService ywPfDdhzDjztService;
    @Resource
    private YwCgDdmxService ywCgDdmxService;
    @Resource
    private DaKhdaService daKhdaService;
    @Autowired
    private TImportErrorListService tImportErrorListService;

    @Override
    public Page<YwPfRkdhz> getPage(String sixCode, Page<YwPfRkdhz> page, YwPfRkdhz ywPfRkdhz, Map<String, String> param) {
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Integer pageNo=ywPfRkdhz.getPageNo();
        Integer pageSize=ywPfRkdhz.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywPfRkdhz.getDjlx();
        List<YwPfRkdhz> list=ywPfRkdhzMapper.getList(SystemConstant.XXLY,yhbh,djlx,pageNo,pageSize);
        List<YwPfRkdhz> newList=this.changeList(sixCode,yhbh,list);


        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }
    public List<YwPfRkdhz> changeList(String sixCode, String yhbh, List<YwPfRkdhz> list){
        //获取当天日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        List<YwPfRkdhz> newList=new ArrayList<YwPfRkdhz>();
        if(list!=null&&list.size()>0){
            //获取系统下所有用户
            List<DaYgdaJson> daYgdaList= daYgdaService.findCgy(sixCode);
            //获取部门
            List<DaBmdaJson> daBmdaList= daBmdaService.findPfBmda(sixCode,yhbh);
            //获取客户
            List<DaKhdaJson> dakhdaList= daKhdaService.findKhda(sixCode,yhbh);
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                YwPfRkdhz bean=list.get(i);
                Date kdrq=bean.getKdrq();
                Date kdsj=bean.getKdsj();
                String newkdrq=dateFormat1.format(kdrq);
                String newkdsj=dateFormat.format(kdsj);
                bean.setNewkdrq(newkdrq);
                bean.setNewkdsj(newkdsj);
                if(StringUtils.isNotEmpty(bean.getCgfs())&&"8".equals(bean.getCgfs())){
                    bean.setCgfs("批发出库");
                }
                if(StringUtils.isNotEmpty(bean.getDjlx())&&"1".equals(bean.getDjlx())){
                    bean.setDjlx("批发出库单");
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
                if(dakhdaList!=null&&dakhdaList.size()>0){
                    int h=dakhdaList.size();
                    for (int j = 0; j <h ; j++) {
                        DaKhdaJson daKhdaJson=dakhdaList.get(j);
                        if(daKhdaJson.getKhbh().equals(bean.getKhbh())){
                            bean.setKhmc(daKhdaJson.getKhmc());
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
    public Page<YwPfRkdhz> getQueryPage(String sixCode, Page<YwPfRkdhz> page, YwPfRkdhzQueryJson ywPfRkdhzQueryJson, Map<String, String> param) {
        Integer pageNo=ywPfRkdhzQueryJson.getPageNo();
        Integer pageSize=ywPfRkdhzQueryJson.getPageSize();
//        String xxly="IPAD";
        String yhbh=param.get("yhbh");
        String djlx=ywPfRkdhzQueryJson.getDjlx();
        List<YwPfRkdhz> list=ywPfRkdhzMapper.getQueryPage(SystemConstant.XXLY,yhbh,ywPfRkdhzQueryJson.getStartTime(),ywPfRkdhzQueryJson.getEndTime(),
                ywPfRkdhzQueryJson.getBmbh(),ywPfRkdhzQueryJson.getKhbh(),ywPfRkdhzQueryJson.getCgy(),ywPfRkdhzQueryJson.getPzh(),djlx,pageNo,pageSize);

        List<YwPfRkdhz> newList=this.changeList(sixCode,yhbh,list);
        page.setRecords(newList);
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        return page;
    }

    @Override
    public AddReturnJson saveBean(String sixCode, YwPfRkdhz ywPfRkdhz) {
        Map<String,List<TImportErrorList>> map=new HashMap<String,List<TImportErrorList>>();
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        Integer returnFlag=0;
        Boolean deleteFlag=false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AddReturnJson addReturnJson=new AddReturnJson();
        List<TInitImportPfywTxt> list=new ArrayList<TInitImportPfywTxt>();
        //先删除原来临时数据
        if(StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())&&"1".equals(ywPfRkdhz.getDjlx())){
            deleteFlag=tInitImportPfywTxtService.deleteOldData(sixCode,ywPfRkdhz.getCgy(),SystemConstant.PF_TYPE);
        }else if(StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())&&"2".equals(ywPfRkdhz.getDjlx())){
            deleteFlag=tInitImportPfywTxtService.deleteOldData(sixCode,ywPfRkdhz.getCgy(),SystemConstant.PT_TYPE);
        }
        log.info("删除采购临时表状态："+deleteFlag);
        long endd = System.currentTimeMillis();
        log.info("删除采购临时表状态，耗时：" + (endd - start) + "毫秒");
        String tgflag=ywPfRkdhz.getCgy()+ JBDate.getNowDate(new Date().getTime());
        //临时表中批量插入数据
        String[] tmbhAndslArray=ywPfRkdhz.getTmbhAndsl();
        if(tmbhAndslArray!=null&&tmbhAndslArray.length>0) {
            int k = tmbhAndslArray.length;
            TInitImportPfywTxt tInitImportPfyw1 = new TInitImportPfywTxt();

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
                    getTInitImportDbywList(tmbh, sl, j, tgflag, ywPfRkdhz, list, tInitImportPfyw1, dateFormat, bean);


                }
            }
            long endq = System.currentTimeMillis();
            log.info("构建实体类，耗时：" + (endq - endd) + "毫秒");
            //批量操作
            if (list != null && list.size() > 0) {
                boolean flag = tInitImportPfywTxtService.saveBatch(sixCode, list);
                long end = System.currentTimeMillis();
                log.info("完成保存，耗时：" + (end - endq) + "毫秒");
                if (flag == true) {
                    //调用存储过程增加明细和汇总等其他表数据
                    String msg = "";
                    if (StringUtils.isNotEmpty(ywPfRkdhz.getDjlx()) && "1".equals(ywPfRkdhz.getDjlx())) {
                        msg = ywCgDdmxService.initCgDdmx(sixCode, SystemConstant.PF_TYPE, tgflag);
                    } else if (StringUtils.isNotEmpty(ywPfRkdhz.getDjlx()) && "2".equals(ywPfRkdhz.getDjlx())) {
                        msg = ywCgDdmxService.initCgDdmx(sixCode, SystemConstant.PT_TYPE, tgflag);
                    }
                    log.info("执行存储过程返回的msg：" + msg);
                    long end1 = System.currentTimeMillis();
                    log.info("执行存储过程，耗时：" + (end1 - end) + "毫秒");
                    if (StringUtils.isNotEmpty(msg) && msg.startsWith("OK")) {
                        //修改采购汇总信息来源
                        log.info("返回的pzh是：" + msg);
                        String[] pzhArray = msg.split("#");
                        String pzh = pzhArray[1];
                        if (StringUtils.isNotEmpty(pzh)) {
                            YwPfRkdhz queryYwPfRkdhz = new YwPfRkdhz();
                            queryYwPfRkdhz.setXxly(SystemConstant.XXLY);
                            queryYwPfRkdhz.setBzxx(ywPfRkdhz.getBzxx());
                            Integer count = ywPfRkdhzMapper.update(queryYwPfRkdhz, new EntityWrapper<YwPfRkdhz>().eq("pzh", pzh));
                            if (count != null && count > 0) {
                                returnFlag = 1;
                                addReturnJson.setFlag(1);
                                addReturnJson.setMsg(msg);
                            }
                        } else {
                            addReturnJson.setFlag(2);
                            List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                            TImportErrorList tImportError=new TImportErrorList();
                            tImportError.setMsg("凭证号为空！");
                            tImportErrorList.add(tImportError);
                            map.put("msgList",tImportErrorList);
                            addReturnJson.setMsgList(map);
                        }
                    } else {
                        addReturnJson.setFlag(2);
                        //查询错误记录
                        List<TImportErrorList> tImportErrorList=tImportErrorListService.getList(sixCode,tgflag);
                        map.put("msgList",tImportErrorList);
                        addReturnJson.setMsgList(map);
                    }
                    log.info("返回的状态是：" + returnFlag);
                } else {
                    addReturnJson.setFlag(2);
                    List<TImportErrorList> tImportErrorList=new ArrayList<TImportErrorList>();
                    TImportErrorList tImportError=new TImportErrorList();
                    tImportError.setMsg("临时表保存失败!");
                    tImportErrorList.add(tImportError);
                    map.put("msgList",tImportErrorList);
                }
            } else {
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
    public void getTInitImportDbywList(String tmbh, String sl, int j, String tgflag, YwPfRkdhz ywPfRkdhz, List<TInitImportPfywTxt> list, TInitImportPfywTxt tInitImportPfywTxt1, SimpleDateFormat dateFormat, DaSpSpDaJson bean){

        if(bean!=null){
            TInitImportPfywTxt tInitImportPfyw= (TInitImportPfywTxt) tInitImportPfywTxt1.clone();
            tInitImportPfyw.setSpkh(bean.getSpkh());
            tInitImportPfyw.setKsmc(bean.getSpmc());
            tInitImportPfyw.setJldw(bean.getJldw());
            tInitImportPfyw.setYslsh(bean.getYslsh());
            tInitImportPfyw.setYsbh(bean.getYsbh());
            tInitImportPfyw.setYsmc(bean.getYsmc());
            tInitImportPfyw.setBxbh(StringUtils.isNotEmpty(bean.getBxbh())?bean.getBxbh():"-");

            tInitImportPfyw.setBxmc(bean.getBxmc());
            tInitImportPfyw.setYsmc(bean.getYsmc());
            tInitImportPfyw.setBxmc(StringUtils.isNotEmpty(bean.getBxmc())?bean.getBxmc():"-");
            tInitImportPfyw.setCmzbh(bean.getCmzbm());
            this.setCm(bean.getCmdmlwz(),sl,tInitImportPfyw);
            tInitImportPfyw.setHanghao(j);
            tInitImportPfyw.setTgflag(tgflag);
            tInitImportPfyw.setGysbh(ywPfRkdhz.getBmbh());
            tInitImportPfyw.setYhbh(ywPfRkdhz.getCgy());
            if(StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())&&"1".equals(ywPfRkdhz.getDjlx())){
                tInitImportPfyw.setYwflag(SystemConstant.PF_TYPE);
                tInitImportPfyw.setBz3("8");
            }else if(StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())&&"2".equals(ywPfRkdhz.getDjlx())){
                tInitImportPfyw.setYwflag(SystemConstant.PT_TYPE);
                tInitImportPfyw.setBz3("3");
            }
            tInitImportPfyw.setBz4("qk");
            tInitImportPfyw.setBz1(ywPfRkdhz.getKhbh());
            tInitImportPfyw.setBz2(dateFormat.format(ywPfRkdhz.getKdrq()));

            tInitImportPfyw.setLsj(Double.valueOf(bean.getLsjg()));
            tInitImportPfyw.setDpj(Double.valueOf(bean.getDpjg()));
            tInitImportPfyw.setJj(Double.valueOf(bean.getJhjg()));
            tInitImportPfyw.setZkl(Double.valueOf("0.00"));
            tInitImportPfyw.setTmbh(tmbh);
            tInitImportPfyw.setSl(Integer.parseInt(sl));
            tInitImportPfyw.setTmfa("");
            tInitImportPfyw.setBzxx(ywPfRkdhz.getBzxx());
            list.add(tInitImportPfyw);
        }
    }
    public void setCm(String cm, String sl, TInitImportPfywTxt tInitImportPfyw){

        if(StringUtils.isNotEmpty(cm)&&"CM1".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm1(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm1(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM2".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm2(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm2(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM3".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm3(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm3(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM4".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm4(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm4(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM5".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm5(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm5(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM6".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm6(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm6(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM7".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm7(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm7(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM8".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm8(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm8(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM9".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm9(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm9(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM10".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm10(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm10(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM11".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm11(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm11(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM12".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm12(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm12(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM13".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm13(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm13(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM14".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm14(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm14(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM15".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm15(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm15(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM16".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm16(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm16(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM17".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm17(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm17(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM18".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm18(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm18(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM19".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm19(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm19(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM20".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm20(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm20(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM21".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm21(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm21(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM22".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm22(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm22(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM23".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm23(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm23(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM24".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm24(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm24(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM25".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm25(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm25(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM26".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm26(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm26(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM27".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm27(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm27(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM28".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm28(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm28(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM29".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm29(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm29(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM30".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm30(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm30(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM31".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm31(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm31(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM32".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm32(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm32(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM33".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm33(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm33(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM34".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm34(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm34(0);
        }
        if(StringUtils.isNotEmpty(cm)&&"CM35".equalsIgnoreCase(cm)){
            tInitImportPfyw.setCm35(Integer.parseInt(sl));
        }else{
            tInitImportPfyw.setCm35(0);
        }

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByPzh(String sixCode, String pzh) {
        Integer flag=0;
        Integer flag1=0;
        Integer flag2=0;
        //删除采购入库单汇总同时删除关联表
        flag=ywPfRkdhzMapper.delete(pzh);
        log.info("删除汇总："+flag);
        //批量删除采购入库单明细
        if(flag>0) {
            flag1 = ywPfRkdmxService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除明细：" + flag1);
            //删除采购入库单状态表
            flag2 = ywPfDdhzDjztService.batchDeleteByPzh(sixCode, pzh);
            log.info("删除状态：" + flag2);
        }
        return flag;
    }

    @Override
    public List<String> getPzh(String sixCode, Map<String, String> param) {
        String yhbh=param.get("yhbh");
        String djlx=param.get("djlx");
        List<String> list=ywPfRkdhzMapper.getPzh(SystemConstant.XXLY,yhbh,djlx);
        return list;
    }

    @Override
    public YwPfRkdhz findByPzh(String sixCode, String pzh) {
        YwPfRkdhz bean =ywPfRkdhzMapper.findByPzh(pzh);
        return bean;
    }
    @Override
    public YwPfRkdhz findLsByPzh(String sixCode, String pzh) {
        YwPfRkdhz bean =ywPfRkdhzMapper.findLsByPzh(pzh);
        return bean;
    }
}
