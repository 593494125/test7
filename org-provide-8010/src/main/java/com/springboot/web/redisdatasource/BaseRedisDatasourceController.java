package com.springboot.web.redisdatasource;


import com.alibaba.fastjson.JSON;
import com.springboot.common.RedisDBConfig;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.CommonGetRedisValue;
import com.springboot.model.redisdatasource.BaseOrgRedisDatasource;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.model.user.BaseAuth;
import com.springboot.service.redisdatasource.BaseOrgRedisDatasourceService;
import com.springboot.service.redisdatasource.BaseRedisDatasourceService;
import com.springboot.service.user.BaseAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-07-22
 */
@Controller
@RequestMapping("/api/baseRedisDatasource")
@Slf4j
public class BaseRedisDatasourceController {


    @Resource
    private BaseRedisDatasourceService baseRedisDatasourceService;
    @Resource
    private BaseOrgRedisDatasourceService baseOrgRedisDatasourceService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisDBConfig redisDBConfig;
    @Resource
    private BaseAuthService baseAuthService;
    @PostMapping("/add")
    public Result add(@RequestBody BaseRedisDatasource baseRedisDatasource) {
        Result result=null;
        String orgId=baseRedisDatasource.getOrgId();
        baseRedisDatasourceService.insertBean("",baseRedisDatasource);

        BaseOrgRedisDatasource baseOrgRedisDatasource=new BaseOrgRedisDatasource();
        baseOrgRedisDatasource.setOrgId(orgId);
        baseOrgRedisDatasource.setRedisId(baseRedisDatasource.getId());
        baseOrgRedisDatasourceService.insertBean("",baseOrgRedisDatasource);

        //通过orgId获取sixCode
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+orgId, baseRedisDatasource.getId());
        //存储redis数据源
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_baseRedisDatasource", JSON.toJSON(baseRedisDatasource).toString());
        //创建数据库连接
        try {
            redisDBConfig.createRedisTemplate(baseRedisDatasource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result= ResultGenerator.genSuccessResult();
        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody BaseRedisDatasource baseRedisDatasource) {

        baseRedisDatasourceService.deleteById("",baseRedisDatasource);

        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BaseRedisDatasource baseRedisDatasource) {
        Result result=null;
        baseRedisDatasourceService.updateById("",baseRedisDatasource);
        //需要移除redis数据源中的key
//        String sixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,baseRedisDatasource.getOrgId());
        BaseAuth baseAuth =baseAuthService.findByorgId("",baseRedisDatasource.getOrgId());
        String sixCode="";
        if(baseAuth!=null){
            sixCode=baseAuth.getSixCode();
            if(StringUtils.isNotEmpty(sixCode)){
                Map<String, RedisTemplate<String, String>> redisTemplateMap= RedisDBConfig.redisTemplateMap;
                redisTemplateMap.remove(sixCode);

                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+baseRedisDatasource.getOrgId(), baseRedisDatasource.getId());
                //存储redis数据源
                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseRedisDatasource.getOrgId()+"_baseRedisDatasource", JSON.toJSON(baseRedisDatasource).toString());
                result= ResultGenerator.genSuccessResult();
            }else{
                result= ResultGenerator.genErrorSetMsgErrorResult("数据库中五位码为空!");
            }
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult("数据库中认证信息为空!");
        }

        return result;
    }
    @PostMapping("/detail")
    @com.springboot.configurer.JSON(type = BaseRedisDatasource.class, filter = "yhbh,pageNo,pageSize")
    public Result detail(@RequestBody BaseRedisDatasource baseRedisDatasource) {
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(baseRedisDatasource.getOrgId())){
            param.put("orgId",baseRedisDatasource.getOrgId());
        }
        BaseRedisDatasource bean = baseRedisDatasourceService.findOne("",param);

        return ResultGenerator.genSuccessResult(bean);
    }

    //验证redis数据库连接
    @PostMapping("/checkRedisDataSource")
    public Result checkRedisDataSource(@RequestBody BaseRedisDatasource baseRedisDatasource) {
        Result result=null;
        //连接本地的 Redis 服务
        try{
            String host=baseRedisDatasource.getHost();
            String port=baseRedisDatasource.getPort();
            String url="http://"+host+":"+port;
            Jedis jedis = new Jedis(url);
            jedis.auth(baseRedisDatasource.getPassword());
            //测试是否连接成功
            String ping = jedis.ping();
            if("PONG".equals(ping)){
                log.info("redis连接成功"+ping);
                result= ResultGenerator.genSuccessResult();
            }else{
                result= ResultGenerator.genErrorSetMsgErrorResult("失败");
            }
        }catch(Exception e){
            result= ResultGenerator.genErrorSetMsgErrorResult("失败");
        }
        return result;
    }
}

