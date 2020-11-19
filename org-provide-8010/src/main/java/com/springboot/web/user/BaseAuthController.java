package com.springboot.web.user;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.*;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseAuth;
import com.springboot.model.user.BaseUser;
import com.springboot.service.datasource.BaseDatasourceService;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.user.BaseAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-06-10
 */
@Controller
@RequestMapping("/api/baseAuth")
@Slf4j
public class BaseAuthController {


    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseAuthService baseAuthService;
    @Resource
    private BaseOrgService baseOrgService;
    @Resource
    private BaseDatasourceService baseDatasourceService;
    @PostMapping("/add")
    @JSON(type = BaseAuth.class, filter = "yhbh,pageNo,pageSize")
    public Result add(@RequestBody BaseAuth baseAuth) {
        Result result=null;
        BaseUser baseUser_op = RequestUtils.getRequestUser();
        if(StringUtils.isNotEmpty(baseAuth.getOrgId())){
            //验证数据源是否存在
            Integer count=baseDatasourceService.getCountByOrgId("",baseAuth.getOrgId());
            if(count!=null&&count>0){
                baseAuth.setState(1);//0:过期1：正常
                //生成六位码
                String sixCode= SixCode.getStringRandom(5);
                //将sixCode放入redis中,登录的时候通过六位码获取组织id判断是哪家企业
                String oldSixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,baseAuth.getOrgId());
                //先从a8全局参数表中获取五位码
                BaseAuth bean= baseAuthService.findByorgId("",baseAuth.getOrgId());
                if(StringUtils.isEmpty(oldSixCode)){
                    if(bean!=null){
                        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId(),bean.getSixCode());
                        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+bean.getSixCode(),bean.getOrgId());//通过六位码获取组织id
                        baseAuth.setSixCode(bean.getSixCode());
                    }else{
                        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseAuth.getOrgId(),sixCode);
                        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode,baseAuth.getOrgId());//通过六位码获取组织id
                        baseAuth.setSixCode(sixCode);
                    }

                }else{
                    if(bean!=null){
                        //直接返回数据库查的信息修改五位码测试地址就会同时覆盖

                    }else{
                        //数据库删除了，缓存中存在
                        baseAuth.setSixCode(oldSixCode);
                    }
                }
                if(bean!=null){
                    //向a8数据库插入五位码
                    baseAuthService.updateA8Bean(bean.getSixCode());
                    result= ResultGenerator.genSuccessResult(bean);
                }else{
                    baseAuth.setEffectStartTime(JBDate.getNowDate(new Date().getTime()));
                    baseAuthService.insertBean("",baseAuth);
                    //向a8数据库插入五位码
                    baseAuthService.updateA8Bean(baseAuth.getSixCode());//必须要有数据源，如果没有数据源不知道往哪一家客户表里面添加数据
                    result= ResultGenerator.genSuccessResult(baseAuth);
                }
            }else{
                result=ResultGenerator.genErrorSetMsgErrorResult("请先给该企业添加数据源,没有数据源生成五位码!");
            }
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("组织ID为空!");
        }
        return result;
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BaseAuth baseAuth) {
        //删除的同时删除五位码
        BaseAuth bean=baseAuthService.findById("",baseAuth.getId());
        if(bean!=null){
            String sixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId());
            redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId());
            if(StringUtils.isNotEmpty(sixCode)){
                redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);
            }
        }
        baseAuthService.deleteById("",baseAuth.getId());
        //当有有权限的时候，需要把对应的权限都删除掉
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BaseAuth baseAuth) {
        Result result=null;
        BaseUser baseUser_op = RequestUtils.getRequestUser();
        if(StringUtils.isNotEmpty(baseAuth.getId())){
            BaseAuth bean= baseAuthService.findById("",baseAuth.getId());
            BaseOrg org=baseOrgService.findById("",bean.getOrgId());
            if(org!=null){
                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId()+"_state",org.getState().toString());
            }
            String oldSixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId());
            if(StringUtils.isEmpty(oldSixCode)){
                if(bean!=null){
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId(),bean.getSixCode());
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+bean.getSixCode(),bean.getOrgId());//通过六位码获取组织id
                    log.info("新五位码__"+bean.getOrgId()+"======>"+bean.getSixCode());
                    baseAuth.setSixCode(bean.getSixCode());
                    baseAuthService.updateById("",baseAuth);
                }else{
                    //生成六位码
                    String sixCode= SixCode.getStringRandom(5);
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseAuth.getOrgId(),sixCode);
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode,baseAuth.getOrgId());//通过六位码获取组织id
                    System.out.println("新五位码__"+baseAuth.getOrgId()+"======>"+sixCode);
                    baseAuth.setSixCode(sixCode);
                    baseAuth.setEffectStartTime(JBDate.getNowDate(new Date().getTime()));
                    baseAuthService.insertBean("",baseAuth);
                }

            }else{
                if(bean!=null){
                    //直接返回数据库查的信息
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,bean.getOrgId(),bean.getSixCode());
                    redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+bean.getSixCode(),bean.getOrgId());//通过六位码获取组织id
                    baseAuth.setSixCode(bean.getSixCode());
                    baseAuthService.updateById("",baseAuth);
                }else{
                    //数据库删除了，缓存中存在
                    baseAuth.setSixCode(oldSixCode);
                    baseAuth.setEffectStartTime(JBDate.getNowDate(new Date().getTime()));
                    baseAuthService.insertBean("",baseAuth);
                    System.out.println("老五位码__"+baseAuth.getOrgId()+"======>"+oldSixCode);
                }
            }
            //向a8数据库插入五位码
            baseAuthService.updateA8Bean(baseAuth.getSixCode());

            result=ResultGenerator.genSuccessResult();
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("ID为空!");
        }
        return result;
    }
    @PostMapping("/list")
    @JSON(type = BaseAuth.class, filter = "yhbh,pageNo,pageSize")
    public Result list(@RequestBody BaseAuth baseAuth) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(baseAuth.getTitle())){
            param.put("title",baseAuth.getTitle());
        }
        if(StringUtils.isNotEmpty(baseAuth.getOrgId())){
            param.put("orgId",baseAuth.getOrgId());
        }
        Page<BaseAuth> page = new Page<BaseAuth>(baseAuth.getPageNo(), baseAuth.getPageSize());
        Page<BaseAuth> baseAuthList = baseAuthService.getPage("",page,param);

        return ResultGenerator.genSuccessResult(baseAuthList);
    }

}

