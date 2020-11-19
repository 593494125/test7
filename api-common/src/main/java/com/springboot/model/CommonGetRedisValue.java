package com.springboot.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.common.CustomeException;
import com.springboot.common.HttpPostUtil;
import com.springboot.common.SystemConstant;
import com.springboot.core.ResultGenerator;
import com.springboot.jwt.JwtTokenUtil;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.model.department.DaBmda;
import com.springboot.model.posparm.YwPosParm;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.model.user.DaQxYhda;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class CommonGetRedisValue {
    //获取部门名称
    public static void setBmmc(String sixCode,String bmdaJson, DaQxYhda newBaseUser, YwPosParm bean){
        //set部门名称(暂时查数据库)
        List<DaBmda> daBmdaList = JSONArray.parseArray(bmdaJson,DaBmda.class);
        if(daBmdaList!=null&&daBmdaList.size()>0){
            int k=daBmdaList.size();
            for (int i = 0; i < k; i++) {
                String bmbh=daBmdaList.get(i).getBmbh();
                if(bmbh.equals(newBaseUser.getBmbh())){
                    bean.setBmmc(daBmdaList.get(i).getBmmc());
                    log.info("该数据是从redis缓存中获取bmbh,bmmc为:"+bmbh+"==="+daBmdaList.get(i).getBmmc());
                    break;
                }
            }
        }
    }
    //调用a8接口获取主机头
    public static String checkHost(String sixCode,String host, String orgId,String address){
        String result="";
        try{
            Map<String,String> param=new HashMap<String,String>();
            String url=host+"/Face/Execute";
            String json="{\"Method\":\"SetFiveCode\",\"sixCode\":\""+sixCode+"\","+"\"redisFaceUrl\":\""+address+"\","+"\"token\":\"2\",\"ygbh\":\"system\"}";
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //调用区域服务器更新redis数据源
    public static String updateRedisDataSource(String sixCode, BaseRedisDatasource baseRedisDatasource,String areaServiceUrl){
        String result="";
        Map<String,String> param=new HashMap<String,String>();
        //获取token
        String token = JwtTokenUtil.generateToken("admin");
        param.put("token",token);
        try{
            String url=areaServiceUrl+":8113/api/baseRedisDatasource/update";
//            String url="http://localhost"+":8015/api/baseRedisDatasource/update";
            String json="{\"id\":\""+baseRedisDatasource.getId()+"\","+"\"orgId\":\""+baseRedisDatasource.getOrgId()+"\","+"\"host\":\""+baseRedisDatasource.getHost()+"\","+"\"port\":\""+baseRedisDatasource.getPort()+"\","+"\"password\":\""+baseRedisDatasource.getPassword()+"\","+"\"dbIndex\":\""+baseRedisDatasource.getDbIndex()+"\","+"\"sixCode\":\""+"\"}";
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomeException("主应用服务器异常!");
        }
        try{
            String url=areaServiceUrl+":8112/api/baseRedisDatasource/update";
//            String url="http://localhost"+":8017/api/baseRedisDatasource/update";
            String json="{\"id\":\""+baseRedisDatasource.getId()+"\","+"\"orgId\":\""+baseRedisDatasource.getOrgId()+"\","+"\"host\":\""+baseRedisDatasource.getHost()+"\","+"\"port\":\""+baseRedisDatasource.getPort()+"\","+"\"password\":\""+baseRedisDatasource.getPassword()+"\","+"\"dbIndex\":\""+baseRedisDatasource.getDbIndex()+"\","+"\"sixCode\":\""+"\"}";
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomeException("a8应用服务器异常!");
        }

//        try{
////            String url=SystemConstant.AREA_SERVICE_URL+":8019/api/baseRedisDatasource/update";
//            String url="http://localhost"+":8020/api/baseRedisDatasource/update";
//            String json="{\"id\":\""+baseRedisDatasource.getId()+"\","+"\"orgId\":\""+baseRedisDatasource.getOrgId()+"\","+"\"host\":\""+baseRedisDatasource.getHost()+"\","+"\"port\":\""+baseRedisDatasource.getPort()+"\","+"\"password\":\""+baseRedisDatasource.getPassword()+"\","+"\"dbIndex\":\""+baseRedisDatasource.getDbIndex()+"\","+"\"sixCode\":\""+"\"}";
//            result= HttpPostUtil.doPostByJson(url,json,param);
//        }catch(Exception e){
//            e.printStackTrace();
//            throw new CustomeException("版本应用服务器异常!");
//        }
        return result;
    }
    //调用区域服务器更新数据源
    public static String updateDataSource(String sixCode, BaseDatasource baseDatasource, String areaServiceUrl){
        String result="";
        Map<String,String> param=new HashMap<String,String>();
        //获取token
        String token = JwtTokenUtil.generateToken("admin");
        param.put("token",token);
        try{
            String url=areaServiceUrl+":8113/api/datasource/update";
//            String url="http://localhost"+":8015/api/datasource/update";
            String json=JSONObject.toJSONString(baseDatasource);
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomeException("主应用服务器异常!");
        }
        try{
            String url=areaServiceUrl+":8112/api/datasource/update";
//            String url="http://localhost"+":8017/api/datasource/update";
            String json=JSONObject.toJSONString(baseDatasource);
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomeException("a8应用服务器异常!");
        }

        try{
            String url=SystemConstant.AREA_SERVICE_URL+":8019/api/datasource/update";
//            String url="http://localhost"+":8020/api/datasource/update";
            String json=JSONObject.toJSONString(baseDatasource);
            result= HttpPostUtil.doPostByJson(url,json,param);
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomeException("版本应用服务器异常!");
        }
        return result;
    }
}
