package com.springboot.web.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.common.*;
import com.springboot.core.DownLoadResult;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.jwt.JwtTokenUtil;
import com.springboot.jwt.SystemDefines;
import com.springboot.model.CommonGetRedisValue;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.model.department.DaBmda;
import com.springboot.model.equip.BaseEquip;
import com.springboot.model.goods.*;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.posparm.YwPosParm;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.model.system.DaQjCsb;
import com.springboot.model.system.DaQjCsbKeyJson;
import com.springboot.model.system.GnqxJson;
import com.springboot.model.tm.DaSpTmfaszPpJson;
import com.springboot.model.tm.FjTmbhJson1;
import com.springboot.model.tm.SystemTmfa1;
import com.springboot.model.upload.BaseUpload;
import com.springboot.model.user.BaseUser;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.datasource.BaseDatasourceService;
import com.springboot.service.department.DaBmdaService;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.equip.BaseEquipService;
import com.springboot.service.goods.*;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.posparm.YwPosParmService;
import com.springboot.service.redisdatasource.BaseRedisDatasourceService;
import com.springboot.service.system.DaQjCsbService;
import com.springboot.service.system.DaQxZydaService;
import com.springboot.service.tm.DaSpTmfaszPpService;
import com.springboot.service.upload.BaseUploadService;
import com.springboot.service.user.BaseUserService;
import com.springboot.service.user.DaQxYhdaService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;


@Controller
@RequestMapping("/api/app")
public class LoginController {

    private static Logger log= LoggerFactory.getLogger(LoginController.class);

    @Resource
    private BaseUserService baseUserService;
    @Resource
    private DaYgdaService daYgdaService;
    @Resource
    private BaseEquipService baseEquipService;
    @Resource
    private BaseOrgService baseOrgService;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Resource
    private DaSpBxdaService daSpBxdaService;
    @Resource
    private DaSpYsdaService daSpYsdaService;
    @Resource
    private BaseUploadService baseUploadService;
    @Resource
    private DaQxYhdaService daQxYhdaService;
    @Resource
    private DaSpTmfaszPpService daSpTmfaszPpService;
    @Resource
    private DaSpCmbtService daSpCmbtService;
    @Resource
    private DaSpCmzbService daSpCmzbService;
    @Resource
    private DaSpCmdmService daSpCmdmService;
    @Resource
    private YwPosParmService ywPosParmService;
    @Resource
    private DaQjCsbService daQjCsbService;
    @Resource
    private DaQxZydaService daQxZydaService;
    @Resource
    private RedisDBConfig redisDBConfig;
    @Resource
    private DaBmdaService daBmdaService;
    @Resource
    private BaseDatasourceService baseDatasourceService;
    @Resource
    private BaseRedisDatasourceService baseRedisDatasourceService;

    @Value("${encrypt.aesKey}")
    private String aesKey;
    @Value("${encrypt.ivVal}")
    private String ivVal;
    @Value("${web.filepath}")
    private String filepath;
    @Value("${web.fileAddress}")
    private String fileAddress;

