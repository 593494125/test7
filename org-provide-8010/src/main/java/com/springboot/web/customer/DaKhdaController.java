package com.springboot.web.customer;


import com.springboot.common.RequestUtils;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.customer.DaKhda;
import com.springboot.model.customer.DaKhdaJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.customer.DaKhdaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户档案 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
@Controller
@RequestMapping("/api/daKhda")
public class DaKhdaController {

    @Resource
    private DaKhdaService daKhdaService;
    @PostMapping("/findKhda")
    public Result findByTmbh(@RequestBody DaKhda daKhda) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
//        String yhbh=redisUtil.get(daBmda.getSixCode()+"_"+daYgda.getYgbh())
        List<DaKhdaJson> bean= daKhdaService.findKhda(daKhda.getSixCode(),daYgda.getYgbh());
        if(bean!=null){
            result= ResultGenerator.genSuccessResult(bean);
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
        }
        return result;
    }
}

