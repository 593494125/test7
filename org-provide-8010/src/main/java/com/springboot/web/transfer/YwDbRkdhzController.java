package com.springboot.web.transfer;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.AddReturnJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.transfer.YwDbRkdhz;
import com.springboot.model.transfer.YwDbRkdhzQueryJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.transfer.YwDbRkdhzService;
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
 * @since 2020-05-12
 */
@Controller
@RequestMapping("/api/ywDbRkdhz")
public class YwDbRkdhzController {

    @Resource
    private YwDbRkdhzService ywDbRkdhzService;

    @PostMapping("/list")
    @JSON(type = YwDbRkdhz.class, include = "pzh,bmbh1mc,bmbh2mc,khdh,zjsl,zjje,cgymc,cgfs,xxly,djlx,jzbz,newkdrq,lsjze")
    public Result list(@RequestBody YwDbRkdhz ywDbRkdhz) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywDbRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywDbRkdhz.getDjlx())&&ywDbRkdhz.getPageNo()!=null&&ywDbRkdhz.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywDbRkdhz.setPageSize(SystemConstant.pageSize);
            Page<YwDbRkdhz> page = new Page<YwDbRkdhz>(ywDbRkdhz.getPageNo(), ywDbRkdhz.getPageSize());
            Page<YwDbRkdhz> ywDbRkdhzList = ywDbRkdhzService.getPage(ywDbRkdhz.getSixCode(),page,ywDbRkdhz,param);
            if(ywDbRkdhzList.getRecords()!=null&&ywDbRkdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywDbRkdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/query_list")
    @JSON(type = YwDbRkdhz.class, include = "pzh,bmbh1mc,bmbh2mc,khdh,zjsl,zjje,cgymc,cgfs,xxly,djlx,jzbz,newkdrq,lsjze")
    public Result query_list(@RequestBody YwDbRkdhzQueryJson ywDbRkdhzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywDbRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywDbRkdhzQueryJson.getDjlx())&&ywDbRkdhzQueryJson.getPageNo()!=null&&ywDbRkdhzQueryJson.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywDbRkdhzQueryJson.setPageSize(SystemConstant.pageSize);
            Page<YwDbRkdhz> page = new Page<YwDbRkdhz>(ywDbRkdhzQueryJson.getPageNo(), ywDbRkdhzQueryJson.getPageSize());
            Page<YwDbRkdhz> ywDbRkdhzList = ywDbRkdhzService.getQueryPage(ywDbRkdhzQueryJson.getSixCode(),page,ywDbRkdhzQueryJson,param);
            if(ywDbRkdhzList.getRecords()!=null&&ywDbRkdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywDbRkdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/get_pzh")
    @JSON(type = YwDbRkdhz.class, include = "pzh")
    public Result get_pzh(@RequestBody YwDbRkdhzQueryJson ywDbRkdhzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywDbRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywDbRkdhzQueryJson.getDjlx())){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            param.put("djlx",ywDbRkdhzQueryJson.getDjlx());
            List<String> list = ywDbRkdhzService.getPzh(ywDbRkdhzQueryJson.getSixCode(),param);
            return ResultGenerator.genSuccessResult(list);
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/add")
    @JSON(type = TImportErrorList.class, include = "msg")
    public Result add1(@RequestBody YwDbRkdhz ywDbRkdhz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        if(StringUtils.isEmpty(ywDbRkdhz.getCgy())){
            ywDbRkdhz.setCgy(daYgda.getYgbh());
        }
        ywDbRkdhz.setKdr(daYgda.getYgbh());
        if(StringUtils.isNotEmpty(ywDbRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywDbRkdhz.getBmbh1())&&StringUtils.isNotEmpty(ywDbRkdhz.getBmbh2())&&ywDbRkdhz.getKdrq()!=null&&ywDbRkdhz.getTmbhAndsl()!=null&&StringUtils.isNotEmpty(ywDbRkdhz.getDjlx())){
            AddReturnJson addReturnJson= ywDbRkdhzService.saveBean(ywDbRkdhz.getSixCode(),ywDbRkdhz);
            if(addReturnJson.getFlag()==1) {
                result = ResultGenerator.genSuccessResult();
            }else if(addReturnJson.getFlag()==2){
                result= ResultGenerator.genErrorResult(addReturnJson.getMsgList());
            }else{
                result= ResultGenerator.genErrorResult();
            }
        }else{
            result= ResultGenerator.genParamsErrorResult();
        }

        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody YwDbRkdhz ywDbRkdhz) {
        Result result=null;
        Integer flag= ywDbRkdhzService.deleteByPzh(ywDbRkdhz.getSixCode(),ywDbRkdhz.getPzh());
        if(flag>0){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }

}

