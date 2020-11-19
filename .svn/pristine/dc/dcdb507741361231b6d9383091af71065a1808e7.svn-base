package com.springboot.web.equip;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RedisUtil;
import com.springboot.common.RequestUtils;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.equip.BaseEquip;
import com.springboot.model.user.BaseAuth;
import com.springboot.model.user.BaseUser;
import com.springboot.service.equip.BaseEquipService;
import com.springboot.service.user.BaseAuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-04-23
 */
@Controller
@RequestMapping("/api/baseEquip")
public class BaseEquipController {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseEquipService baseEquipService;
    @PostMapping("/delete")
    public Result delete(@RequestBody String jsons) {

        if(StringUtils.isNotEmpty(jsons)){
            JSONObject json = JSONObject.parseObject(jsons);
            String ids=json.getString("ids");
            String[] idsArray=ids.split(",");
            if(idsArray!=null&&idsArray.length>0){
                List<String> idsList=new ArrayList<String>();
                for (int i = 0; i < idsArray.length; i++) {
                    idsList.add(idsArray[i]);
                }
                baseEquipService.batchDeleteByIds("",idsList);
                return ResultGenerator.genSuccessResult();
            }else{
                return ResultGenerator.genParamsErrorResult();
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/list")
    @JSON(type = BaseEquip.class, filter = "yhbh,pageNo,pageSize")
    public Result list(@RequestBody BaseEquip baseEquip) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(baseEquip.getEquipManufacturer())){
            param.put("equipManufacturer",baseEquip.getEquipManufacturer());
        }
        if(StringUtils.isNotEmpty(baseEquip.getOrgId())){
            param.put("orgId",baseEquip.getOrgId());
        }
        Page<BaseEquip> page = new Page<BaseEquip>(baseEquip.getPageNo(), baseEquip.getPageSize());
        Page<BaseEquip> baseEquipList = baseEquipService.getPage("",page,param);

        return ResultGenerator.genSuccessResult(baseEquipList);
    }
}

