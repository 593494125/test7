package com.springboot.web.purchase;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgDdhz;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.purchase.YwCgDdhzService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * (采购退货申请清单)采购清单 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Controller
@RequestMapping("/api/ywCgDdhz")
public class YwCgDdhzController {

    @Resource
    private YwCgDdhzService ywCgDdhzService;


    @PostMapping("/list")
    @JSON(type = YwCgDdhz.class, filter = "yhbh,sixCode,pageNo,pageSize")
    public Result list(@RequestBody YwCgDdhz ywCgDdhz) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();
        param.put("pageNo",String.valueOf(ywCgDdhz.getPageNo()));
        param.put("pageSize",String.valueOf(ywCgDdhz.getPageSize()));
        param.put("yhbh",daYgda.getYgbh());
//        param.put("dqzt","F");
        Page<YwCgDdhz> page = new Page<YwCgDdhz>(ywCgDdhz.getPageNo(), ywCgDdhz.getPageSize());
        Page<YwCgDdhz> ywCgDdhzList = ywCgDdhzService.getPage(ywCgDdhz.getSixCode(),page,param);

        return ResultGenerator.genSuccessResult(ywCgDdhzList);
    }
    @PostMapping("/add")
    public Result add(@RequestBody YwCgDdhz ywCgDdhz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Boolean flag= ywCgDdhzService.saveBean(ywCgDdhz.getSixCode(),ywCgDdhz);
        if(flag==true){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }

}

