package com.springboot.web.supplier;


import com.springboot.common.RequestUtils;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.supplier.DaGysda;
import com.springboot.model.supplier.DaGysdaJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.supplier.DaGysdaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 供应商档案 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Controller
@RequestMapping("/api/daGysda")
public class DaGysdaController {

    @Resource
    private DaGysdaService daGysdaService;
    @PostMapping("/findDaGysda")
    public Result findByDaGysda(@RequestBody DaGysda daGysda) {

        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        List<DaGysdaJson> bean= daGysdaService.findByDaGysda(daGysda.getSixCode(),daYgda.getYgbh());
        if(bean!=null){
            result= ResultGenerator.genSuccessResult(bean);
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
        }
        return result;
    }
}

