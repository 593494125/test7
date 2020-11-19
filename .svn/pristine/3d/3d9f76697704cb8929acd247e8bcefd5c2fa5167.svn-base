package com.springboot.web.employee;


import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.employee.DaYgda;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.service.employee.DaYgdaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 员工档案 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-04-22
 */
@Controller
@RequestMapping("/api/daYgda")
@Slf4j
public class DaYgdaController {

    @Resource
    private DaYgdaService daYgdaService;
    @PostMapping("/findCgy")
    public Result findCgy(@RequestBody DaYgda daYgda) {
        Result result=null;
        //获取组织id
        List<DaYgdaJson> bean= daYgdaService.findCgy(daYgda.getSixCode());
        if(bean!=null){
            result= ResultGenerator.genSuccessResult(bean);
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
        }
        return result;
    }
}

