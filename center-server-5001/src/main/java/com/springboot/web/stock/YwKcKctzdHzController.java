package com.springboot.web.stock;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.AddReturnJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.stock.YwKcKctzdHz;
import com.springboot.model.stock.YwKcKctzdHzQueryJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.stock.YwKcKctzdHzService;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2020-05-14
 */
@Controller
@RequestMapping("/api/ywKcKctzdHz")
public class YwKcKctzdHzController {

    @Resource
    private YwKcKctzdHzService ywKcKctzdHzService;

    @PostMapping("/list")
    @JSON(type = YwKcKctzdHz.class, include = "pzh,tzbm,cgymc,sgdj,tzfs,zjsl,zjje,newtzrq,bmmc")
    public Result list(@RequestBody YwKcKctzdHz ywKcKctzdHz) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKctzdHz.getSixCode())&&ywKcKctzdHz.getPageNo()!=null&&ywKcKctzdHz.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywKcKctzdHz.setPageSize(SystemConstant.pageSize);
            Page<YwKcKctzdHz> page = new Page<YwKcKctzdHz>(ywKcKctzdHz.getPageNo(), ywKcKctzdHz.getPageSize());
            Page<YwKcKctzdHz> ywKcKctzdHzList = ywKcKctzdHzService.getPage(ywKcKctzdHz.getSixCode(),page,ywKcKctzdHz,param);
            if(ywKcKctzdHzList.getRecords()!=null&&ywKcKctzdHzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywKcKctzdHzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/query_list")
    @JSON(type = YwKcKctzdHz.class, include = "pzh,tzbm,cgymc,sgdj,tzfs,zjsl,zjje,newtzrq,bmmc")
    public Result query_list(@RequestBody YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKctzdHzQueryJson.getSixCode())&&ywKcKctzdHzQueryJson.getPageNo()!=null&&ywKcKctzdHzQueryJson.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywKcKctzdHzQueryJson.setPageSize(SystemConstant.pageSize);
            Page<YwKcKctzdHz> page = new Page<YwKcKctzdHz>(ywKcKctzdHzQueryJson.getPageNo(), ywKcKctzdHzQueryJson.getPageSize());
            Page<YwKcKctzdHz> ywPfRkdhzList = ywKcKctzdHzService.getQueryPage(ywKcKctzdHzQueryJson.getSixCode(),page,ywKcKctzdHzQueryJson,param);
            if(ywPfRkdhzList.getRecords()!=null&&ywPfRkdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywPfRkdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/get_pzh")
    @JSON(type = YwKcKctzdHz.class, include = "pzh")
    public Result get_pzh(@RequestBody YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKctzdHzQueryJson.getSixCode())){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            List<String> list = ywKcKctzdHzService.getPzh(ywKcKctzdHzQueryJson.getSixCode(),param);
            return ResultGenerator.genSuccessResult(list);
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/add")
    @JSON(type = TImportErrorList.class, include = "msg")
    public Result add1(@RequestBody YwKcKctzdHz ywKcKctzdHz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        if(StringUtils.isEmpty(ywKcKctzdHz.getCzy())){
            ywKcKctzdHz.setCzy(ywKcKctzdHz.getCzy());
        }
        if(StringUtils.isNotEmpty(ywKcKctzdHz.getSixCode())&&StringUtils.isNotEmpty(ywKcKctzdHz.getTzbm())&&ywKcKctzdHz.getTzrq()!=null&&StringUtils.isNotEmpty(ywKcKctzdHz.getSgdj())&&ywKcKctzdHz.getTmbhAndsl()!=null){
            AddReturnJson addReturnJson= ywKcKctzdHzService.saveBean(ywKcKctzdHz.getSixCode(),ywKcKctzdHz);
            if(addReturnJson.getFlag()==1) {
                result = ResultGenerator.genSuccessResult();
            }else if(addReturnJson.getFlag()==2){
                result= ResultGenerator.genErrorResult(addReturnJson.getMsgList());
            }else{
                result= ResultGenerator.genErrorResult();
            }
        }else{
            result=ResultGenerator.genParamsErrorResult();
        }
        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody YwKcKctzdHz ywKcKctzdHz) {
        Result result=null;
        Integer flag= ywKcKctzdHzService.deleteByPzh(ywKcKctzdHz.getSixCode(),ywKcKctzdHz.getPzh());
        if(flag>0){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }
}

