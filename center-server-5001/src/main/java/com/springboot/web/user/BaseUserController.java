package com.springboot.web.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.AES128Util;
import com.springboot.common.JBDate;
import com.springboot.common.RequestUtils;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.BasePageForm;
import com.springboot.model.user.BaseUser;
import com.springboot.service.datasource.BaseDatasourceService;
import com.springboot.service.user.BaseUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/10/10.
 */
@Controller
@RequestMapping("/api/user")
public class BaseUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private BaseDatasourceService baseDatasourceService;
    @Resource
    private BaseUserService baseUserService;

    @Value("${server.port}")
    private String port;
    @Value("${encrypt.aesKey}")
    private String aesKey;
    @Value("${encrypt.ivVal}")
    private String ivVal;
    @PostMapping("/add")
    public Result add(@RequestBody BaseUser baseUser) {
        Result result=null;
        BaseUser baseUser_op = RequestUtils.getRequestUser();
        baseUser.setCreateTime(JBDate.getNowDate(new Date().getTime()));
        baseUser.setCreateUserId(baseUser_op.getId());
        baseUser.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        baseUser.setUpdateUserId(baseUser_op.getId());
        //用户密码加密
        try {
            String password=baseUser.getPassWord();
            String encryptPassWord= AES128Util.encrypt(password, aesKey, ivVal);
            baseUser.setPassWord(encryptPassWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(baseUser.getOrgId())){
            baseUserService.insertBean("",baseUser);
            result=ResultGenerator.genSuccessResult();
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("用户所属组织为空!");
        }
        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody BaseUser baseUser) {
        baseUserService.deleteById("",baseUser.getId());
        //当有有权限的时候，需要把对应的权限都删除掉
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BaseUser baseUser) {
        Result result=null;
        BaseUser baseUser_op = RequestUtils.getRequestUser();
        baseUser.setUpdateUserId(baseUser_op.getId());
        baseUser.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        if(StringUtils.isNotEmpty(baseUser.getOrgId())){
            baseUserService.updateById("",baseUser);
            result=ResultGenerator.genSuccessResult();
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("用户所属组织为空!");
        }
        return result;
    }
    @PostMapping("/list")
    public Result list(@RequestBody BasePageForm basePageForm) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        param.put("orgId",baseUser.getOrgId());
        Page<BaseUser> page = new Page<BaseUser>(basePageForm.getPageNo(), basePageForm.getPageSize());
        Page<BaseUser> baseUserList = baseUserService.getPage("",page,param);

        return ResultGenerator.genSuccessResult(baseUserList);
    }
    @PostMapping("/check_password")
    public Result check_password(@RequestBody BaseUser baseUser) {
        BaseUser user=baseUserService.findByName("",baseUser.getUserName());
//        BaseUser user=baseUserService.selectOne(new EntityWrapper<BaseUser>().eq("userName", baseUser.getUserName()));
        if(user!=null){
            String oldPassword=user.getPassWord();//通过当前用户查询旧密码
            //用户密码加密
            try {
                String oldPassword1=baseUser.getPassWord();//用户输入的密码
                if(StringUtils.isNotEmpty(oldPassword1)){
                    String encryptPassWord= AES128Util.encrypt(oldPassword1, aesKey, ivVal);
                    if(oldPassword.equals(encryptPassWord)){
                        return ResultGenerator.genSuccessSetMsgResult("原密码正确!");
                    }else{
                        return ResultGenerator.genErrorSetMsgErrorResult("原密码不正确!");
                    }
                }else{
                    return ResultGenerator.genParamsErrorResult();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultGenerator.genErrorSetMsgErrorResult("该用户不存在!");
    }
    @PostMapping("/update_password")
    public Result update_password(@RequestBody BaseUser baseUser) {

        BaseUser user=baseUserService.findByName("",baseUser.getUserName());
//        BaseUser user=baseUserService.selectOne(new EntityWrapper<BaseUser>().eq("userName", baseUser.getUserName()));
        if(user!=null){
            //用户密码加密
            try {
                String newPassword=baseUser.getPassWord();//用户输入的密码
                if(StringUtils.isNotEmpty(newPassword)){
                    String encryptPassWord= AES128Util.encrypt(newPassword, aesKey, ivVal);
                    user.setPassWord(encryptPassWord);
                    baseUserService.updateById(user);
                    return ResultGenerator.genSuccessResult();
                }else{
                    return ResultGenerator.genParamsErrorResult();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultGenerator.genErrorSetMsgErrorResult("该用户不存在!");
    }
    @PostMapping(value="/findByUserName")
    public Result findByUserName(@RequestBody String jsons) {
        JSONObject json = JSONObject.parseObject(jsons);
        System.out.println("请求的端口号为=========》"+port);
        String count = baseUserService.findByUserName(json.get("id").toString(),json.get("name").toString());
        return ResultGenerator.genSuccessResult(count);
    }


}