    /**
     * 系统管理员获取token接口
     * appId（获取Token的ID）
     * appSecret（获取Token的密码）
     */
    @PostMapping("/getToken")
    public Result getToken() {
        Result result=null;
        //解析jsons获取appId，appSecret
            String ygbh="auth";
            //根据用户名和密码获取用户对象
//                yhmm = SHA256Util.mySHA256(yhmm);
            String Token = JwtTokenUtil.generateToken(ygbh);
            redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,SystemConstant.DEFAULT_REDIS_KEY+"_mac_"+ygbh.toLowerCase(),Token);
            result= ResultGenerator.genSuccessResult(Token);
        return result;
    }

    /**
     * 授权
     *修改返回对象专门为了downurl参数做的修改，为的是不影响以前用户使用
     **/
    @PostMapping("/authorize")
    public DownLoadResult authorize(@RequestBody String jsons) {
        //解析jsons获取orgId
        if(StringUtils.isNotEmpty(jsons)){
            JSONObject json = JSONObject.parseObject(jsons);
            String sixCode=json.getString("sixCode");
            String uuid=json.getString("uuid");
            String equipType=json.getString("equipType");
            String equipManufacturer=json.getString("equipManufacturer");
            String sn=json.getString("sn");
            String name="";
            String areaServiceUrl="";
            Integer num=0;
            if(StringUtils.isNotEmpty(sixCode)){
                String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);//通过五位码获取组织id
                if(StringUtils.isNotEmpty(orgId)){
                    //获取组织
                    BaseOrg org=baseOrgService.findById("",orgId);
                    if(org!=null){
                        name=org.getOrgName();
                        //验证开通的手机数量
                        Integer count=org.getCount();
                        num=baseEquipService.getCountByOrgId("",orgId);
                        if(num!=null&&count!=null&&num==count){//如果设备数量等于开通数量时，限制继续授权
                            return ResultGenerator.genErrorSetMsgErrorResult1("用户数已达上限，请联系客服!");
                        }
                    }else{
                        return ResultGenerator.genErrorSetMsgErrorResult1("该企业不存在，请先创建企业信息!");
                    }
                    //用户重复授权时根据uuid和组织orgId查询数据，删除旧的授权数据
                    BaseEquip bean=baseEquipService.findByUuidAndOrgId("",uuid,orgId);
                    if(bean!=null){
                        baseEquipService.deleteById("",bean.getId());
                    }
                    //sn为空的时候从uuid中获取
                    if(StringUtils.isEmpty(sn)){
                        String[] strArray=uuid.split(",");
                        if(strArray!=null&&strArray.length>0){
                            String str=strArray[0];
                            sn=str.substring(7,14);
                        }
                    }
                    Integer flag=this.insertBean(uuid,orgId,equipType,equipManufacturer,"",sn);
                    //判断如果设备表中存在该企业就不再同步商品明细表
                    if(num!=null&&num>0){
                    }else{
                        ImportTask task = new ImportTask(sixCode);
                        taskExecutor.execute(task);
                    }
                    if(flag!=null&&flag>0){
                        //默认启用条码方案
//                        redisUtil.set(sixCode+"_isStartTmFangan", "1");
                        //获取主机头
                        String hostAddress=this.getHostAddress(orgId);
                        if(StringUtils.isEmpty(hostAddress)){
                            return ResultGenerator.genErrorSetMsgErrorResult1("该企业主机头不存在!");
                        }
                        //获取区域服务器地址
                        if(org!=null){
                            areaServiceUrl=org.getAreaServiceUrl()+SystemConstant.AREA_SERVICE_PORT;
                        }else{
                            return ResultGenerator.genErrorSetMsgErrorResult1("区域服务器地址不能为空!");
                        }
                        return ResultGenerator.genSuccessResult1(name+"#"+hostAddress,fileAddress,areaServiceUrl);
                    }else{
                        return ResultGenerator.genErrorSetMsgErrorResult1("授权失败!");
                    }
                }else{
                    return ResultGenerator.genErrorSetMsgErrorResult1("五位码不正确，请重新获取!");
                }
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult1("param_required");
            }
        }else{
            return ResultGenerator.genErrorSetMsgErrorResult1("param_required");
        }
    }
    public Integer insertBean(String uuid,String orgId,String equipType,String equipManufacturer,String userName,String sn){
        //将设备信息存入数据库中
        BaseEquip baseEquip=new BaseEquip();
        baseEquip.setUuid(uuid);
        baseEquip.setEquipType(equipType);
        baseEquip.setEquipManufacturer(equipManufacturer);
        baseEquip.setUserName(userName);
        baseEquip.setSn(sn);
        baseEquip.setOrgId(orgId);
        baseEquip.setCreateTime(JBDate.getNowDate(new Date().getTime()));
        baseEquip.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        Integer flag=baseEquipService.insertBean("",baseEquip);
        return flag;
    }
    /**
     * 授权
     *修改返回对象专门为了downurl参数做的修改，为的是不影响以前用户使用
     **/
    @PostMapping("/simpleAuthorize")
    public DownLoadResult simpleAuthorize(@RequestBody String jsons) {
        //解析jsons获取orgId
        if(StringUtils.isNotEmpty(jsons)){
            JSONObject json = JSONObject.parseObject(jsons);
            String sixCode=json.getString("sixCode");
            String name="";
            String areaServiceUrl="";
            if(StringUtils.isNotEmpty(sixCode)){
                String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);//通过五位码获取组织id
                if(StringUtils.isNotEmpty(orgId)){
                    //获取组织
                    BaseOrg org=baseOrgService.findById("",orgId);
                    if(org!=null){
                        //获取主机头
                        name=org.getOrgName();
                        String hostAddress=org.getHostAddress();
                        if(StringUtils.isEmpty(hostAddress)){
                            return ResultGenerator.genErrorSetMsgErrorResult1("该企业主机头不存在!");
                        }
                        //获取区域服务器地址
                        areaServiceUrl=org.getAreaServiceUrl()+SystemConstant.AREA_SERVICE_PORT;
                        return ResultGenerator.genSuccessResult1(name+"#"+hostAddress,fileAddress,areaServiceUrl);
                    }else{
                        return ResultGenerator.genErrorSetMsgErrorResult1("该企业不存在，请先创建企业信息!");
                    }
                }else{
                    return ResultGenerator.genErrorSetMsgErrorResult1("五位码不正确，请重新获取!");
                }
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult1("param_required");
            }
        }else{
            return ResultGenerator.genErrorSetMsgErrorResult1("param_required");
        }
    }
    @PostMapping("/login")
    public Result login(@RequestBody BaseUser baseUser, HttpServletRequest request) {
        log.info(baseUser.getUserName());
        log.info(baseUser.getPassWord());
        BaseUser newBaseUser =null;
        try {
            String passWord= AES128Util.encrypt(baseUser.getPassWord(), aesKey, ivVal);
            newBaseUser = baseUserService.findByNameAndPassWord("",baseUser.getUserName(),passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newBaseUser != null) {
            String Token = JwtTokenUtil.generateToken(baseUser.getUserName());
            String encryptToken = Token;

            newBaseUser.setToken(encryptToken);
            redisUtil.setEx(SystemConstant.DEFAULT_REDIS_KEY,"system_user_login_"+baseUser.getUserName(), com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString(),24*60*60,TimeUnit.SECONDS);
            return ResultGenerator.genSuccessResult(newBaseUser);
        } else {
            return ResultGenerator.API_CODE_PASSWORD_ERROR();
        }
    }

    @PostMapping("/appLogin")
    @com.springboot.configurer.JSON(type = DaQxYhda.class, filter = "sixCode,pageNo,pageSize,jdbh,dqbh,bzxx,yhzw,khbh,wxh,qxzbz,qxzbh,qxz,yhmmx")
    public Result appLogin(@RequestBody DaQxYhda daYgda, HttpServletRequest request) {
//        long start = System.currentTimeMillis();
        Result result=null;
        DaQxYhda newBaseUser = null;
        List<DaQxYhda> daQxYhdaList=null;
        if(StringUtils.isEmpty(daYgda.getYgbh())||StringUtils.isEmpty(daYgda.getYhmm())){
            result= ResultGenerator.genErrorSetMsgErrorResult("用户名或密码为空，请输入!");
        }else{
            String passWord = SHA256Util.mySHA256(daYgda.getYhmm());
            if (StringUtils.isNotEmpty(daYgda.getSixCode())) {
                String sixCode=daYgda.getSixCode();
                String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);//通过六位码获取组织id
                if(StringUtils.isNotEmpty(orgId)){
                    //获取该企业冻结状态
                    String state=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_state");
                    if(StringUtils.isNotEmpty(state)&&"1".equals(state)){
                        //验证设备是否存在
                        BaseEquip baseEquip=baseEquipService.findByUuidAndOrgId("",daYgda.getUuid(),orgId);
                        if(baseEquip==null){
                            result= ResultGenerator.genErrorSetMsgErrorResult("请重新注册!");
                        }else{
                            String Token = JwtTokenUtil.generateToken(daYgda.getYgbh());
                            try{
                                //首先从redis中获取所有用户信息
                                String baseUserJson=redisUtil.get(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE);
                                if(StringUtils.isNotEmpty(baseUserJson)){
                                    daQxYhdaList=JSONArray.parseArray(baseUserJson,DaQxYhda.class);
                                    if(daQxYhdaList!=null&&daQxYhdaList.size()>0){
                                        for (DaQxYhda daQxYhda:daQxYhdaList) {
                                            String yhbh=daQxYhda.getYhbh();
                                            if(StringUtils.isEmpty(yhbh)||"null".equals(yhbh)||yhbh==null){
                                                newBaseUser = daQxYhdaService.findByNameAndPassWord(sixCode, daYgda.getYgbh(), passWord);
                                                if(newBaseUser==null){
                                                    return  ResultGenerator.API_CODE_PASSWORD_ERROR();
                                                }
                                                //同步用户
                                                daQxYhdaList=daYgdaService.getList(sixCode);
                                                redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
                                                break;
                                            }else{
                                                if(daQxYhda.getYhbh().toLowerCase().equals(daYgda.getYgbh().toLowerCase())){
                                                    newBaseUser=daQxYhda;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

//                                String newBaseUserStr=redisUtil.get(sixCode,sixCode+"_system_user_login_"+daYgda.getYgbh().toLowerCase());
                                if(newBaseUser!=null){
                                    //缓存中有
//                                    newBaseUser=JSONObject.parseObject(newBaseUserStr,DaQxYhda.class);
                                    //验证用户名和密码
                                    String oldUsername=newBaseUser.getYhbh();
                                    String oldPassword=newBaseUser.getYhmm();
                                    //新的用户名和密码
                                    if(StringUtils.isNotEmpty(oldUsername)&&StringUtils.isNotEmpty(oldPassword)){
                                        if(oldUsername.toLowerCase().equals(daYgda.getYgbh().toLowerCase())){
                                            if(oldPassword.equals(passWord)){
                                                log.info("从redis中获取登录用户的信息=====>"+com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString());
                                            }else{
                                                return  ResultGenerator.API_CODE_PASSWORD_ERROR();//redis中存在该用户，密码错误
                                            }
                                        }else{
                                            return  ResultGenerator.API_CODE_PASSWORD_ERROR();//redis中存在该用户，用户名错误
                                        }
                                    }else{//redis中存在该用户，但是用户名或者密码为空了
                                        newBaseUser = daQxYhdaService.findByNameAndPassWord(sixCode, daYgda.getYgbh(), passWord);
                                        if(newBaseUser==null){
                                            return  ResultGenerator.API_CODE_PASSWORD_ERROR();
                                        }
                                        log.info("从数据库中获取登录用户的信息=====>"+com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString());
                                        //同步用户
                                        daQxYhdaList=daYgdaService.getList(sixCode);
                                        redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
                                    }

                                }else{//redis缓存中没有
                                    newBaseUser = daQxYhdaService.findByNameAndPassWord(sixCode, daYgda.getYgbh(), passWord);//查询数据库中无该用户即用户名或密码可能错误
                                    if(newBaseUser==null){
                                        return  ResultGenerator.API_CODE_PASSWORD_ERROR();
                                    }
                                    log.info("从数据库中获取登录用户的信息=====>"+com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString());
                                    //同步用户
                                    daQxYhdaList=daYgdaService.getList(sixCode);
                                    redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
                                }
                                if (newBaseUser != null) {
                                    newBaseUser.setSixCode(sixCode);
                                    String encryptToken = Token;
                                    newBaseUser.setToken(encryptToken);
                                    newBaseUser.setExpiretime(String.valueOf(SystemDefines.JWT_EXPIRATION));
                                    newBaseUser.setYgbh(newBaseUser.getYhbh());
                                    newBaseUser.setYgmc(newBaseUser.getYhmc());

                                    newBaseUser= this.commonRedis(sixCode,daYgda.getYgbh(),passWord,newBaseUser);

                                    result= ResultGenerator.genSuccessResult(newBaseUser);
                                } else {
                                    result= ResultGenerator.API_CODE_PASSWORD_ERROR();
                                }
                            }catch(Exception e){
                                newBaseUser = daQxYhdaService.findByNameAndPassWord(sixCode, daYgda.getYgbh(), passWord);
                                if(newBaseUser==null){
                                    return  ResultGenerator.API_CODE_PASSWORD_ERROR();
                                }
                                //同步用户
                                daQxYhdaList=daYgdaService.getList(sixCode);
                                redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
                                if (newBaseUser != null) {
                                    newBaseUser.setSixCode(sixCode);
                                    String encryptToken = Token;
                                    newBaseUser.setToken(encryptToken);
                                    newBaseUser.setExpiretime(String.valueOf(SystemDefines.JWT_EXPIRATION));
                                    newBaseUser.setYgbh(newBaseUser.getYhbh());
                                    newBaseUser.setYgmc(newBaseUser.getYhmc());

                                    newBaseUser= this.commonRedis(sixCode,daYgda.getYgbh(),passWord,newBaseUser);

                                    result= ResultGenerator.genSuccessResult(newBaseUser);
                                } else {
                                    result= ResultGenerator.API_CODE_PASSWORD_ERROR();
                                }
//                                log.error("网络原因,请重新登录", e);
//                                throw new CustomeException("网络原因,请重新登录");
                            }
                        }
                    }else{
                        result= ResultGenerator.genErrorSetMsgErrorResult("该企业已经被冻结,请联系客服!");
                    }
                }else{
                    result= ResultGenerator.genErrorSetMsgErrorResult("五位码错误,无法授权!");
                }
            } else {
                result= ResultGenerator.genErrorSetMsgErrorResult("五位码为空!");
            }
        }
        return result;
    }
    public DaQxYhda commonRedis(String sixCode,String ygbh,String passWord,DaQxYhda newBaseUser){
        //获取部门名称
//        String departJson="";如果中途更换redis数据源，用户未重新登录，使用redis数据源仍然是以前的数据源
        String departJson=redisUtil.get(sixCode,sixCode+SystemConstant.REDIS_DEPART_TABLE);
        if(StringUtils.isNotEmpty(departJson)){
            List<DaBmda> daBmdaList=JSONObject.parseArray(departJson,DaBmda.class);
            if(daBmdaList!=null&&daBmdaList.size()>0){
                for (DaBmda daBmda : daBmdaList) {
                    if(newBaseUser.getBmbh().equals(daBmda.getBmbh())){
                        newBaseUser.setBmmc(daBmda.getBmmc());
                        break;
                    }
                }
            }
        }else{
            //同步部门
            List<DaBmda> daBmdaList=daBmdaService.getList(sixCode);
            if(daBmdaList!=null&&daBmdaList.size()>0){
                for (DaBmda daBmda : daBmdaList) {
                    if(newBaseUser.getBmbh().equals(daBmda.getBmbh())){
                        newBaseUser.setBmmc(daBmda.getBmmc());
                        break;
                    }
                }
                redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_DEPART_TABLE,JSONObject.toJSONString(daBmdaList));
            }
        }
//        redisUtil.set(sixCode,sixCode+"_system_user_login_"+ygbh.toLowerCase()+"_"+passWord,com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString());
        redisUtil.set(sixCode,sixCode+"_system_user_login_"+ygbh.toLowerCase(),com.alibaba.fastjson.JSON.toJSON(newBaseUser).toString());
        //获取前台机器码
//        newBaseUser=this.setYwPosParm(sixCode,newBaseUser);//后期不需要了，
        //获取机档案参数
        newBaseUser=this.setDaQjCsb(sixCode,newBaseUser);
        //获取功能权限
//        newBaseUser=this.setGnqx(sixCode,newBaseUser);//后期不需要了
        //获取模块权限
        newBaseUser=this.setMokuaiAuth(sixCode,newBaseUser);

        return newBaseUser;

    }
    public DaQxYhda setYwPosParm(String sixCode,DaQxYhda newBaseUser){
//        long start = System.currentTimeMillis();
//        log.info("开始时间："+start+ "毫秒");
//        String posparmStr="";
        String posparmStr=redisUtil.get(sixCode,sixCode+"_"+newBaseUser.getQtjqm()+"_ywPosParm");
        if(StringUtils.isEmpty(posparmStr)){
            YwPosParm ywPosParm=ywPosParmService.findByJqbh(sixCode,newBaseUser.getQtjqm(),newBaseUser.getBmbh());
            if(ywPosParm!=null){
                log.info("pos参数是从数据库中获取ywPosParm为:"+ywPosParm);
                redisUtil.set(sixCode,sixCode+"_"+newBaseUser.getQtjqm()+"_ywPosParm",com.alibaba.fastjson.JSON.toJSON(ywPosParm).toString());
                newBaseUser.setYwPosParm(ywPosParm);
            }
        }else{
            //获取是否允许负库存
//            String isAllowStock="";
            log.info("pos参数是从redis缓存中获取key为:"+sixCode+"_"+newBaseUser.getQtjqm()+"_ywPosParm");
            String isAllowStock=redisUtil.get(sixCode,sixCode+"_"+newBaseUser.getBmbh()+"_isAllowStock");
            if(StringUtils.isNotEmpty(isAllowStock)){
                log.info("是否允许负库存是从redis缓存中获取key为:"+sixCode+"_"+newBaseUser.getBmbh()+"_isAllowStock");
                YwPosParm bean=JSONObject.parseObject(posparmStr,YwPosParm.class);
                bean.setIsAllowStock(isAllowStock);
                //set部门名称
                String bmdaJson=redisUtil.get(sixCode,sixCode+"_007_table");
                log.info("部门档案是从redis缓存中获取key为:"+sixCode+"_007_table");
                CommonGetRedisValue.setBmmc(sixCode,bmdaJson,newBaseUser,bean);
                newBaseUser.setYwPosParm(bean);
            }else{//缓存中无是否允许负库存状态
                isAllowStock=ywPosParmService.isAllowStock(sixCode,newBaseUser.getBmbh());
                log.info("是否允许负库存是从数据库中获取isAllowStock为:"+isAllowStock);
                redisUtil.set(sixCode,sixCode+"_"+newBaseUser.getBmbh()+"_isAllowStock",isAllowStock);
                YwPosParm bean=JSONObject.parseObject(posparmStr,YwPosParm.class);
                bean.setIsAllowStock(isAllowStock);
                //set部门名称
                String bmdaJson=redisUtil.get(sixCode,sixCode+"_007_table");
                log.info("部门档案是从redis缓存中获取key为:"+sixCode+"_007_table");
                CommonGetRedisValue.setBmmc(sixCode,bmdaJson,newBaseUser,bean);
                newBaseUser.setYwPosParm(bean);
            }
        }
//        long end = System.currentTimeMillis();
//        log.info("set，耗时：" + (end - start) + "毫秒");
        return newBaseUser;
    }
    public DaQxYhda setDaQjCsb(String sixCode,DaQxYhda newBaseUser){
//        long start = System.currentTimeMillis();
//        log.info("开始时间："+start+ "毫秒");
//        String posparmStr="";

        String posparmStr=redisUtil.get(sixCode,sixCode+"_daQjCsb");
        if(StringUtils.isEmpty(posparmStr)){
            Object object=daQjCsbService.findBean(sixCode);
            if(object!=null){
                log.info("全局参数是从数据库中获取object为:"+object);
                ObjectMapper mapper = new ObjectMapper();
                String jsonstr = null;
                try {
                    jsonstr = mapper.writeValueAsString(object);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                redisUtil.set(sixCode,sixCode+"_daQjCsb",jsonstr);
                newBaseUser.setDaQjCsb(object);
            }
        }else{
            //获取是否启用版型
            log.info("全局参数是从redis缓存中获取key为:"+sixCode+"_daQjCsb");
            JSONObject json=JSONObject.parseObject(posparmStr);
//            String goodsBxProperty="";
            String goodsBxProperty=redisUtil.get(sixCode,sixCode+"_goodsBxProperty");
            if(StringUtils.isNotEmpty(goodsBxProperty)){
                String qybz="";
                if("0".equals(goodsBxProperty)||"1".equals(goodsBxProperty)){
                }else{
                    JSONObject bxjson=JSONObject.parseObject(goodsBxProperty);
                    if(bxjson!=null){
                        qybz=bxjson.getString("qybz");
                    }
                }
                //版型参数
                JSONObject keyjson=json.getJSONObject("goods_property#spbx");
                if(keyjson!=null){
                    keyjson.put("qybz",qybz);
                }else{
                    DaQjCsbKeyJson daQjCsbKeyJson=new DaQjCsbKeyJson();
                    daQjCsbKeyJson.setCsmc("");
                    daQjCsbKeyJson.setBzxx("商品属性管理");
                    daQjCsbKeyJson.setQybz(qybz);
                    json.put("goods_property#spbx",JSONObject.parseObject(JSONObject.toJSONString(daQjCsbKeyJson)));
                }

            }else{//缓存中无商品版型是否启用状态
                DaSpBxbt daSpBxbt=daSpBxdaService.findSystemQybz(sixCode);
                redisUtil.set(sixCode,sixCode+"_goodsBxProperty",daSpBxbt.getQybz());
                log.info("是否启用版型是从数据库中获取goodsBxProperty为:"+daSpBxbt.getQybz());

                JSONObject keyjson=json.getJSONObject("goods_property#spbx");
                if(keyjson!=null){
                    keyjson.put("qybz",daSpBxbt.getQybz());
                }else{
                    DaQjCsbKeyJson daQjCsbKeyJson=new DaQjCsbKeyJson();
                    daQjCsbKeyJson.setCsmc("");
                    daQjCsbKeyJson.setBzxx("商品属性管理");
                    daQjCsbKeyJson.setQybz(daSpBxbt.getQybz());
                    json.put("goods_property#spbx",JSONObject.parseObject(JSONObject.toJSONString(daQjCsbKeyJson)));
                }
            }
            String posFklx=json.getString("01#pos_fklx");
            if(StringUtils.isEmpty(posFklx)){
                Object object=daQjCsbService.findBean(sixCode);
                ObjectMapper mapper = new ObjectMapper();
                String jsonstr = null;
                try {
                    jsonstr = mapper.writeValueAsString(object);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                redisUtil.set(sixCode,sixCode+"_daQjCsb",jsonstr);
                newBaseUser.setDaQjCsb(object);
            }else{
                newBaseUser.setDaQjCsb(json);
            }
        }
//        long end = System.currentTimeMillis();
//        log.info("set，耗时：" + (end - start) + "毫秒");
        return newBaseUser;
    }
    public DaQxYhda setGnqx(String sixCode,DaQxYhda newBaseUser){
//        long start = System.currentTimeMillis();
//        log.info("开始时间："+start+ "毫秒");
        //获取全部功能权限
        List<GnqxJson> gnqxJsonList=daQxZydaService.findAll(sixCode,newBaseUser);
        newBaseUser.setGnqxList(gnqxJsonList);
//        long end = System.currentTimeMillis();
//        log.info("set，耗时：" + (end - start) + "毫秒");
        return newBaseUser;
    }
    public DaQxYhda setMokuaiAuth(String sixCode,DaQxYhda newBaseUser) {
//        long start = System.currentTimeMillis();
//        log.info("开始时间："+start+ "毫秒");
        Map<String,String> map=null;
        if(newBaseUser!=null){
            //从redis中通过yhbh获取groupId
            String groupId="";
            String userJson=redisUtil.get(sixCode,sixCode+ SystemConstant.REDIS_YHDA_TABLE);
            if(StringUtils.isNotEmpty(userJson)){
                List<DaQxYhda> userJsonList= JSONArray.parseArray(userJson,DaQxYhda.class);
                if(userJsonList!=null&&userJsonList.size()>0){
                    for (DaQxYhda daQxYhda:userJsonList) {
                        if(newBaseUser.getYhbh().toLowerCase().equals(daQxYhda.getYhbh().toLowerCase())){
                            groupId=daQxYhda.getGroupId();
                            break;
                        }
                    }
                }
            }
            String moKuaiJson="";
            if(StringUtils.isNotEmpty(groupId)){
                moKuaiJson=redisUtil.get(sixCode,sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupId);
                if(StringUtils.isNotEmpty(moKuaiJson)){
                    JSONObject moKuaiJsonObject=JSONObject.parseObject(moKuaiJson);
                    map=daYgdaService.getMap(sixCode,moKuaiJsonObject);
                    log.info("模块权限是从redis中获取key为:"+sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupId);
                }else{
                    map=daYgdaService.findMoKuaiAuth(sixCode,newBaseUser.getYgbh());
                    redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupId,com.alibaba.fastjson.JSON.toJSON(map).toString());
                }
            }else{
                map=new HashMap<String,String>();
                map.put("errormsg","该用户没有所有模块的权限!");
            }
            if(map!=null){
                if("0".equals(map.get("CR"))||"0".equals(map.get("CT"))||"0".equals(map.get("DC"))||"0".equals(map.get("DR"))||"0".equals(map.get("PF"))||"0".equals(map.get("PT"))
                        ||"0".equals(map.get("LC"))||"0".equals(map.get("LT"))||"0".equals(map.get("QC"))||"0".equals(map.get("QT"))||"0".equals(map.get("KP"))||"0".equals(map.get("KPJZ"))
                        ||"0".equals(map.get("SY"))||"0".equals(map.get("POS"))||"0".equals(map.get("XS"))||"0".equals(map.get("XF"))||"0".equals(map.get("FK"))||"0".equals(map.get("JF"))
                        ||"0".equals(map.get("KHDZD"))||"0".equals(map.get("GYSDZD"))||"0".equals(map.get("SPDA"))||"0".equals(map.get("BMDA"))||"0".equals(map.get("KHDA"))||"0".equals(map.get("GYSDA"))||"0".equals(map.get("YGDA"))){
                    map.put("errormsg","此功能没有权限，请向管理员申请权限!");
                }else{
                    map.put("errormsg","");
                }
            }else{
                map=new HashMap<String,String>();
                map.put("errormsg","该用户没有所有模块的权限!");
            }
        }
        newBaseUser.setMoKuaiAuth(map);
//        long end = System.currentTimeMillis();
//        log.info("set，耗时：" + (end - start) + "毫秒");
        return newBaseUser;
    }

    @PostMapping("/asycSpdamx")
    public Result asycSpdamx(@RequestBody String jsons) {

        //获取五位码
        Result result=null;
        if(StringUtils.isNotEmpty(jsons)) {
            JSONObject json = JSONObject.parseObject(jsons);
            String orgId = json.getString("orgId");
            if(StringUtils.isNotEmpty(orgId)){
                String sixCode = redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId);
                if(StringUtils.isNotEmpty(sixCode)){
                    //从redis中获取同步状态
                    try{
                        asycData(sixCode);
                    }catch(Exception e){
                        log.error(e.getMessage(),e);
                    }
                    result= ResultGenerator.genSuccessResult();
                }else{
                    result= ResultGenerator.genErrorSetMsgErrorResult("五位码为空!");
                }
            }
        }else{
            result= ResultGenerator.genParamsErrorResult();
        }

        return result;
    }
    class ImportTask implements Runnable {

        private String sixCode;
        public ImportTask(String sixCode) {
            this.sixCode = sixCode;
        }

        @Override
        public synchronized void run() {

            asycData(sixCode);
        }
    }
    class ThreadQuery implements Callable<List> {

        private String sixCode;//查询条件 根据条件来定义该类的属性

        private int pageNo;//当前页数

        private int num;//每页查询多少条

        private List<DaSpBxda> daSpBxdaList;
        private List<DaSpYsda> daSpYsdaList;
        private List<DaSpCmzb> daSpCmzbList;
        private List<DaSpCmdm> daSpCmdmList;
        private List<DaSpCmbt> daSpCmbtList;
        private List list;//每次分页查出来的数据


        public  ThreadQuery(String sixCode,int pageNo,int num,List<DaSpBxda> daSpBxdaList,List<DaSpYsda> daSpYsdaList,List<DaSpCmzb> daSpCmzbList,List<DaSpCmdm> daSpCmdmList,List<DaSpCmbt> daSpCmbtList) {
            this.sixCode=sixCode;
            this.pageNo=pageNo;
            this.num=num;
            this.daSpBxdaList=daSpBxdaList;
            this.daSpYsdaList=daSpYsdaList;
            this.daSpCmzbList=daSpCmzbList;
            this.daSpCmdmList=daSpCmdmList;
            this.daSpCmbtList=daSpCmbtList;

        }

        @Override
        public List call() throws Exception {
            //返回数据给Future
            list=daSpSpdaMxService.getList(sixCode,pageNo,num);
            int d=list.size();
            int b=daSpBxdaList.size();
            int y=daSpYsdaList.size();
            int z=daSpCmzbList.size();
            int m=daSpCmdmList.size();
            int bt=daSpCmbtList.size();
            if(list!=null&&d>0){
                YwCgRkdmxJson ywCgRkdmxJson=new YwCgRkdmxJson();
                for (int i = 0; i < d; i++) {
                    DaSpSpDaJson bean= (DaSpSpDaJson) list.get(i);
                    String bxbh=bean.getBxbh();
                    String yslsh=bean.getYslsh();
                    String ysbh=bean.getYsbh();
                    if(StringUtils.isEmpty(bxbh)||"-".equals(bxbh)){
                        bean.setBxmc("-");
                    }else{
                        if(daSpBxdaList!=null&&b>0){
                            ok:
                            for (int j = 0; j < b; j++) {
                                String bxbh1=daSpBxdaList.get(j).getBxbh();
                                if(bxbh.equals(bxbh1)){
                                    bean.setBxmc(daSpBxdaList.get(j).getBxmc());
                                    break ok ;
                                }
                            }
                        }
                    }
                    if(StringUtils.isEmpty(ysbh)||"-".equals(ysbh)){
                        bean.setYsmc("-");
                    }else{
                        if(daSpYsdaList!=null&&y>0){
                            ok1:
                            for (int t = 0; t < y; t++) {
                                String yslsh1=daSpYsdaList.get(t).getYslsh();
                                if(yslsh.equals(yslsh1)){
                                    bean.setYsmc(daSpYsdaList.get(t).getYsmc());
                                    break ok1 ;
                                }
                            }
                        }
                    }
                    if(daSpCmzbList!=null&&z>0){
                        for (int j = 0; j < z; j++) {
                            ywCgRkdmxJson= (YwCgRkdmxJson) ywCgRkdmxJson.clone();
                            ywCgRkdmxJson.setCmzbh(bean.getCmzbm());
                            ywCgRkdmxJson.setCm(bean.getCmdmlwz());
                            ywCgRkdmxJson=getSecondVersionNewList(ywCgRkdmxJson, daSpCmzbList, daSpCmdmList, daSpCmbtList);
                            if(ywCgRkdmxJson!=null){
                                bean.setCmbt(ywCgRkdmxJson.getCmbt());
                            }
                        }
                    }
                }



                //放入redis缓存中
                RedisTemplate<String, String> redisTemplate=redisDBConfig.getRedisTemplateById(sixCode);
                redisTemplate.executePipelined(new RedisCallback<Long>() {
                    @Override
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();

                            try {
                            for (int h = 0; h <d ; h++) {
                                DaSpSpDaJson bean= (DaSpSpDaJson) list.get(h);
                                final byte[] value = ProtoStuffUtil.serialize(bean);
                                String key = sixCode+"_"+bean.getTmbh()+"_daspmx";
                                log.error("executePipelined===========>"+key);
//                                String oldkey=sixCode+"_"+bean.getTmbh();
//                                connection.del(oldkey.getBytes());
//                                log.info("executePipelined====del=======>"+oldkey);
                                connection.set(key.getBytes(),value);
                            }
                            connection.closePipeline();
                        } catch (Exception e) {
                            log.error("executePipelined同步商品明细发生异常", e);
                        }
                        return null;
                    }
                });
            }
            log.info("call===========>"+Thread.currentThread().getName()+"====执行任务完成！");
            return list;
        }
    }

    public void asycData(String sixCode){
        long start = System.currentTimeMillis();
        log.info("开始时间"+start);
        //集合总条数
        int count =daSpSpdaMxService.findCount(sixCode);
        int num = 200000;//一次查询多少条
        //需要查询的次数
        int times = count / num;
        if (count % num != 0) {
            times = times + 1;
        }
        int pageNo=1;
        //将集合切分的段数(2*CPU的核心数)
        List<DaSpBxda> daSpBxdaList=daSpBxdaService.findList(sixCode);
        RedisTemplate<String, String> redisTemplate=redisDBConfig.getRedisTemplateById(sixCode);
        if(daSpBxdaList!=null&&daSpBxdaList.size()>0){
            int t=daSpBxdaList.size();
            //放入redis缓存中
            redisTemplate.executePipelined(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.openPipeline();

                    try {
                        for (int i = 0; i <t ; i++) {
                            DaSpBxda bean= daSpBxdaList.get(i);
                            final byte[] value = ProtoStuffUtil.serialize(bean);
                            String key = sixCode+"_"+bean.getBxbh()+"_bxda";
                            log.info("DaSpBxda===========>"+key);
                            connection.set(key.getBytes(),value);
                        }
                        connection.closePipeline();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }
        List<DaSpYsda> daSpYsdaList=daSpYsdaService.findList(sixCode);
        if(daSpYsdaList!=null&&daSpYsdaList.size()>0){
            int y=daSpYsdaList.size();
            //放入redis缓存中
            redisTemplate.executePipelined(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.openPipeline();

                    try {
                        for (int i = 0; i <y ; i++) {
                            DaSpYsda bean= daSpYsdaList.get(i);
                            final byte[] value = ProtoStuffUtil.serialize(bean);
                            String key = sixCode+"_"+bean.getYslsh()+"_ysda";
                            log.info("DaSpBxda===========>"+key);
                            connection.set(key.getBytes(),value);
                        }
                        connection.closePipeline();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }
        //往redis插入系统默认的条码截取方案规则
        SystemTmfa1 systemTmfa=daSpTmfaszPpService.getSystemTmbh(sixCode);
        if(systemTmfa!=null){
            redisUtil.set(sixCode,sixCode+"_systemtm", JSON.toJSONString(systemTmfa));
            log.info("systemTmfa===========>"+JSON.toJSONString(systemTmfa));
        }
        //获取品牌条码方案集合
        List<DaSpTmfaszPpJson> pPTmbhList=daSpTmfaszPpService.getPpTmbhList(sixCode);
        redisUtil.set(sixCode,sixCode+"_pPtmfa",JSON.toJSONString(pPTmbhList));
        log.info("pPTmbhList===========>"+JSON.toJSONString(pPTmbhList));

        //获取附加条码集合getPpTmbhList
        List<FjTmbhJson1> fjTmbhJsonList=daSpTmfaszPpService.getAllFjTmbh(sixCode);
        int p=fjTmbhJsonList.size();
        //放入redis缓存中
        redisTemplate.executePipelined(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();

                try {
                    for (int i = 0; i <p ; i++) {
                        FjTmbhJson1 bean= fjTmbhJsonList.get(i);
                        final byte[] value = ProtoStuffUtil.serialize(bean);
                        String key = sixCode+"_"+bean.getFjtm()+"_fj";
                        log.info("FjTmbhJson===========>"+key);
                        connection.set(key.getBytes(),value);
                    }
                    connection.closePipeline();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });


        //获取所有的尺码组
        List<DaSpCmzb> daSpCmzbList=daSpCmzbService.getList(sixCode);
        //获取所有的尺码代码
        List<DaSpCmdm> daSpCmdmList=daSpCmdmService.getList(sixCode);
        //获取所有的尺码标题
        List<DaSpCmbt> daSpCmbtList=daSpCmbtService.getList(sixCode);

        List<Callable<List>> tasks = new ArrayList<>();
        List<List<DaSpSpDaJson>> result = new ArrayList<>();//返回结果
        for (int i = 0; i < times; i++) {
            Callable<List> qfe = new ThreadQuery(sixCode,pageNo, num,daSpBxdaList,daSpYsdaList,daSpCmzbList,daSpCmdmList,daSpCmbtList);
            tasks.add(qfe);
            pageNo++;
        }
        //定义固定长度的线程池  防止线程过多
        ExecutorService executorService = Executors.newFixedThreadPool(30);

        //Future用于获取结果
        try {
            List<Future<List>> futures=executorService.invokeAll(tasks);
            //处理线程返回结果
            if(futures!=null&&futures.size()>0){
                for (Future<List> future:futures){
                    result.addAll(future.get());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
//            redisUtil.delete(sixCode+"_asyc_state");
            executorService.shutdown();//关闭线程池
        }
        //获取全局参数
        Object object=daQjCsbService.findBean(sixCode);
        if(object!=null){
            redisUtil.set(sixCode,sixCode+"_daQjCsb",com.alibaba.fastjson.JSON.toJSON(object).toString());
        }
        //获取模块权限
        //获取组id
        List<String> groupIdList=daYgdaService.getGroupIds(sixCode);
        if(groupIdList!=null&&groupIdList.size()>0){
            int m=groupIdList.size();
            for (int i = 0; i < m; i++) {
                //根据groupId获取模块权限
                Map<String,String> map=daYgdaService.findMoKuaiAuth1(sixCode,groupIdList.get(i));
                redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupIdList.get(i),com.alibaba.fastjson.JSON.toJSON(map).toString());
            }
        }
        //同步用户
        List<DaQxYhda> daQxYhdaList=daYgdaService.getList(sixCode);
        redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
        long end = System.currentTimeMillis();
        log.info("线程查询数据用时:"+(end-start)+"ms");
        //同步部门
        List<DaBmda> daBmdaList=daBmdaService.getList(sixCode);
        redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_DEPART_TABLE,com.alibaba.fastjson.JSON.toJSON(daBmdaList).toString());

        long end1 = System.currentTimeMillis();
        log.info("完成总任务，耗时：" + (end1 - start) + "毫秒");
        log.info("thread id======="+Thread.currentThread().getName());

    }
    public YwCgRkdmxJson getSecondVersionNewList(YwCgRkdmxJson bean, List<DaSpCmzb> daSpCmzbList, List<DaSpCmdm> daSpCmdmList, List<DaSpCmbt> daSpCmbtList) {
        //一个采购入库单下的不同款可能用的是不同的尺码标题
        //通过尺码组编号和尺码编号查询尺码标题
        if(daSpCmzbList!=null&&daSpCmzbList.size()>0){
            int z=daSpCmzbList.size();
            ok:
            for (int j = 0; j <z ; j++) {
                DaSpCmzb daSpCmzb=daSpCmzbList.get(j);
                if(daSpCmzb.getCmzbm().equals(bean.getCmzbh())){
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
        return bean;
    }
    /**
     * 上传文件
     * version 版本号
     * file  上传文件
     * type  0:上传gwt 1:上传apk
     **/
    @PostMapping(value="/uploadFile")
    public Result uploadFile(MultipartFile file,String version,String type){
        Result result=null;
        if(StringUtils.isNotEmpty(version)&&file!=null){
            String newfilepath="";
            String fileAddress1="";
            newfilepath=filepath+version+"/";
            String name=FileUpLoad.uploadFile(file,newfilepath);
            //保存数据库
            BaseUpload baseUpload=new BaseUpload();
            baseUpload.setVersion(version);
            //获取服务器地址
            fileAddress1=fileAddress+"/ymtfile/file/"+version+"/"+name;
            baseUpload.setUrl(fileAddress1);
            baseUpload.setStarttime(JBDate.getNowDate(new Date().getTime()));
            baseUpload.setEndtime(JBDate.getNowDate(new Date().getTime()));
            if(StringUtils.isEmpty(type)){
                type="0";
            }
            baseUpload.setType(type);
            baseUploadService.insertBean("",baseUpload);
            result=ResultGenerator.genSuccessResult(baseUpload.getUrl());

        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("参数为空!");
        }

        return result;
    }
    /**
     * 查询最新版本
     * version 版本号
     * type 0:wgt1:apk
     **/
    @PostMapping("/findFile")
    public Result findFile(@RequestBody String jsons){
        Result result=null;
        BaseUpload bean=null;
        String type="";
        if(StringUtils.isNotEmpty(jsons)){
            JSONObject jsonObject=JSONObject.parseObject(jsons);
            if(jsonObject!=null){
                type=jsonObject.getString("type");
            }
        }
        if(StringUtils.isEmpty(type)){
            type="0";
        }
        bean=baseUploadService.findNewBean("",type);
        if(bean!=null){
            result=ResultGenerator.genSuccessResult(bean);
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("服务器上无上传文件，请先上传!");
        }
        return result;
    }
    /**
     *删除最新版本
     * version 版本号
     **/
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestBody String jsons){
        Result result=null;
        JSONObject jsonObject=JSONObject.parseObject(jsons);
        String id=jsonObject.getString("id");
        baseUploadService.deleteById("",id);
        result=ResultGenerator.genSuccessResult();
        return result;
    }
    /**
     * 获取主机头接口
     */
    public String getHostAddress(String orgId) {
        String hostAddress="";
        //从缓存中获取主机头
        BaseOrg baseOrg=baseOrgService.findById("",orgId);
        if(baseOrg!=null){
            hostAddress= baseOrg.getHostAddress();
        }else{
            hostAddress="";
        }
        return hostAddress;
    }
    //后台功能权限接口
    @PostMapping("/getGnAuth")
    public Result getGnAuth(@RequestBody String jsons) {
        Result result=null;
        if(StringUtils.isNotEmpty(jsons)) {
            Map<String,Object> param=new HashMap<String,Object>();
            JSONObject json = JSONObject.parseObject(jsons);
            String sixCode = json.getString("sixCode");
            String code=json.getString("code");
            String qtjqm=json.getString("qtjqm");
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            if(StringUtils.isNotEmpty(sixCode)&&StringUtils.isNotEmpty(code)){
                if(daYgda!=null){
                    Object obj=daQxZydaService.newFindAll(sixCode,code,daYgda);
                    Map<String,String> mokuaiAuth=daYgdaService.findGnMoKuaiAuth(sixCode, daYgda,code,qtjqm);
                    param.put("gnqxList",obj);
                    param.put("moKuaiAuth",mokuaiAuth);
                    result= ResultGenerator.genSuccessResult(param);
                }
            }else{
                result= ResultGenerator.genErrorSetMsgErrorResult("有参数为空,请检查!");
            }
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult("jsons参数为空,请检查!");
        }

        return result;
    }
    @PostMapping("/checkHost")
    public Result checkHost(@RequestBody String jsons) {
        Map<String, String> param=new HashMap<String,String>();
        Result result=null;
        JSONObject jsonObject=JSONObject.parseObject(jsons);
        String host=jsonObject.getString("host");
        String orgId=jsonObject.getString("orgId");
        String areaAddress=jsonObject.getString("areaAddress");
        String areaServiceUrl=jsonObject.getString("areaServiceUrl");
        if(StringUtils.isEmpty(areaServiceUrl)){
            return ResultGenerator.genErrorSetMsgErrorResult("区域服务器地址不能为空!");
        }
        if(StringUtils.isNotEmpty(orgId)&&StringUtils.isNotEmpty(host)){
            //获取sixCode
            String sixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId);
            if(StringUtils.isNotEmpty(sixCode)){
                //校验主机头是否重复
//                List<String> hostList=baseOrgService.getHsotList("");
//                if(hostList!=null&&hostList.size()>0){
//                    if(hostList.contains(host)){
//                      return ResultGenerator.genErrorSetMsgErrorResult("主机头已经存在!");
//                    }
//                }
                //调用a8接口
                String newAreaServiceUrl=areaServiceUrl+SystemConstant.AREA_SERVICE_PORT;
                String a8Result= CommonGetRedisValue.checkHost(sixCode,host,orgId,newAreaServiceUrl);

                if(StringUtils.isNotEmpty(a8Result)){
                    JSONObject a8ResultJson= JSONObject.parseObject(a8Result);
                    if(a8ResultJson!=null){
                        String code=a8ResultJson.getString("code");
                        if(StringUtils.isNotEmpty(code)&&"Y".equals(code)){
                            //修改组织信息
                            BaseOrg org= baseOrgService.findById("",orgId);
                            if(org!=null){
                                org.setHostAddress(host);
                                org.setAddress(host);
                                org.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
                                org.setAreaServiceUrl(areaServiceUrl);
                                org.setAreaAddress(areaAddress);
                                baseOrgService.updateById("",org);
                                //缓存组织信息
                                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,org.getId()+"_data", JSONObject.toJSONString(org));
                                boolean flag=this.saveBatch(sixCode,areaServiceUrl);

                                areaServiceUrl=org.getAreaServiceUrl();
                                //删除各个应用上redis数据源连接
                                param.put("orgId",org.getId());
                                BaseRedisDatasource baseRedisDatasource=baseRedisDatasourceService.findOne("",param);
                                if(baseRedisDatasource!=null){
                                    CommonGetRedisValue.updateRedisDataSource(sixCode,baseRedisDatasource,areaServiceUrl);
                                }
                                //删除各个应用数据库连接
                                BaseDatasource baseDatasource=baseDatasourceService.getBeanByOrgId("",org.getId());
                                if(baseDatasource!=null){
                                    CommonGetRedisValue.updateDataSource(sixCode,baseDatasource,areaServiceUrl);
                                }
                                result= ResultGenerator.genSuccessResult(flag);
                            }else{
                                result=ResultGenerator.genErrorSetMsgErrorResult("组织信息为空!");
                            }
                        }else if(StringUtils.isNotEmpty(code)&&"N".equals(code)){
                            String message=a8ResultJson.getString("message");
                            result=ResultGenerator.genErrorSetMsgErrorResult(message);
                        }
                    }
                }else{
                    result=ResultGenerator.genErrorSetMsgErrorResult("调用a8提供的主机头接口出现异常!");
                }
            }else{
                result=ResultGenerator.genErrorSetMsgErrorResult("redis缓存中五位码为空，请重新刷新认证信息!");
            }
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("参数中host,orgId有空值!");
        }
        return result;
    }

    public boolean saveBatch(String sixCode,String areaServiceUrl){
        //a8getToken地址，a8redis地址，ymt分享地址，a8条码扫描地址
        boolean flag=false;
        List<DaQjCsb> daQjCsbList=new ArrayList<DaQjCsb>();
        //a8getToken地址
        //判断参数数据是否存在
        DaQjCsb daQjCsb1=daQjCsbService.getBean(sixCode,"YMT_TOKEN_URL","YMT");
        if(daQjCsb1!=null){//做修改操作
            daQjCsb1.setBzxx(areaServiceUrl+":8112/api/a8/getToken");
            daQjCsbService.updateByBean(sixCode,daQjCsb1);
            flag=true;
        }else{
            DaQjCsb bean1=new DaQjCsb();
            //获取最大流水号加1
            Integer lsh1=daQjCsbService.findMaxLsh(sixCode);

            bean1.setLsh(lsh1);
            bean1.setCsbh("YMT_TOKEN_URL");
            bean1.setCsmc("易码通TOKEN_URL");
            bean1.setCsfl("YMT");
            bean1.setQybz("1");
            bean1.setBzxx(areaServiceUrl+":8112/api/a8/getToken");
            daQjCsbList.add(bean1);
            flag=daQjCsbService.saveBatch(sixCode,daQjCsbList);
            daQjCsbList.remove(bean1);
        }
        //a8redis地址
        DaQjCsb daQjCsb2=daQjCsbService.getBean(sixCode,"YMT_URL","YMT");
        if(daQjCsb2!=null){//做修改操作
            daQjCsb2.setBzxx(areaServiceUrl+":8112/api/a8/a8Redis");
            daQjCsbService.updateByBean(sixCode,daQjCsb2);
            flag=true;
        }else{
            DaQjCsb bean2=new DaQjCsb();
            //获取最大流水号加1
            Integer lsh2=daQjCsbService.findMaxLsh(sixCode);
            bean2.setLsh(lsh2);
            bean2.setCsbh("YMT_URL");
            bean2.setCsmc("易码通URL");
            bean2.setCsfl("YMT");
            bean2.setQybz("1");
            bean2.setBzxx(areaServiceUrl+":8112/api/a8/a8Redis");
            daQjCsbList.add(bean2);
            flag=daQjCsbService.saveBatch(sixCode,daQjCsbList);
            daQjCsbList.remove(bean2);
        }

//        //a8redis分享地址
//        DaQjCsb daQjCsb3=daQjCsbService.getBean(sixCode,"YMT_SHARE_URL","ymt");
//        if(daQjCsb3!=null){//做修改操作
//            daQjCsb3.setBzxx(areaServiceUrl+":1001");
//            daQjCsbService.updateByBean(sixCode,daQjCsb3);
//            flag=true;
//        }else{
//            DaQjCsb bean3=new DaQjCsb();
//            //获取最大流水号加1
//            Integer lsh3=daQjCsbService.findMaxLsh(sixCode);
//            bean3.setLsh(lsh3);
//            bean3.setCsbh("YMT_SHARE_URL");
//            bean3.setCsmc("易码通分享");
//            bean3.setCsfl("ymt");
//            bean3.setQybz("1");
//            bean3.setBzxx(areaServiceUrl+":1001");
//            daQjCsbList.add(bean3);
//            flag=daQjCsbService.saveBatch(sixCode,daQjCsbList);
//            daQjCsbList.remove(bean3);
//        }
        return flag;
    }

    @PostMapping("/test3")
    public Result test3() {
        long start = System.currentTimeMillis();
        log.info("开始时间："+start+ "毫秒");
//        String sixCode="294a6";
        String sixCode="t9153";
        String uuid="6e609915f0e1c4ff";
            String[] strArray=uuid.split(",");
            if(strArray!=null&&strArray.length>0){
                String str=strArray[0];
                String sn=str.substring(7,14);
                System.out.println(sn);
            }
//        System.out.println("1111111================>"+redisUtil.get(sixCode,sixCode+"_systemtm"));
//        System.out.println("2222222================>"+redisUtil.get(sixCode,sixCode+"_pPtmfa"));

        //获取全局参数
//        Object object=daQjCsbService.findBean(sixCode);
//        if(object!=null){
//            redisUtil.set(sixCode,sixCode+"_daQjCsb",com.alibaba.fastjson.JSON.toJSON(object).toString());
//        }
//        //获取模块权限
//        //获取组id
//        List<String> groupIdList=daYgdaService.getGroupIds(sixCode);
//        if(groupIdList!=null&&groupIdList.size()>0){
//            int m=groupIdList.size();
//            for (int i = 0; i < m; i++) {
//                //根据groupId获取模块权限
//                Map<String,String> map=daYgdaService.findMoKuaiAuth1(sixCode,groupIdList.get(i));
//                redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupIdList.get(i),com.alibaba.fastjson.JSON.toJSON(map).toString());
//            }
//        }
//        //同步用户
//        List<DaQxYhda> daQxYhdaList=daYgdaService.getList(sixCode);
//        redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_YHDA_TABLE,com.alibaba.fastjson.JSON.toJSON(daQxYhdaList).toString());
//
//        //同步部门
//        List<DaBmda> daBmdaList=daBmdaService.getList(sixCode);
//        redisUtil.set(sixCode,sixCode+SystemConstant.REDIS_DEPART_TABLE,com.alibaba.fastjson.JSON.toJSON(daBmdaList).toString());

//        List<DaSpYsda> list=daSpYsdaService.findList(sixCode);
//        List<DaSpSpda> list=daSpSpdaService.findList(sixCode);
//        long end1 = System.currentTimeMillis();
//        log.info("总共耗时1：" + (end1 - start) + "毫秒");
//        List<DaSpSpDaJson> list=daSpSpdaMxService.findList(sixCode);
//        String key="da_spda_mx";
//        String key="da_sp_ysda_table";
        String key="da_sp_spda_table";
        Map<String,String> param=new HashMap<String,String>();
//        if(list!=null&&list.size()>0){
//            for (DaSpSpda bean : list) {
//                String spkh=bean.getSpkh();
//                param.put(spkh,JSONObject.toJSONString(bean));
//            }
//
//        }
//        long end2 = System.currentTimeMillis();
//        log.info("总共耗时2：" + (end2 - end1) + "毫秒");

//        redisUtil.hPutAll(sixCode,key,param);
//        long end3 = System.currentTimeMillis();
//        log.info("总共耗时3：" + (end3 - end2) + "毫秒");

//        Map<Object,Object> map= redisUtil.hGetAll(sixCode,key);
//        long end4 = System.currentTimeMillis();
//        log.info("总共耗时4：" + (end4 - end3) + "毫秒");

//        List<Object> list1=redisUtil.hValues(sixCode,key);
//        log.info("get成功"+list1.size());
//        redisUtil.delete(sixCode,key);获取30万条一分钟
//        long end5 = System.currentTimeMillis();
//        log.info("总共耗时5：" + (end5 - end4) + "毫秒");
        List<Object> list=new ArrayList<Object>();
        list.add("b001");
        list.add("b002");
        list.add("b003");
        long end6 = System.currentTimeMillis();
        log.info("总共耗时6：" + (end6 - start) + "毫秒");
        return ResultGenerator.genSuccessResult(list);
    }


}
