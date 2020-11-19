package com.springboot.service.impl.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.HttpPostUtil;
import com.springboot.common.RedisClient;
import com.springboot.common.RedisUtil;
import com.springboot.dao.goods.DaSpSpdaMxMapper;
import com.springboot.model.AddReturnJson;
import com.springboot.model.goods.*;
import com.springboot.model.tm.DaSpTmfaszPpJson;
import com.springboot.model.tm.FjTmbhJson1;
import com.springboot.model.tm.SystemTmfa1;
import com.springboot.service.goods.*;
import com.springboot.service.tm.DaSpTmfaszPpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品商品明细 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaSpSpdaMxServiceImpl extends ServiceImpl<DaSpSpdaMxMapper, DaSpSpdaMx> implements DaSpSpdaMxService {

    @Value("${web.address}")
    private String address;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private DaSpSpdaMxMapper daSpSpdaMxMapper;
    @Resource
    private DaSpBxdaService daSpBxdaService;
    @Resource
    private DaSpYsdaService daSpYsdaService;
    @Resource
    private DaSpCmbtService daSpCmbtService;
    @Resource
    private DaSpCmzbService daSpCmzbService;
    @Resource
    private DaSpCmdmService daSpCmdmService;
    @Resource
    private DaSpTmfaszPpService daSpTmfaszPpService;
    //旧的扫条码方法
    @Override
    public DaSpSpDaJson findByTmbh(String sixCode,String isStartTmFangan,DaSpSpdaMx daSpSpdaMx) {
        //将商品明细数据放入缓存
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        DaSpSpDaJson newbean=null;
        //获取缓存的对象
        String key = sixCode+"_"+daSpSpdaMx.getTmbh()+"_daspmx";
//      redisClient.deleteCache(key);
        DaSpSpDaJson oldBean=redisClient.get(sixCode,key,DaSpSpDaJson.class);
        if(oldBean !=null){
            redisClient.closePipeline(sixCode);
            return oldBean;
        }else{
            List<DaSpSpDaJson> daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(daSpSpdaMx.getTmbh());
            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0){
                DaSpSpDaJson bean=daSpSpDaJsonlist.get(0);
                if(bean!=null){
                    newbean= getDaSpSpDaJson(sixCode,bean);
                    redisClient.set(sixCode,key, newbean);
                    redisClient.closePipeline(sixCode);
                }
            }
        }
        long end = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end - start) + "毫秒");
        return newbean;
    }
    public DaSpSpDaJson getDaSpSpDaJson(String sixCode,DaSpSpDaJson bean){

        if(StringUtils.isEmpty(bean.getBxbh())||"-".equals(bean.getBxbh())){
            bean.setBxbh("-");
            bean.setBxmc("-");
        }else{
            //从缓存中获取
            String bxmc="";
            DaSpBxda daSpBxda=redisClient.get(sixCode,sixCode+"_"+bean.getBxbh()+"_bxda",DaSpBxda.class);
            if(daSpBxda!=null){
                bxmc=daSpBxda.getBxmc();
            }else{
                bxmc=daSpBxdaService.findByBxbh(sixCode,bean.getBxbh());
            }
            bean.setBxmc(bxmc);
        }
        if(StringUtils.isEmpty(bean.getYsbh())||"-".equals(bean.getYsbh())){
            bean.setYsbh("-");
            bean.setYsmc("-");
        } else{
            String ysmc="";
            DaSpYsda daSpYsda=redisClient.get(sixCode,sixCode+"_"+bean.getYslsh()+"_ysda",DaSpYsda.class);
            if(daSpYsda!=null){
                ysmc=daSpYsda.getYsmc();
            }else{
                ysmc=daSpYsdaService.findByYsbh(sixCode,bean.getYslsh(),bean.getYsbh());
            }
            bean.setYsmc(ysmc);
        }
        //获取尺码标题
        //转换格式
        List<SaoDaSpSpDaJson> list=new ArrayList<SaoDaSpSpDaJson>();
        SaoDaSpSpDaJson saoDaSpSpDaJson=new SaoDaSpSpDaJson();
        saoDaSpSpDaJson.setCmzbm(bean.getCmzbm());
        saoDaSpSpDaJson.setCm(bean.getCmdmlwz());
        list.add(saoDaSpSpDaJson);
        this.getNewList(sixCode,list);
        bean.setCmbt(list.get(0).getCmbt());
        return bean;
    }
    //新的扫条码方法
    @Override
    public DaSpSpDaJson newfindByTmbh(String sixCode,String isStartTmFangan,DaSpSpdaMx daSpSpdaMx) {
        //将商品明细数据放入缓存
//        long start = System.currentTimeMillis();
        DaSpSpDaJson bean=null;
        //获取缓存的对象
//       String key = sixCode+"_"+daSpSpdaMx.getTmbh();
//      redisClient.deleteCache(key);
        //获取缓存中是否启用条码方案开关0：不启用1：启用
        if("0".equals(isStartTmFangan)){//不启用条码方案
            bean=this.getBeanByNotStart(sixCode,daSpSpdaMx);
        }else{   //启用条码方案
            bean=getBeanByStart(sixCode,daSpSpdaMx);
        }
//        long end = System.currentTimeMillis();
//        log.info("完成总任务，耗时：" + (end - start) + "毫秒");
        return bean;
    }
    //不启用条码方案
    public DaSpSpDaJson getBeanByNotStart(String sixCode,DaSpSpdaMx daSpSpdaMx){
        DaSpSpDaJson newbean=null;
        //获取缓存的对象
        String key = sixCode+"_"+daSpSpdaMx.getTmbh()+"_daspmx";
        DaSpSpDaJson oldBean=redisClient.get(sixCode,key,DaSpSpDaJson.class);
        if(oldBean !=null){
            log.info("商品明细是从redis中获取key为:"+key);
            redisClient.closePipeline(sixCode);
            return oldBean;
        }else{
            List<DaSpSpDaJson> daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(daSpSpdaMx.getTmbh());
            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0){
                DaSpSpDaJson bean=daSpSpDaJsonlist.get(0);
                if(bean!=null){
                    newbean= getDaSpSpDaJson(sixCode,bean);
                    redisClient.set(sixCode,key, newbean);
                    redisClient.closePipeline(sixCode);
                    log.info("商品明细是从数据库中获取为:"+bean);
                }
            }
        }
        return newbean;
    }
    //不启用条码方案
    public DaSpSpDaJson getBeanByStart(String sixCode,DaSpSpdaMx daSpSpdaMx){
        DaSpSpDaJson bean=null;
        DaSpSpDaJson oldBean=null;
        List<DaSpSpDaJson> daSpSpDaJsonlist=null;
        StringBuffer sb=new StringBuffer();
        //获取品牌条码方案集合
        String pptmkey = sixCode+"_pPtmfa";
        String daSpTmfaszPpJsonStr=redisUtil.get(sixCode,pptmkey);
        List<DaSpTmfaszPpJson> daSpTmfaszPpJsonList =JSONObject.parseArray(daSpTmfaszPpJsonStr, DaSpTmfaszPpJson.class);

        if(daSpTmfaszPpJsonList!=null&&daSpTmfaszPpJsonList.size()>0){
            int k=daSpTmfaszPpJsonList.size();
            for (int i = 0; i < k; i++) {
                DaSpTmfaszPpJson daSpTmfaszPpJson=daSpTmfaszPpJsonList.get(i);
                String ppTmbh="";
                String newppTmbh="";
                if("1".equals(daSpTmfaszPpJson.getJqfa())){//左截取
                    int le=daSpSpdaMx.getTmbh().length();
                    int le1=Integer.parseInt(daSpTmfaszPpJson.getZjws());
                    if(le<le1){
                        ppTmbh=daSpSpdaMx.getTmbh();
                    }else{
                        ppTmbh=daSpSpdaMx.getTmbh().substring(0,le1);
                    }
                    newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                    oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                    if(oldBean==null){//表示缓存中不存在
                        //查询数据库
                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0){//数据库有数据
                            bean=this.commonTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                            break;//数据库中查找到条码值跳出循环
                        }else{
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                    oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                    if(oldBean==null){
                                        //查询数据库
                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                            bean=this.commonTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                            break;//数据库中查找到条码值跳出循环
                                        }else{//查询附加条码
                                            //根据截取后的附加条码获取条码编号
                                            FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                            if(redisfjTmbhJson!=null){//表示redis中存在附加条码
                                                String tmbh=redisfjTmbhJson.getTmbh();
                                                if(StringUtils.isNotEmpty(tmbh)){//判断是否存在商品明细条码
                                                    oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                    if(oldBean==null) {//表示缓存中不存在
                                                        //查询数据库
                                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                            bean=this.commonTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                            break;//数据库中查找到条码值跳出循环
                                                        }
                                                    }else{
                                                        break;
                                                    }
                                                }else{
                                                    //表示附加条码找不到，需要从数据库中查找
                                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                    if(fjTmbhJson!=null){
                                                        redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                        redisClient.closePipeline(sixCode);
                                                    }else{
                                                        continue;//数据库中没有说明附加条码错误
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        break;
                                    }
                                }
//                                }
                            }
                        }
                    }else{
                        break;
                    }
                }else if("2".equals(daSpTmfaszPpJson.getJqfa())){//中截取
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                    try {
                        ppTmbh=daSpSpdaMx.getTmbh().substring(zindex,yindex);
                    }catch(Exception e){
                        log.info("数组越界!");
                        continue;
                    }
                    newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                    oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                    if(oldBean==null) {//表示缓存中不存在
                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                            bean=this.commonTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                            break;//数据库中查找到条码值跳出循环
                        }else{
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                    oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                    if(oldBean==null){
                                        //查询数据库
                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                            bean=this.commonTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                            break;//数据库中查找到条码值跳出循环
                                        }else{//查询附加条码
                                            //根据截取后的附加条码获取条码编号
                                            FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                            if(redisfjTmbhJson!=null){
                                                String tmbh=redisfjTmbhJson.getTmbh();
                                                if(StringUtils.isNotEmpty(tmbh)){
                                                    oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                    if(oldBean==null) {//表示缓存中不存在
                                                        //查询数据库
                                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                            bean=this.commonTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                            break;//数据库中查找到条码值跳出循环
                                                        }
                                                    }else{
                                                        break;
                                                    }
                                                }else{
                                                    //表示附加条码找不到，需要从数据库中查找
                                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                    if(fjTmbhJson!=null){
                                                        redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                        redisClient.closePipeline(sixCode);
                                                    }else{
                                                        continue;//数据库中没有说明附加条码错误
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                        }
                    }else{
                        break;
                    }
                }else if("3".equals(daSpTmfaszPpJson.getJqfa())){//右丢弃
                    int yindex=daSpSpdaMx.getTmbh().length()-Integer.parseInt(daSpTmfaszPpJson.getYdqws());
                    if(yindex>0){//跳出循环
                        try {
                            ppTmbh=daSpSpdaMx.getTmbh().substring(0,yindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }
                        newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                        oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                        if(oldBean==null) {//表示缓存中不存在
                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                bean=this.commonTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                                break;//数据库中查找到条码值跳出循环
                            }else{
                                //表示数据库无数据，走系统默认条码方案
                                String systemtmkey=sixCode+"_systemtm";
                                String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                                SystemTmfa1 systemTmfa=null;
                                if(StringUtils.isNotEmpty(systemTmfastr)){
                                    systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                                }else{
                                    systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                    redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                                }
                                if(systemTmfa!=null){//系统条码方案一定是有值
                                    //根据系统默认条码规则截取条码号
                                    String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                    if(StringUtils.isNotEmpty(systemTmbh)){
                                        String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                        oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                        if(oldBean==null){
                                            //查询数据库
                                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                bean=this.commonTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                                break;//数据库中查找到条码值跳出循环
                                            }else{//查询附加条码
                                                //根据截取后的附加条码获取条码编号
                                                FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                                if(redisfjTmbhJson!=null){
                                                    String tmbh=redisfjTmbhJson.getTmbh();
                                                    if(StringUtils.isNotEmpty(tmbh)){
                                                        oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                        if(oldBean==null) {//表示缓存中不存在
                                                            //查询数据库
                                                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                                bean=this.commonTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                                break;
                                                            }
                                                        }else{
                                                            break;
                                                        }
                                                    }else{
                                                        //表示附加条码找不到，需要从数据库中查找
                                                        FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                        if(fjTmbhJson!=null){
                                                            redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                            redisClient.closePipeline(sixCode);
                                                        }else{
                                                            continue;//数据库中没有说明附加条码错误
                                                        }
                                                    }
                                                }
                                            }
                                        }else{
                                            break;
                                        }
                                    }
                                }
                            }
                        }else{
                            break;
                        }
                    }

                }else if("4".equals(daSpTmfaszPpJson.getJqfa())){//中丢弃
                    sb.setLength(0);
                    String zjqtmbh=daSpSpdaMx.getTmbh();//获取条码号
                    int le=zjqtmbh.length();
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    if(zindex>0){
                        try {
                            ppTmbh=zjqtmbh.substring(0,zindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }
                        int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                        if(yindex>0){
                            String ppTmbh1="";
                            try {
                                 ppTmbh1=zjqtmbh.substring(yindex,le);
                            }catch(Exception e){
                                log.info("数组越界!");
                                continue;
                            }
                            ppTmbh=sb.append(ppTmbh).append(ppTmbh1).toString();
                            newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                            oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                            if(oldBean==null) {//表示缓存中不存在
                                daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                                if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                    bean=this.commonTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                                    break;//数据库中查找到条码值跳出循环
                                }else{
                                    //表示数据库无数据，走系统默认条码方案
                                    String systemtmkey=sixCode+"_systemtm";
                                    String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                                    SystemTmfa1 systemTmfa=null;
                                    if(StringUtils.isNotEmpty(systemTmfastr)){
                                        systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                                    }else{
                                        systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                        redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                                    }
                                    if(systemTmfa!=null){//系统条码方案一定是有值
                                        //根据系统默认条码规则截取条码号
                                        String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                        if(StringUtils.isNotEmpty(systemTmbh)){
                                            String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                            oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                            if(oldBean==null){
                                                //查询数据库
                                                daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                                if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                    bean=this.commonTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                                    break;//数据库中查找到条码值跳出循环
                                                }else{//查询附加条码
                                                    //根据截取后的附加条码获取条码编号
                                                    FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                                    if(redisfjTmbhJson!=null){
                                                        String tmbh=redisfjTmbhJson.getTmbh();
                                                        if(StringUtils.isNotEmpty(tmbh)){
                                                            oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                            if(oldBean==null) {//表示缓存中不存在
                                                                //查询数据库
                                                                daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                                if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                                    bean=this.commonTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                                    break;
                                                                }
                                                            }else{
                                                                break;
                                                            }
                                                        }else{
                                                            //表示附加条码找不到，需要从数据库中查找
                                                            FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                            if(fjTmbhJson!=null){
                                                                redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                                redisClient.closePipeline(sixCode);
                                                            }else{
                                                                continue;//数据库中没有说明附加条码错误
                                                            }
                                                        }
                                                    }
                                                }
                                            }else{
                                                break;
                                            }
                                        }
                                    }
                                }
                            }else{
                                break;
                            }
                        }
                    }
                }
            }
        }
        redisClient.closePipeline(sixCode);
        if(oldBean !=null){
            return oldBean;
        }else{
            return bean;
        }
    }
    //根据系统条码方案获取截取后的条码
    public String getSystemTmbh(String sixCode,String tmbh,SystemTmfa1 systemTmfa,StringBuffer sb){
        String systemTmbh="";
        if("1".equals(systemTmfa.getCsbh())) {//左截取
            int le=tmbh.length();
            int le1=Integer.parseInt(systemTmfa.getBzxx());
            if(le<le1){
                systemTmbh=tmbh;
            }else{
                systemTmbh=tmbh.substring(0,le1);
            }
        }else if("2".equals(systemTmfa.getCsbh())){//中截取
            String oldtmbh=systemTmfa.getBzxx();
            String[] array=oldtmbh.split("-");
            String oldzindex=array[0];
            String oldyindex=array[1];
            int zindex=Integer.parseInt(oldzindex)-1;
            int yindex=zindex+Integer.parseInt(oldyindex);
            try {
                systemTmbh=tmbh.substring(zindex,yindex);
            }catch(Exception e){
                log.info("数组越界!");
                systemTmbh="";
            }
        }else if("3".equals(systemTmfa.getCsbh())){//右丢弃
            int yindex=tmbh.length()-Integer.parseInt(systemTmfa.getBzxx());
            if(yindex>0){
                try {
                    systemTmbh=tmbh.substring(0,yindex);
                }catch(Exception e){
                    log.info("数组越界!");
                    systemTmbh="";
                }
            }
        }else if("4".equals(systemTmfa.getCsbh())){//中丢弃
            sb.setLength(0);
            String oldtmbh=systemTmfa.getBzxx();
            String[] array=oldtmbh.split("-");
            String oldzindex=array[0];
            String oldyindex=array[1];
            int le=tmbh.length();
            int zindex=Integer.parseInt(oldzindex)-1;
            if(zindex>0){
                String systemTmbh1="";
                String systemTmbh2="";
                try {
                    systemTmbh1=tmbh.substring(0,zindex);
                }catch(Exception e){
                    log.info("数组越界!");
                    systemTmbh1="";
                }
                int yindex=zindex+Integer.parseInt(oldyindex);
                try {
                    systemTmbh2=tmbh.substring(yindex,le);
                }catch(Exception e){
                    log.info("数组越界!");
                    systemTmbh2="";
                }
                systemTmbh=sb.append(systemTmbh1).append(systemTmbh2).toString();
            }
        }
        return systemTmbh;
    }
    //共用方法（缓存中没有数据就往缓存中存入）
    public DaSpSpDaJson commonTm(String sixCode,String ppTmbh,String systemTmbh,String tmbh, List<DaSpSpDaJson> daSpSpDaJsonlist){
        DaSpSpDaJson bean=daSpSpDaJsonlist.get(0);
        DaSpSpDaJson newbean= getDaSpSpDaJson(sixCode,bean);

        if(StringUtils.isNotEmpty(ppTmbh)){
            redisClient.set(sixCode,sixCode+"_"+ppTmbh+"_daspmx", newbean);
        }else if(StringUtils.isNotEmpty(systemTmbh)){
            redisClient.set(sixCode,sixCode+"_"+systemTmbh+"_daspmx", newbean);
        }else if(StringUtils.isNotEmpty(tmbh)){
            redisClient.set(sixCode,sixCode+"_"+tmbh+"_daspmx", newbean);
        }
        redisClient.closePipeline(sixCode);
        return newbean;
    }
    //盘存共用方法（缓存中没有数据就往缓存中存入）
    public DaSpSpDaJson commonPcTm(String sixCode,String ppTmbh,String systemTmbh,String tmbh, List<DaSpSpDaJson> daSpSpDaJsonlist){
        DaSpSpDaJson bean=daSpSpDaJsonlist.get(0);
        //获取当前系统盘存价格参数
        String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
        if(StringUtils.isNotEmpty(csbh)){
            if("QDPJ".equals(csbh)){
                bean.setLsjg(bean.getDpjg());
            }
        }else{
            csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
            if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                bean.setLsjg(bean.getDpjg());
            }
        }
        DaSpSpDaJson newbean= getDaSpSpDaJson(sixCode,bean);
        if(StringUtils.isNotEmpty(ppTmbh)){
            redisClient.set(sixCode,sixCode+"_"+ppTmbh+"_daspmx", newbean);
        }else if(StringUtils.isNotEmpty(systemTmbh)){
            redisClient.set(sixCode,sixCode+"_"+systemTmbh+"_daspmx", newbean);
        }else if(StringUtils.isNotEmpty(tmbh)){
            redisClient.set(sixCode,sixCode+"_"+tmbh+"_daspmx", newbean);
        }
        redisClient.closePipeline(sixCode);
        return newbean;
    }
    //此方法只针对盘存模块使用
    @Override
    public DaSpSpDaJson findPcByTmbh(String sixCode,String  isStartTmFangan,DaSpSpdaMx daSpSpdaMx) {
        //将商品明细数据放入缓存
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        DaSpSpDaJson bean=null;
        //获取缓存中是否启用条码方案开关0：不启用1：启用
        if("0".equals(isStartTmFangan)){//不启用条码方案
            bean=this.getPcBeanByNotStart(sixCode,daSpSpdaMx);
        }else{   //启用条码方案
            bean=getPcBeanByStart(sixCode,daSpSpdaMx);
        }
        long end = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end - start) + "毫秒");
        return bean;
    }
    //不启用条码方案盘存条码扫描
    public DaSpSpDaJson getPcBeanByNotStart(String sixCode,DaSpSpdaMx daSpSpdaMx){
        DaSpSpDaJson newbean=null;
        //获取缓存的对象
        String key = sixCode+"_"+daSpSpdaMx.getTmbh()+"_daspmx";
//      redisClient.deleteCache(key);
        DaSpSpDaJson oldBean=redisClient.get(sixCode,key,DaSpSpDaJson.class);
        if(oldBean !=null){
            String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
            if(StringUtils.isNotEmpty(csbh)){
                if("QDPJ".equals(csbh)){
                    oldBean.setLsjg(oldBean.getDpjg());
                }
            }else{
                csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                    oldBean.setLsjg(oldBean.getDpjg());
                }
            }
            redisClient.closePipeline(sixCode);
            return oldBean;
        }else{
            List<DaSpSpDaJson> daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(daSpSpdaMx.getTmbh());
            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0){
                DaSpSpDaJson bean=daSpSpDaJsonlist.get(0);
                //获取当前系统盘存价格参数
                String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                if(StringUtils.isNotEmpty(csbh)){
                    if("QDPJ".equals(csbh)){
                        bean.setLsjg(bean.getDpjg());
                    }
                }else{
                    csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                    if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                        bean.setLsjg(bean.getDpjg());
                    }
                }

                newbean= getDaSpSpDaJson(sixCode,bean);
                redisClient.set(sixCode,key, newbean);
                redisClient.closePipeline(sixCode);
            }
        }
        return newbean;
    }
    //启用条码方案盘存条码扫描
    public DaSpSpDaJson getPcBeanByStart(String sixCode,DaSpSpdaMx daSpSpdaMx){
        DaSpSpDaJson bean=null;
        DaSpSpDaJson oldBean=null;
        List<DaSpSpDaJson> daSpSpDaJsonlist=null;
        StringBuffer sb=new StringBuffer();
        //获取品牌条码方案集合
        String pptmkey = sixCode+"_pPtmfa";
        String daSpTmfaszPpJsonStr=redisUtil.get(sixCode,pptmkey);
        List<DaSpTmfaszPpJson> daSpTmfaszPpJsonList =JSONObject.parseArray(daSpTmfaszPpJsonStr, DaSpTmfaszPpJson.class);

        if(daSpTmfaszPpJsonList!=null&&daSpTmfaszPpJsonList.size()>0){
            int k=daSpTmfaszPpJsonList.size();
            for (int i = 0; i < k; i++) {
                DaSpTmfaszPpJson daSpTmfaszPpJson=daSpTmfaszPpJsonList.get(i);
                String ppTmbh="";
                String newppTmbh="";
                if("1".equals(daSpTmfaszPpJson.getJqfa())){//左截取
                    int le=daSpSpdaMx.getTmbh().length();
                    int le1=Integer.parseInt(daSpTmfaszPpJson.getZjws());
                    if(le<le1){
                        ppTmbh=daSpSpdaMx.getTmbh();
                    }else{
                        ppTmbh=daSpSpdaMx.getTmbh().substring(0,le1);
                    }
//                    ppTmbh=daSpSpdaMx.getTmbh().substring(0,Integer.valueOf(daSpTmfaszPpJson.getZjws()));
                    newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                    oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                    if(oldBean==null){//表示缓存中不存在
                        //查询数据库
                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0){//数据库有数据
                            bean=this.commonPcTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                            break;//数据库中查找到条码值跳出循环
                        }else{
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                    oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                    if(oldBean==null){
                                        //查询数据库
                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                            bean=this.commonPcTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                            break;//数据库中查找到条码值跳出循环
                                        }else{//查询附加条码
                                            //根据截取后的附加条码获取条码编号
                                            FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                            if(redisfjTmbhJson!=null){//表示redis中存在附加条码
                                                String tmbh=redisfjTmbhJson.getTmbh();
                                                if(StringUtils.isNotEmpty(tmbh)){//判断是否存在商品明细条码
                                                    oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                    if(oldBean==null) {//表示缓存中不存在
                                                        //查询数据库
                                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                            bean=this.commonPcTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                            break;//数据库中查找到条码值跳出循环
                                                        }
                                                    }else{
                                                        break;
                                                    }
                                                }else{
                                                    //表示附加条码找不到，需要从数据库中查找
                                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                    if(fjTmbhJson!=null){
                                                        redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                        redisClient.closePipeline(sixCode);
                                                    }else{
                                                        continue;//数据库中没有说明附加条码错误
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        break;
                                    }
                                }
//                                }
                            }
                        }
                    }else{
                        //获取当前系统盘存价格参数
                        String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                        if(StringUtils.isNotEmpty(csbh)){
                            if("QDPJ".equals(csbh)){
                                oldBean.setLsjg(oldBean.getDpjg());
                            }
                        }else{
                            csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                            if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                oldBean.setLsjg(oldBean.getDpjg());
                            }
                        }
                        break;
                    }
                }else if("2".equals(daSpTmfaszPpJson.getJqfa())){//中截取
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                    try {
                        ppTmbh=daSpSpdaMx.getTmbh().substring(zindex,yindex);
                    }catch(Exception e){
                        log.info("数组越界!");
                        continue;
                    }
                    newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                    oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                    if(oldBean==null) {//表示缓存中不存在
                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                            bean=this.commonPcTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                            break;//数据库中查找到条码值跳出循环
                        }else{
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                    oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                    if(oldBean==null){
                                        //查询数据库
                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                            bean=this.commonPcTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                            break;//数据库中查找到条码值跳出循环
                                        }else{//查询附加条码
                                            //根据截取后的附加条码获取条码编号
                                            FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                            if(redisfjTmbhJson!=null){
                                                String tmbh=redisfjTmbhJson.getTmbh();
                                                if(StringUtils.isNotEmpty(tmbh)){
                                                    oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                    if(oldBean==null) {//表示缓存中不存在
                                                        //查询数据库
                                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                            bean=this.commonPcTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                            break;//数据库中查找到条码值跳出循环
                                                        }
                                                    }else{
                                                        break;
                                                    }
                                                }else{
                                                    //表示附加条码找不到，需要从数据库中查找
                                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                    if(fjTmbhJson!=null){
                                                        redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                        redisClient.closePipeline(sixCode);
                                                    }else{
                                                        continue;//数据库中没有说明附加条码错误
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                        }
                    }else{
                        //获取当前系统盘存价格参数
                        String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                        if(StringUtils.isNotEmpty(csbh)){
                            if("QDPJ".equals(csbh)){
                                oldBean.setLsjg(oldBean.getDpjg());
                            }
                        }else{
                            csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                            if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                oldBean.setLsjg(oldBean.getDpjg());
                            }
                        }
                        break;
                    }
                }else if("3".equals(daSpTmfaszPpJson.getJqfa())){//右丢弃
                    int yindex=daSpSpdaMx.getTmbh().length()-Integer.parseInt(daSpTmfaszPpJson.getYdqws());
                    if(yindex>0){//跳出循环
                        try {
                            ppTmbh=daSpSpdaMx.getTmbh().substring(0,yindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }
                        newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                        oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                        if(oldBean==null) {//表示缓存中不存在
                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                bean=this.commonPcTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                                break;//数据库中查找到条码值跳出循环
                            }else{
                                //表示数据库无数据，走系统默认条码方案
                                String systemtmkey=sixCode+"_systemtm";
                                String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                                SystemTmfa1 systemTmfa=null;
                                if(StringUtils.isNotEmpty(systemTmfastr)){
                                    systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                                }else{
                                    systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                    redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                                }
                                if(systemTmfa!=null){//系统条码方案一定是有值
                                    //根据系统默认条码规则截取条码号
                                    String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                    if(StringUtils.isNotEmpty(systemTmbh)){
                                        String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                        oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                        if(oldBean==null){
                                            //查询数据库
                                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                bean=this.commonPcTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                                break;//数据库中查找到条码值跳出循环
                                            }else{//查询附加条码
                                                //根据截取后的附加条码获取条码编号
                                                FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                                if(redisfjTmbhJson!=null){
                                                    String tmbh=redisfjTmbhJson.getTmbh();
                                                    if(StringUtils.isNotEmpty(tmbh)){
                                                        oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                        if(oldBean==null) {//表示缓存中不存在
                                                            //查询数据库
                                                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                                bean=this.commonPcTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                                break;
                                                            }
                                                        }else{
                                                            break;
                                                        }
                                                    }else{
                                                        //表示附加条码找不到，需要从数据库中查找
                                                        FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                        if(fjTmbhJson!=null){
                                                            redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                            redisClient.closePipeline(sixCode);
                                                        }else{
                                                            continue;//数据库中没有说明附加条码错误
                                                        }
                                                    }
                                                }
                                            }
                                        }else{
                                            break;
                                        }
                                    }
                                }
                            }
                        }else{
                            //获取当前系统盘存价格参数
                            String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                            if(StringUtils.isNotEmpty(csbh)){
                                if("QDPJ".equals(csbh)){
                                    oldBean.setLsjg(oldBean.getDpjg());
                                }
                            }else{
                                csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                                if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                    oldBean.setLsjg(oldBean.getDpjg());
                                }
                            }
                            break;
                        }
                    }

                }else if("4".equals(daSpTmfaszPpJson.getJqfa())){//中丢弃
                    sb.setLength(0);
                    String zjqtmbh=daSpSpdaMx.getTmbh();//获取条码号
                    int le=zjqtmbh.length();
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    if(zindex>0){
                        try {
                            ppTmbh=zjqtmbh.substring(0,zindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }

                        int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                        String ppTmbh1="";
                        try {
                             ppTmbh1=zjqtmbh.substring(yindex,le);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }
                        ppTmbh=sb.append(ppTmbh).append(ppTmbh1).toString();
                        newppTmbh=sixCode+"_"+ppTmbh+"_daspmx";
                        oldBean=redisClient.get(sixCode,newppTmbh,DaSpSpDaJson.class);
                        if(oldBean==null) {//表示缓存中不存在
                            daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(ppTmbh);
                            if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                bean=this.commonPcTm(sixCode,ppTmbh,"","",daSpSpDaJsonlist);
                                break;//数据库中查找到条码值跳出循环
                            }else{
                                //表示数据库无数据，走系统默认条码方案
                                String systemtmkey=sixCode+"_systemtm";
                                String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                                SystemTmfa1 systemTmfa=null;
                                if(StringUtils.isNotEmpty(systemTmfastr)){
                                    systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                                }else{
                                    systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                    redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                                }
                                if(systemTmfa!=null){//系统条码方案一定是有值
                                    //根据系统默认条码规则截取条码号
                                    String systemTmbh=this.getSystemTmbh(sixCode,daSpSpdaMx.getTmbh(),systemTmfa,sb);
                                    String newsystemTmbh=sixCode+"_"+systemTmbh+"_daspmx";
                                    oldBean=redisClient.get(sixCode,newsystemTmbh,DaSpSpDaJson.class);
                                    if(oldBean==null){
                                        //查询数据库
                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(systemTmbh);
                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                            bean=this.commonPcTm(sixCode,"",systemTmbh,"",daSpSpDaJsonlist);
                                            break;//数据库中查找到条码值跳出循环
                                        }else{//查询附加条码
                                            //根据截取后的附加条码获取条码编号
                                            FjTmbhJson1 redisfjTmbhJson=redisClient.get(sixCode,newppTmbh+"_fj",FjTmbhJson1.class);
                                            if(redisfjTmbhJson!=null){
                                                String tmbh=redisfjTmbhJson.getTmbh();
                                                if(StringUtils.isNotEmpty(tmbh)){
                                                    oldBean=redisClient.get(sixCode,sixCode+"_"+tmbh+"_daspmx",DaSpSpDaJson.class);
                                                    if(oldBean==null) {//表示缓存中不存在
                                                        //查询数据库
                                                        daSpSpDaJsonlist=daSpSpdaMxMapper.findListByTmbh(tmbh);
                                                        if(daSpSpDaJsonlist!=null&&daSpSpDaJsonlist.size()>0) {//数据库有数据
                                                            bean=this.commonPcTm(sixCode,"","",tmbh,daSpSpDaJsonlist);
                                                            break;
                                                        }
                                                    }else{
                                                        break;
                                                    }
                                                }else{
                                                    //表示附加条码找不到，需要从数据库中查找
                                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                                    if(fjTmbhJson!=null){
                                                        redisClient.set(sixCode,sixCode+"_"+fjTmbhJson.getFjtm()+"_fj", fjTmbhJson.getTmbh());
                                                        redisClient.closePipeline(sixCode);
                                                    }else{
                                                        continue;//数据库中没有说明附加条码错误
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                        }else{
                            //获取当前系统盘存价格参数
                            String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                            if(StringUtils.isNotEmpty(csbh)){
                                if("QDPJ".equals(csbh)){
                                    oldBean.setLsjg(oldBean.getDpjg());
                                }
                            }else{
                                csbh=daSpSpdaMxMapper.findPcPriceByTmbh();
                                if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                    oldBean.setLsjg(oldBean.getDpjg());
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        redisClient.closePipeline(sixCode);
        if(oldBean !=null){
            return oldBean;
        }else{
            return bean;
        }
    }
    //扫一扫获取图片和数据
    @Override
    public DaSpSpDaMxJson findPicByTmbh(String sixCode,String ygbh,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx) {
        //不能取缓存
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        //是否启用条码方案
        DaSpSpDaMxJson daSpSpDaMxJson=null;
        //传参为条码编号
        //获取缓存中是否启用条码方案开关0：不启用1：启用
        //先通过款号查询，从档案表里查询
        daSpSpDaMxJson=daSpSpdaMxMapper.findDaPicBySpkh(daSpSpdaMx.getTmbh());
        if(daSpSpDaMxJson==null){
            if("0".equals(isStartTmFangan)){//不启用条码方案
                daSpSpDaMxJson=daSpSpdaMxMapper.findPicByTmbh(daSpSpdaMx.getTmbh());
            }else{   //启用条码方案
                daSpSpDaMxJson=this.getPicBeanByStart(sixCode,daSpSpdaMx.getTmbh());
            }
        }
//        DaSpSpDaMxJson daSpSpDaMxJson=daSpSpdaMxMapper.findPicByTmbh(daSpSpdaMx.getTmbh());
        long end1 = System.currentTimeMillis();
        log.info("启用条码方案，耗时：" + (end1 - start) + "毫秒");
        if(daSpSpDaMxJson!=null){
            List<GoodsPic> list=daSpSpdaMxMapper.findPicUrl(daSpSpDaMxJson.getSpkh());
            if(list!=null&&list.size()>0){
                daSpSpDaMxJson.setImageUrlList(list);
            }
            long end2 = System.currentTimeMillis();
            log.info("查询该款图片，耗时：" + (end2 - end1) + "毫秒");
            DaSpBxbt daSpBxbt=daSpSpdaMxMapper.findDaSpBxbt();
            long end3 = System.currentTimeMillis();
            log.info("查询版型属性，耗时：" + (end3 - end2) + "毫秒");
            //查询本店该款所有明细
            List<SaoDaSpSpDaJson> saoDaSpSpDaJsonList=daSpSpdaMxMapper.findSaoSpDaMx(ygbh,daSpSpDaMxJson.getSpkh(),daSpBxbt.getQybz());
            long end4 = System.currentTimeMillis();
            log.info("查询本店该款所有明细，耗时：" + (end4 - end3) + "毫秒");
            //转换格式
            List<SaoDaSpSpDaJson> newList=this.getNewList(sixCode,saoDaSpSpDaJsonList);
            //查看几属性
            if(daSpBxbt!=null){
                if("0".equals(daSpBxbt.getQybz())){//表示三属性--不包含版型
                    List<Object> saoDaSpSpDaJson3List=new ArrayList<Object>();
                    SaoDaSpSpDaJson3 bean=new SaoDaSpSpDaJson3();
                    if(saoDaSpSpDaJsonList!=null&&saoDaSpSpDaJsonList.size()>0){
                        int h=saoDaSpSpDaJsonList.size();
                        for (int i = 0; i < h; i++) {
                            SaoDaSpSpDaJson3 saoDaSpSpDaJson3= (SaoDaSpSpDaJson3) bean.clone();
                            saoDaSpSpDaJson3.setYsbh(saoDaSpSpDaJsonList.get(i).getYsbh());
                            saoDaSpSpDaJson3.setYsmc(saoDaSpSpDaJsonList.get(i).getYsmc());
                            saoDaSpSpDaJson3.setCmbh(saoDaSpSpDaJsonList.get(i).getCmbh());
                            saoDaSpSpDaJson3.setCmbt(saoDaSpSpDaJsonList.get(i).getCmbt());
                            saoDaSpSpDaJson3.setKcsl(saoDaSpSpDaJsonList.get(i).getKcsl());
                            saoDaSpSpDaJson3.setXssl(saoDaSpSpDaJsonList.get(i).getXssl());
                            saoDaSpSpDaJson3List.add(saoDaSpSpDaJson3);
                            daSpSpDaMxJson.setSaoDaSpSpDaJsonList(saoDaSpSpDaJson3List);
                        }
                    }

                }else if("1".equals(daSpBxbt.getQybz())){//表示四属性--包含版型
                    List<Object> saoDaSpSpDaJson1List=new ArrayList<Object>();
                    SaoDaSpSpDaJson1 bean=new SaoDaSpSpDaJson1();
                    if(saoDaSpSpDaJsonList!=null&&saoDaSpSpDaJsonList.size()>0){
                        int h=saoDaSpSpDaJsonList.size();
                        for (int i = 0; i < h; i++) {
                            SaoDaSpSpDaJson1 saoDaSpSpDaJson1= (SaoDaSpSpDaJson1) bean.clone();
                            saoDaSpSpDaJson1.setBxbh(saoDaSpSpDaJsonList.get(i).getBxbh());
                            saoDaSpSpDaJson1.setBxmc(saoDaSpSpDaJsonList.get(i).getBxmc());
                            saoDaSpSpDaJson1.setYsbh(saoDaSpSpDaJsonList.get(i).getYsbh());
                            saoDaSpSpDaJson1.setYsmc(saoDaSpSpDaJsonList.get(i).getYsmc());
                            saoDaSpSpDaJson1.setCmbh(saoDaSpSpDaJsonList.get(i).getCmbh());
                            saoDaSpSpDaJson1.setCmbt(saoDaSpSpDaJsonList.get(i).getCmbt());
                            saoDaSpSpDaJson1.setKcsl(saoDaSpSpDaJsonList.get(i).getKcsl());
                            saoDaSpSpDaJson1.setXssl(saoDaSpSpDaJsonList.get(i).getXssl());
                            saoDaSpSpDaJson1List.add(saoDaSpSpDaJson1);
                            daSpSpDaMxJson.setSaoDaSpSpDaJsonList(saoDaSpSpDaJson1List);
                        }
                    }
                }
                daSpSpDaMxJson.setDaSpBxbt(daSpBxbt);
            }

        }
        long end5 = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end5 - start) + "毫秒");
        return daSpSpDaMxJson;
    }
    //启用条码方案
    public DaSpSpDaMxJson getPicBeanByStart(String sixCode,String oldTmbh){
        DaSpSpDaMxJson bean=null;
        DaSpSpDaMxJson oldBean=null;
        StringBuffer sb=new StringBuffer();
        //获取品牌条码方案集合
        String pptmkey = sixCode+"_pPtmfa";
        String daSpTmfaszPpJsonStr=redisUtil.get(sixCode,pptmkey);
        List<DaSpTmfaszPpJson> daSpTmfaszPpJsonList =JSONObject.parseArray(daSpTmfaszPpJsonStr, DaSpTmfaszPpJson.class);

        if(daSpTmfaszPpJsonList!=null&&daSpTmfaszPpJsonList.size()>0){
            int k=daSpTmfaszPpJsonList.size();
            for (int i = 0; i < k; i++) {
                DaSpTmfaszPpJson daSpTmfaszPpJson=daSpTmfaszPpJsonList.get(i);
                String ppTmbh="";
                String newppTmbh="";
                if("1".equals(daSpTmfaszPpJson.getJqfa())){//左截取
                    int le=oldTmbh.length();
                    int le1=Integer.parseInt(daSpTmfaszPpJson.getZjws());
                    if(le<le1){
                        ppTmbh=oldTmbh;
                    }else{
                        ppTmbh=oldTmbh.substring(0,le1);
                    }
                    //查询数据库
                    bean=daSpSpdaMxMapper.findPicByTmbh(ppTmbh);

                    if(bean==null){//数据库无数据
                        //表示数据库无数据，走系统默认条码方案
                        String systemtmkey=sixCode+"_systemtm";
                        String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                        SystemTmfa1 systemTmfa=null;
                        if(StringUtils.isNotEmpty(systemTmfastr)){
                            systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                        }else{
                            systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                            redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                        }
                        if(systemTmfa!=null){//系统条码方案一定是有值
                            //根据系统默认条码规则截取条码号
                            String systemTmbh=this.getSystemTmbh(sixCode,oldTmbh,systemTmfa,sb);
                            if(StringUtils.isNotEmpty(systemTmbh)){
                                //查询数据库
                                bean=daSpSpdaMxMapper.findPicByTmbh(systemTmbh);
                                if(bean==null) {//数据库无数据
                                    //根据截取后的附加条码获取条码编号
                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                    if(fjTmbhJson!=null){
                                        bean=daSpSpdaMxMapper.findPicByTmbh(fjTmbhJson.getTmbh());
                                    }else{
                                        continue;//数据库中没有说明附加条码错误
                                    }
                                }else{
                                    break;
                                }
                            }
                        }
                    }else{
                        break;
                    }
                }else if("2".equals(daSpTmfaszPpJson.getJqfa())){//中截取
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                    try {
                        ppTmbh=oldTmbh.substring(zindex,yindex);
                    }catch(Exception e){
                        log.info("数组越界!");
                        continue;
                    }
                    bean=daSpSpdaMxMapper.findPicByTmbh(ppTmbh);
                    if(bean==null) {//表示数据库中无数据
                        //表示数据库无数据，走系统默认条码方案
                        String systemtmkey=sixCode+"_systemtm";
                        String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                        SystemTmfa1 systemTmfa=null;
                        if(StringUtils.isNotEmpty(systemTmfastr)){
                            systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                        }else{
                            systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                            redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                        }
                        if(systemTmfa!=null){//系统条码方案一定是有值
                            //根据系统默认条码规则截取条码号
                            String systemTmbh=this.getSystemTmbh(sixCode,oldTmbh,systemTmfa,sb);
                            if(StringUtils.isNotEmpty(systemTmbh)){
                                //查询数据库
                                bean=daSpSpdaMxMapper.findPicByTmbh(systemTmbh);
                                if(bean==null) {//数据库无数据
                                    //根据截取后的附加条码获取条码编号
                                    FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                    if(fjTmbhJson!=null){
                                        bean=daSpSpdaMxMapper.findPicByTmbh(fjTmbhJson.getTmbh());
                                    }else{
                                        continue;//数据库中没有说明附加条码错误
                                    }
                                }else{
                                    break;
                                }
                            }
                        }
                    }else{
                        break;
                    }
                }else if("3".equals(daSpTmfaszPpJson.getJqfa())){//右丢弃
                    int yindex=oldTmbh.length()-Integer.parseInt(daSpTmfaszPpJson.getYdqws());
                    if(yindex>0){//跳出循环
                        try {
                            ppTmbh=oldTmbh.substring(0,yindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }
                        bean=daSpSpdaMxMapper.findPicByTmbh(ppTmbh);
                        if(bean==null) {//表示数据库中无数据
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,oldTmbh,systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    //查询数据库
                                    bean=daSpSpdaMxMapper.findPicByTmbh(systemTmbh);
                                    if(bean==null) {//数据库无数据
                                        //根据截取后的附加条码获取条码编号
                                        FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                        if(fjTmbhJson!=null){
                                            bean=daSpSpdaMxMapper.findPicByTmbh(fjTmbhJson.getTmbh());
                                        }else{
                                            continue;//数据库中没有说明附加条码错误
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                        }else{
                            break;
                        }
                    }
                }else if("4".equals(daSpTmfaszPpJson.getJqfa())){//中丢弃
                    sb.setLength(0);
                    String zjqtmbh=oldTmbh;//获取条码号
                    int le=zjqtmbh.length();
                    int zindex=Integer.parseInt(daSpTmfaszPpJson.getZjqsws())-1;
                    if(zindex>0){
                        try {
                            ppTmbh=zjqtmbh.substring(0,zindex);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }

                        int yindex=zindex+Integer.parseInt(daSpTmfaszPpJson.getZjjsws());
                        String ppTmbh1="";
                        try {
                            ppTmbh1=zjqtmbh.substring(yindex,le);
                        }catch(Exception e){
                            log.info("数组越界!");
                            continue;
                        }

                        ppTmbh=sb.append(ppTmbh).append(ppTmbh1).toString();
                        bean=daSpSpdaMxMapper.findPicByTmbh(ppTmbh);
                        if(bean==null) {//表示数据库中无数据
                            //表示数据库无数据，走系统默认条码方案
                            String systemtmkey=sixCode+"_systemtm";
                            String systemTmfastr=redisUtil.get(sixCode,systemtmkey);
                            SystemTmfa1 systemTmfa=null;
                            if(StringUtils.isNotEmpty(systemTmfastr)){
                                systemTmfa=JSONObject.parseObject(systemTmfastr, SystemTmfa1.class);
                            }else{
                                systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
                                redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
                            }
                            if(systemTmfa!=null){//系统条码方案一定是有值
                                //根据系统默认条码规则截取条码号
                                String systemTmbh=this.getSystemTmbh(sixCode,oldTmbh,systemTmfa,sb);
                                if(StringUtils.isNotEmpty(systemTmbh)){
                                    //查询数据库
                                    bean=daSpSpdaMxMapper.findPicByTmbh(systemTmbh);
                                    if(bean==null) {//数据库无数据
                                        //根据截取后的附加条码获取条码编号
                                        FjTmbhJson1 fjTmbhJson=daSpTmfaszPpService.getFjTmbh(sixCode,ppTmbh);
                                        if(fjTmbhJson!=null){
                                            bean=daSpSpdaMxMapper.findPicByTmbh(fjTmbhJson.getTmbh());
                                        }else{
                                            continue;//数据库中没有说明附加条码错误
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bean;
    }
    //关联部门数据方法
    @Override
    public DaSpSpDaMxJson findDepartByTmbh(String sixCode, String ygbh,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx) {
        //不能取缓存
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        //是否启用条码方案
        DaSpSpDaMxJson daSpSpDaMxJson=null;
        //获取缓存中是否启用条码方案开关0：不启用1：启用
        daSpSpDaMxJson=daSpSpdaMxMapper.findDaPicBySpkh(daSpSpdaMx.getTmbh());
        if(daSpSpDaMxJson==null){
            if("0".equals(isStartTmFangan)){//不启用条码方案
                daSpSpDaMxJson=daSpSpdaMxMapper.findPicByTmbh(daSpSpdaMx.getTmbh());
            }else{   //启用条码方案
                daSpSpDaMxJson=this.getPicBeanByStart(sixCode,daSpSpdaMx.getTmbh());
            }
        }
        if(daSpSpDaMxJson!=null){
            //查看几属性
            DaSpBxbt daSpBxbt=daSpSpdaMxMapper.findDaSpBxbt();
            //查询所有店该款所有明细
            List<SaoDaSpSpDaJson> saoDaSpSpDaJsonList=daSpSpdaMxMapper.findSaoDepartSpDaMx(ygbh,daSpSpDaMxJson.getSpkh(),daSpBxbt.getQybz());
            //转换格式
            List<SaoDaSpSpDaJson> newList=this.getNewList(sixCode,saoDaSpSpDaJsonList);

            if(daSpBxbt!=null){
                if("0".equals(daSpBxbt.getQybz())){//表示三属性--不包含版型
                    List<Object> saoDepartDaSpSpDaJson3List=new ArrayList<Object>();
                    SaoDepartDaSpSpDaJson3 bean=new SaoDepartDaSpSpDaJson3();
                    if(saoDaSpSpDaJsonList!=null&&saoDaSpSpDaJsonList.size()>0){
                        int h=saoDaSpSpDaJsonList.size();
                        for (int i = 0; i < h; i++) {
                            SaoDepartDaSpSpDaJson3 saoDepartDaSpSpDaJson3= (SaoDepartDaSpSpDaJson3) bean.clone();
                            saoDepartDaSpSpDaJson3.setBmbh(saoDaSpSpDaJsonList.get(i).getBmbh());
                            saoDepartDaSpSpDaJson3.setBmmc(saoDaSpSpDaJsonList.get(i).getBmmc());
                            saoDepartDaSpSpDaJson3.setYsbh(saoDaSpSpDaJsonList.get(i).getYsbh());
                            saoDepartDaSpSpDaJson3.setYsmc(saoDaSpSpDaJsonList.get(i).getYsmc());
                            saoDepartDaSpSpDaJson3.setCmbh(saoDaSpSpDaJsonList.get(i).getCmbh());
                            saoDepartDaSpSpDaJson3.setCmbt(saoDaSpSpDaJsonList.get(i).getCmbt());
                            saoDepartDaSpSpDaJson3.setKcsl(saoDaSpSpDaJsonList.get(i).getKcsl());
                            saoDepartDaSpSpDaJson3List.add(saoDepartDaSpSpDaJson3);
                            daSpSpDaMxJson.setSaoDaSpSpDaJsonList(saoDepartDaSpSpDaJson3List);
                        }
                    }

                }else if("1".equals(daSpBxbt.getQybz())){//表示四属性--包含版型
                    List<Object> saoDepartDaSpSpDaJson4List=new ArrayList<Object>();
                    SaoDepartDaSpSpDaJson4 bean=new SaoDepartDaSpSpDaJson4();
                    if(saoDaSpSpDaJsonList!=null&&saoDaSpSpDaJsonList.size()>0){
                        int h=saoDaSpSpDaJsonList.size();
                        for (int i = 0; i < h; i++) {
                            SaoDepartDaSpSpDaJson4 saoDepartDaSpSpDaJson4= (SaoDepartDaSpSpDaJson4) bean.clone();
                            saoDepartDaSpSpDaJson4.setBmbh(saoDaSpSpDaJsonList.get(i).getBmbh());
                            saoDepartDaSpSpDaJson4.setBmmc(saoDaSpSpDaJsonList.get(i).getBmmc());
                            saoDepartDaSpSpDaJson4.setBxbh(saoDaSpSpDaJsonList.get(i).getBxbh());
                            saoDepartDaSpSpDaJson4.setBxmc(saoDaSpSpDaJsonList.get(i).getBxmc());
                            saoDepartDaSpSpDaJson4.setYsbh(saoDaSpSpDaJsonList.get(i).getYsbh());
                            saoDepartDaSpSpDaJson4.setYsmc(saoDaSpSpDaJsonList.get(i).getYsmc());
                            saoDepartDaSpSpDaJson4.setCmbh(saoDaSpSpDaJsonList.get(i).getCmbh());
                            saoDepartDaSpSpDaJson4.setCmbt(saoDaSpSpDaJsonList.get(i).getCmbt());
                            saoDepartDaSpSpDaJson4.setKcsl(saoDaSpSpDaJsonList.get(i).getKcsl());
                            saoDepartDaSpSpDaJson4List.add(saoDepartDaSpSpDaJson4);
                            daSpSpDaMxJson.setSaoDaSpSpDaJsonList(saoDepartDaSpSpDaJson4List);
                        }
                    }
                }
                daSpSpDaMxJson.setDaSpBxbt(daSpBxbt);
            }
        }
        return daSpSpDaMxJson;
    }

    public List<SaoDaSpSpDaJson> getNewList(String sixCode, List<SaoDaSpSpDaJson> list){
        if(list!=null&&list.size()>0){
            int k=list.size();
            //获取所有的尺码组
            List<DaSpCmzb> daSpCmzbList=daSpCmzbService.getList(sixCode);
            //获取所有的尺码代码
            List<DaSpCmdm> daSpCmdmList=daSpCmdmService.getList(sixCode);
            //获取所有的尺码标题
            List<DaSpCmbt> daSpCmbtList=daSpCmbtService.getList(sixCode);

            for (int i = 0; i < k; i++) {
                //一个采购入库单下的不同款可能用的是不同的尺码标题
                //通过尺码组编号和尺码编号查询尺码标题
                SaoDaSpSpDaJson bean=list.get(i);
//                DaSpCmbt daSpCmbt=daSpCmbtService.findByCmzbh(sixCode,bean.getCmzbh());
                if(daSpCmzbList!=null&&daSpCmzbList.size()>0){
                    int z=daSpCmzbList.size();
                    ok:
                    for (int j = 0; j <z ; j++) {
                        DaSpCmzb daSpCmzb=daSpCmzbList.get(j);
                        if(daSpCmzb.getCmzbm().equals(bean.getCmzbm())){
                            String cmdm=daSpCmzb.getCmdm();
                            if(daSpCmdmList!=null&&daSpCmdmList.size()>0){
                                int d=daSpCmdmList.size();
                                for (int l = 0; l <d ; l++) {
                                    DaSpCmdm daSpCmdm =daSpCmdmList.get(l);
                                    if(daSpCmdm.getCmdm().equals(cmdm)){
                                        String cmbt=daSpCmdm.getCmbt();
                                        if(daSpCmbtList!=null&&daSpCmbtList.size()>0){
                                            int b=daSpCmbtList.size();
                                            for (int m = 0; m <b ; m++) {
                                                DaSpCmbt daSpCmbt= daSpCmbtList.get(m);
                                                if(daSpCmbt.getCmbt().equals(cmbt)){//获取尺码标题
                                                    Method[] methods=daSpCmbt.getClass().getMethods();
                                                    for (int n = 0; n <methods.length ; n++) {
                                                        if(("get"+bean.getCm()).toLowerCase().equals(methods[n].getName().toLowerCase())){
                                                            try {
                                                                String cmbt1 =(String)methods[n].invoke(daSpCmbt);
                                                                bean.setCmbt(cmbt1);
                                                                break ok ;
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            } catch (InvocationTargetException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
    @Override
    public List<GoodsPic> findPicUrl(String sixCode, DaSpSpdaMx daSpSpdaMx) {
        List<GoodsPic> list=daSpSpdaMxMapper.findPicUrl(daSpSpdaMx.getSpkh());
        return list;
    }

    @Override
    public AddReturnJson uploadPic(String sixCode,String token, DaSpSpdaMx daSpSpdaMx,String hostAddress) {
        AddReturnJson addReturnJson=new AddReturnJson();
        List<Img> imageUrl=daSpSpdaMx.getImageUrlList();
        String imgs= JSONObject.toJSONString(imageUrl);
        String url=hostAddress+address;
        //获取款号下的条码编号
        String json="{\"sixCode\": \""+sixCode+"\",\"Token\":\""+token+"\",\"Method\": \"UploadPic\", \"ygbh\": \"system\",\"spkh\": \""+daSpSpdaMx.getSpkh()+"\", \"imgList\": "+imgs+"}";
        String result=HttpPostUtil.post(url, json);
        JSONObject jsonResult=JSONObject.parseObject(result);
        if(jsonResult!=null){
            String msg=jsonResult.getString("message");
            String code=jsonResult.getString("code");
            if(StringUtils.isNotEmpty(msg)&&"Y".equalsIgnoreCase(code)){
                addReturnJson.setFlag(1);
            }else{
                addReturnJson.setFlag(2);
            }
            addReturnJson.setMsg(msg);
        }
        return addReturnJson;
    }


    @Override
    public String findPcPriceByTmbh(String sixCode) {
        String cmbh=daSpSpdaMxMapper.findPcPriceByTmbh();
        return cmbh;
    }

    @Override
    public List<DaSpSpDaJson> findListByTmbh(String sixCode, DaSpSpdaMx daSpSpdaMx) {
        List<DaSpSpDaJson> list=daSpSpdaMxMapper.findListByTmbh(daSpSpdaMx.getTmbh());
        return list;
    }

    @Override
    public List<DaSpSpDaJson> findList(String sixCode) {
        List<DaSpSpDaJson> list=daSpSpdaMxMapper.findList();
        return list;
    }

    @Override
    public int findCount(String sixCode) {
        int count=daSpSpdaMxMapper.findCount();
        return count;
    }
    @Override
    public List<DaSpSpDaJson> getList(String sixCode,Integer pageNo,Integer pageSize) {
        List<DaSpSpDaJson> list=daSpSpdaMxMapper.getList(pageNo,pageSize);
        return list;
    }

    @Override
    public DaSpBxbt findDaSpBxbt(String sixCode) {
        DaSpBxbt bean=daSpSpdaMxMapper.findDaSpBxbt();
        return bean;
    }
}
