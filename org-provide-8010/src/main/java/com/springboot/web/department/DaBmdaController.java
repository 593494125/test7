package com.springboot.web.department;


import com.springboot.common.RequestUtils;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.department.DaBmda;
import com.springboot.model.department.DaBmdaJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.department.DaBmdaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门档案 前端控制器
 * </p>
 *
 * @author zjq
 * @since 2020-04-24
 */
@Controller
@RequestMapping("/api/daBmda")
public class DaBmdaController {


    @Resource
    private DaBmdaService daBmdaService;
    @PostMapping("/findBmda")
    public Result findByTmbh(@RequestBody DaBmda daBmda) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
//        String yhbh=redisUtil.get(daBmda.getSixCode()+"_"+daYgda.getYgbh())
        if(StringUtils.isNotEmpty(daBmda.getSixCode())){
//            List<DaBmdaJson> list= daBmdaService.findCgBmda(daBmda.getSixCode(),daYgda.getYhbh());;
            List<DaBmdaJson> list=null;
            if("CG".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findCgBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }else if("DBC".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findDbcBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }else if("DBR".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findDbrBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }else if("PF".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findPfBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }else if("KC".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findKcBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }else if("LSPOS".equalsIgnoreCase(daBmda.getCbJgFlag())){
                list= daBmdaService.findLsPosBmda(daBmda.getSixCode(),daYgda.getYgbh());
            }
            if(list!=null){
                result= ResultGenerator.genSuccessResult(list);
            }else{
                result= ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            result=ResultGenerator.genParamsErrorResult();
        }

        return result;
    }

}

