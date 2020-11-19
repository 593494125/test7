package com.springboot.web.barcode;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.JBDate;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.BasePageForm;
import com.springboot.model.barcode.BarcodeDevice;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseUser;
import com.springboot.service.barcode.BarcodeDeviceService;
import com.springboot.service.org.BaseOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-06-01
 */
@Controller
@RequestMapping("/api/barcodeDevice")
public class BarcodeDeviceController {

    @Resource
    private BarcodeDeviceService barcodeDeviceService;

    @PostMapping("/add")
    public Result add(@RequestBody BarcodeDevice barcodeDevice) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        barcodeDevice.setCreateTime(JBDate.getNowDate(new Date().getTime()));
        barcodeDevice.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        barcodeDeviceService.insertBean("",barcodeDevice);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody BarcodeDevice barcodeDevice) {
        barcodeDeviceService.deleteById("",barcodeDevice.getId());
        //当有有权限的时候，需要把对应的权限都删除掉
        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BarcodeDevice barcodeDevice) {
        Result result=null;
        BaseUser baseUser = RequestUtils.getRequestUser();
        barcodeDevice.setUpdateTime(JBDate.getNowDate(new Date().getTime()));
        barcodeDeviceService.updateById("",barcodeDevice);
        result= ResultGenerator.genSuccessResult();
        return result;
    }
    @PostMapping("/findByMax")
    public Result findByMax(@RequestBody BarcodeDevice barcodeDevice) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        BarcodeDevice bean=barcodeDeviceService.findByMax("",barcodeDevice.getMac());

        return ResultGenerator.genSuccessResult(bean);
    }
    @PostMapping("/list")
    public Result list(@RequestBody BarcodeDevice barcodeDevice) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        barcodeDevice.setPageSize(SystemConstant.pageSize);
        Page<BarcodeDevice> page = new Page<BarcodeDevice>(barcodeDevice.getPageNo(), barcodeDevice.getPageSize());
        Page<BarcodeDevice> barcodeDevicepage = barcodeDeviceService.getPage("",page,barcodeDevice);


        return ResultGenerator.genSuccessResult(barcodeDevicepage);
    }

}

