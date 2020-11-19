package com.springboot.web.wholesale;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.AddReturnJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.user.DaQxYhda;
import com.springboot.model.wholesale.YwPfRkdhz;
import com.springboot.model.wholesale.YwPfRkdhzQueryJson;
import com.springboot.service.wholesale.YwPfRkdhzService;
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
 * @since 2020-05-13
 */
@Controller
@RequestMapping("/api/ywPfRkdhz")
public class YwPfRkdhzController {

    @Resource
    private YwPfRkdhzService ywPfRkdhzService;

    @PostMapping("/list")

    @JSON(type = YwPfRkdhz.class, include = "pzh,bmbh,khbh,khdh,zjsl,zjje,cgfs,xxly,djlx,jzbz,skfs,lsjze,newkdsj,cgymc,bmmc,khmc")
    public Result list(@RequestBody YwPfRkdhz ywPfRkdhz) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywPfRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())&&ywPfRkdhz.getPageNo()!=null&&ywPfRkdhz.getPageSize()!=null){
            param.put("yhbh",daYgda.getYgbh());
            ywPfRkdhz.setPageSize(SystemConstant.pageSize);
            Page<YwPfRkdhz> page = new Page<YwPfRkdhz>(ywPfRkdhz.getPageNo(), ywPfRkdhz.getPageSize());
            Page<YwPfRkdhz> ywPfRkdhzList = ywPfRkdhzService.getPage(ywPfRkdhz.getSixCode(),page,ywPfRkdhz,param);
            if(ywPfRkdhzList.getRecords()!=null&&ywPfRkdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywPfRkdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/query_list")
    @JSON(type = YwPfRkdhz.class, include = "pzh,bmbh,khbh,khdh,zjsl,zjje,cgfs,xxly,djlx,jzbz,skfs,lsjze,newkdrq,cgymc,bmmc,khmc")
    public Result query_list(@RequestBody YwPfRkdhzQueryJson ywPfRkdhzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywPfRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywPfRkdhzQueryJson.getDjlx())&&ywPfRkdhzQueryJson.getPageNo()!=null&&ywPfRkdhzQueryJson.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywPfRkdhzQueryJson.setPageSize(SystemConstant.pageSize);
            Page<YwPfRkdhz> page = new Page<YwPfRkdhz>(ywPfRkdhzQueryJson.getPageNo(), ywPfRkdhzQueryJson.getPageSize());
            Page<YwPfRkdhz> ywPfRkdhzList = ywPfRkdhzService.getQueryPage(ywPfRkdhzQueryJson.getSixCode(),page,ywPfRkdhzQueryJson,param);
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
    @JSON(type = YwPfRkdhz.class, include = "pzh")
    public Result get_pzh(@RequestBody YwPfRkdhzQueryJson ywPfRkdhzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywPfRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywPfRkdhzQueryJson.getDjlx())){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            param.put("djlx",ywPfRkdhzQueryJson.getDjlx());
            List<String> list = ywPfRkdhzService.getPzh(ywPfRkdhzQueryJson.getSixCode(),param);
            return ResultGenerator.genSuccessResult(list);
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/add")
    @JSON(type = TImportErrorList.class, include = "msg")
    public Result add1(@RequestBody YwPfRkdhz ywPfRkdhz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        if(StringUtils.isEmpty(ywPfRkdhz.getCgy())){
            ywPfRkdhz.setCgy(daYgda.getYgbh());
        }
        ywPfRkdhz.setKdr(daYgda.getYgbh());
        if(StringUtils.isNotEmpty(ywPfRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywPfRkdhz.getBmbh())&&StringUtils.isNotEmpty(ywPfRkdhz.getKhbh())&&ywPfRkdhz.getKdrq()!=null&&ywPfRkdhz.getTmbhAndsl()!=null&&StringUtils.isNotEmpty(ywPfRkdhz.getDjlx())){
            AddReturnJson addReturnJson= ywPfRkdhzService.saveBean(ywPfRkdhz.getSixCode(),ywPfRkdhz);
            if(addReturnJson.getFlag()==1) {
                result = ResultGenerator.genSuccessResult();
            }else if(addReturnJson.getFlag()==2){
                result= ResultGenerator.genErrorResult(addReturnJson.getMsgList());
            }else{
                result= ResultGenerator.genErrorResult();
            }
        }else{
            result = ResultGenerator.genParamsErrorResult();
        }

        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody YwPfRkdhz ywPfRkdhz) {
        Result result=null;
        Integer flag= ywPfRkdhzService.deleteByPzh(ywPfRkdhz.getSixCode(),ywPfRkdhz.getPzh());
        if(flag>0){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }
}

