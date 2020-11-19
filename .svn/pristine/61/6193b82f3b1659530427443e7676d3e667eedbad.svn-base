package com.springboot.web.org;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.JBDate;
import com.springboot.common.RedisUtil;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseUser;
import com.springboot.service.datasource.BaseDatasourceService;
import com.springboot.service.equip.BaseEquipService;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.redisdatasource.BaseRedisDatasourceService;
import com.springboot.service.user.BaseAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/org")
public class BaseOrgController {

    @Resource
    private BaseOrgService baseOrgService;
    @Resource
    private BaseDatasourceService baseDatasourceService;
    @Resource
    private BaseRedisDatasourceService baseRedisDatasourceService;
    @Resource
    private BaseEquipService baseEquipService;
    @Resource
    private BaseAuthService baseAuthService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/add")
    public Result add(@RequestBody BaseOrg baseOrg) {
        BaseUser baseUser = RequestUtils.getRequestUser();

        baseOrg.setCreateUserId(baseUser.getId());
        baseOrg.setCreateUser(baseUser.getUserName());
        baseOrg.setCreateTime(JBDate.getNowDate(new Date().getTime()));
        baseOrg.setUpdateUserId(baseUser.getId());
        baseOrg.setUpdateUser(baseUser.getUserName());
        baseOrg.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        baseOrg.setAreaServiceUrl(SystemConstant.AREA_SERVICE_URL);
        baseOrgService.insertBean("",baseOrg);
        //缓存该企业冻结状态
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_state",String.valueOf(baseOrg.getState()));
        //缓存组织信息
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_data", JSONObject.toJSONString(baseOrg));
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BaseOrg baseOrg) {
        //当有有缓存的时候，需要把对应的五位码也删除掉
        String sixCode=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId());
        redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId());
        if(StringUtils.isNotEmpty(sixCode)){
            redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);
        }
        //删除该企业冻结状态
        redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_state");
        //删除该企业reidsId
        redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+baseOrg.getId());
        //删除该企业数据信息
        redisUtil.delete(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_data");
        //删除数据源
        baseDatasourceService.deleteByOrgId("",baseOrg.getId());
        //删除redis数据源
        baseRedisDatasourceService.deleteByOrgId("",baseOrg.getId());
        //删除注册设备
        baseEquipService.deleteByOrgId("",baseOrg.getId());
        //删除许可证
        baseAuthService.deleteByOrgId("",baseOrg.getId());
        //删除组织
        baseOrgService.deleteById("",baseOrg.getId());
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BaseOrg baseOrg) {
        Result result=null;
        BaseUser baseUser = RequestUtils.getRequestUser();
        baseOrg.setUpdateUserId(baseUser.getId());
        baseOrg.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        baseOrgService.updateById("",baseOrg);
        //缓存该企业冻结状态
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_state",String.valueOf(baseOrg.getState()));
        //缓存组织信息
        redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseOrg.getId()+"_data", JSONObject.toJSONString(baseOrg));
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/list")
    @JSON(type = BaseOrg.class, include = "sixCode,id,orgName,updateTime,bigOrgName,hostAddress,areaServiceUrl,areaAddress,equiptCount,sixCodeCount,sourceCount,redisSourceCount,orgHostCount,state,count")
    public Result list(@RequestBody BaseOrg baseOrg) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        param.put("orgId",baseUser.getOrgId());
        if(StringUtils.isNotEmpty(baseOrg.getOrgName())){
            param.put("orgName",baseOrg.getOrgName());
        }
        if(StringUtils.isNotEmpty(baseOrg.getKey())){
            param.put("key",baseOrg.getKey());
        }
        Page<BaseOrg> page = new Page<BaseOrg>(baseOrg.getPageNo(), baseOrg.getPageSize());
        Page<BaseOrg> baseOrgList = baseOrgService.getPage("",page,baseOrg,param);


        return ResultGenerator.genSuccessResult(baseOrgList);
    }
    /**
     *
     * 区域服务器下拉框接口
     */
    @PostMapping("/getSelectList")
    public Result getSelectList(@RequestBody String jsons){
        Result result=null;
        //获取下拉框
        JSONObject jsonObject=JSONObject.parseObject(jsons);
        if(jsonObject!=null){
            String areaAddress=jsonObject.getString("areaAddress");
            if(StringUtils.isNotEmpty(areaAddress)){
                //通过区域地址获取区域服务器地址
                List<String> list=baseOrgService.getSelectList("",areaAddress);
                result=ResultGenerator.genSuccessResult(list);
            }else{
                result=ResultGenerator.genErrorSetMsgErrorResult("区域地址为空,请先选择区域地址!");
            }
        }
        return  result;
    }

}
