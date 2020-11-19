package com.springboot.web.bluetooth;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.bluetooth.BlueToothSetting;
import com.springboot.service.bluetooth.BlueToothSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-07-08
 */
@Controller
@RequestMapping("/api/blueToothSetting")
public class BlueToothSettingController {

    @Resource
    private BlueToothSettingService blueToothSettingService;
    @PostMapping("/add")
    public Result add(@RequestBody BlueToothSetting blueToothSetting) {
        Integer flag=blueToothSettingService.insertBean("",blueToothSetting);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BlueToothSetting blueToothSetting) {
        Integer flag=blueToothSettingService.deleteById("",blueToothSetting.getId());
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BlueToothSetting blueToothSetting) {
        Integer flag=blueToothSettingService.updateById("",blueToothSetting);
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/findByServiceTz")
    @JSON(type = BlueToothSetting.class, filter = "sixCode,yhbh,pageNo,pageSize")
    public Result findByMac(@RequestBody BlueToothSetting blueToothSetting) {
        Result result=null;
        BlueToothSetting bean = blueToothSettingService.findByServiceTz("",blueToothSetting);
        if(bean!=null){
            result=ResultGenerator.genSuccessResult(bean);
        }else{
            result=ResultGenerator.genErrorSetMsgErrorResult("无数据!");
        }
        return result;
    }
    @PostMapping("/getPage")
    public Result getPage(@RequestBody BlueToothSetting blueToothSetting) {
        Map<String,String> param=new HashMap<String,String>();
        Page<BlueToothSetting> page = new Page<BlueToothSetting>(blueToothSetting.getPageNo(), blueToothSetting.getPageSize());
        Page<BlueToothSetting> blueToothSettingList = blueToothSettingService.getPage("",page,param);
        return ResultGenerator.genSuccessResult(blueToothSettingList);
    }
    @PostMapping("/getList")
    @JSON(type = BlueToothSetting.class, filter = "yhbh,sixCode,pageNo,pageSize")
    public Result getList() {
        Map<String,String> param=new HashMap<String,String>();
        List<BlueToothSetting> blueToothSettingList = blueToothSettingService.getList("",param);
        return ResultGenerator.genSuccessResult(blueToothSettingList);
    }

}

