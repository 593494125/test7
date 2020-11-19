package com.springboot.web.stock;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.AddReturnJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.stock.YwKcKcpcdHz;
import com.springboot.model.stock.YwKcKctzdHzQueryJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.stock.YwKcKcpcdHzService;
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
 * @since 2020-05-18
 */
@Controller
@RequestMapping("/api/ywKcKcpcdHz")
public class YwKcKcpcdHzController {
    @Resource
    private YwKcKcpcdHzService ywKcKcpcdHzService;



    @PostMapping("/list")
    @JSON(type = YwKcKcpcdHz.class, include = "pzh,tzbm,cgymc,sgdj,tzfs,zjsl,zjje,newtzrq,bmmc")
    public Result list(@RequestBody YwKcKcpcdHz ywKcKcpcdHz) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKcpcdHz.getSixCode())&&ywKcKcpcdHz.getPageNo()!=null&&ywKcKcpcdHz.getPageSize()!=null){
            param.put("yhbh",daYgda.getYgbh());
            ywKcKcpcdHz.setPageSize(SystemConstant.pageSize);
            Page<YwKcKcpcdHz> page = new Page<YwKcKcpcdHz>(ywKcKcpcdHz.getPageNo(), ywKcKcpcdHz.getPageSize());
            Page<YwKcKcpcdHz> ywKcKcpcdHzList = ywKcKcpcdHzService.getPage(ywKcKcpcdHz.getSixCode(),page,ywKcKcpcdHz,param);
            if(ywKcKcpcdHzList.getRecords()!=null&&ywKcKcpcdHzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywKcKcpcdHzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/query_list")
    @JSON(type = YwKcKcpcdHz.class, include = "pzh,tzbm,cgymc,sgdj,tzfs,zjsl,zjje,newtzrq,bmmc")
    public Result query_list(@RequestBody YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKctzdHzQueryJson.getSixCode())&&ywKcKctzdHzQueryJson.getPageNo()!=null&&ywKcKctzdHzQueryJson.getPageSize()!=null){
            param.put("yhbh",daYgda.getYgbh());
            ywKcKctzdHzQueryJson.setPageSize(SystemConstant.pageSize);
            Page<YwKcKcpcdHz> page = new Page<YwKcKcpcdHz>(ywKcKctzdHzQueryJson.getPageNo(), ywKcKctzdHzQueryJson.getPageSize());
            Page<YwKcKcpcdHz> ywKcKcpcdHzList = ywKcKcpcdHzService.getQueryPage(ywKcKctzdHzQueryJson.getSixCode(),page,ywKcKctzdHzQueryJson,param);
            if(ywKcKcpcdHzList.getRecords()!=null&&ywKcKcpcdHzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywKcKcpcdHzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/get_pzh")
    @JSON(type = YwKcKcpcdHz.class, include = "pzh")
    public Result get_pzh(@RequestBody YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywKcKctzdHzQueryJson.getSixCode())){
            param.put("yhbh",daYgda.getYgbh());
            List<String> list = ywKcKcpcdHzService.getPzh(ywKcKctzdHzQueryJson.getSixCode(),param);
            return ResultGenerator.genSuccessResult(list);
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/add")
    @JSON(type = TImportErrorList.class, include = "msg")
    public Result add1(@RequestBody YwKcKcpcdHz ywKcKcpcdHz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        if(StringUtils.isEmpty(ywKcKcpcdHz.getCzy())){
            ywKcKcpcdHz.setCzy(ywKcKcpcdHz.getCzy());
        }
        if(StringUtils.isNotEmpty(ywKcKcpcdHz.getSixCode())&&StringUtils.isNotEmpty(ywKcKcpcdHz.getTzbm())&&ywKcKcpcdHz.getTzrq()!=null&&StringUtils.isNotEmpty(ywKcKcpcdHz.getSgdj())&&ywKcKcpcdHz.getTmbhAndsl()!=null){
            AddReturnJson addReturnJson= ywKcKcpcdHzService.saveBean(ywKcKcpcdHz.getSixCode(),ywKcKcpcdHz);
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
    public Result delete(@RequestBody YwKcKcpcdHz ywKcKcpcdHz) {
        Result result=null;
        Integer flag= ywKcKcpcdHzService.deleteByPzh(ywKcKcpcdHz.getSixCode(),ywKcKcpcdHz.getPzh());
        if(flag>0){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }
}

