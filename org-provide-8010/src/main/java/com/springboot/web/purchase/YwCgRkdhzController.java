package com.springboot.web.purchase;


import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.AddReturnJson;
import com.springboot.model.error.TImportErrorList;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.model.purchase.YwCgRkdhzQueryJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.purchase.YwCgRkdhzService;
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
 * @since 2020-05-06
 */
@Controller
@RequestMapping("/api/ywCgRkdhz")
public class YwCgRkdhzController {

    @Resource
    private YwCgRkdhzService ywCgRkdhzService;



    @PostMapping("/list")
    @JSON(type = YwCgRkdhz.class, include = "pzh,zjsl,zjje,cgy,cgfs,xxly,djlx,bmbh,gysbh,lsjze,newkdrq,cgymc,bmmc,gysmc")
    public Result list(@RequestBody YwCgRkdhz ywCgRkdhz) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywCgRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())&&ywCgRkdhz.getPageNo()!=null&&ywCgRkdhz.getPageSize()!=null){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            ywCgRkdhz.setPageSize(SystemConstant.pageSize);
            Page<YwCgRkdhz> page = new Page<YwCgRkdhz>(ywCgRkdhz.getPageNo(), ywCgRkdhz.getPageSize());
            Page<YwCgRkdhz> ywCgDdhzList = ywCgRkdhzService.getPage(ywCgRkdhz.getSixCode(),page,ywCgRkdhz,param);
            if(ywCgDdhzList.getRecords()!=null&&ywCgDdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywCgDdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/query_list")
    @JSON(type = YwCgRkdhz.class, include = "pzh,zjsl,zjje,cgy,cgfs,xxly,djlx,bmbh,gysbh,lsjze,newkdrq,cgymc,bmmc,gysmc,jzbz")
    public Result query_list(@RequestBody YwCgRkdhzQueryJson ywCgRkdhzQueryJson) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywCgRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywCgRkdhzQueryJson.getDjlx())&&ywCgRkdhzQueryJson.getPageNo()!=null&&ywCgRkdhzQueryJson.getPageSize()!=null){
            param.put("yhbh",daYgda.getYgbh());
            ywCgRkdhzQueryJson.setPageSize(SystemConstant.pageSize);
            Page<YwCgRkdhz> page = new Page<YwCgRkdhz>(ywCgRkdhzQueryJson.getPageNo(), ywCgRkdhzQueryJson.getPageSize());
            Page<YwCgRkdhz> ywCgDdhzList = ywCgRkdhzService.getQueryPage(ywCgRkdhzQueryJson.getSixCode(),page,ywCgRkdhzQueryJson,param);
            if(ywCgDdhzList.getRecords()!=null&&ywCgDdhzList.getRecords().size()>0){
                return ResultGenerator.genSuccessResult(ywCgDdhzList);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/get_pzh")
    @JSON(type = YwCgRkdhz.class, include = "pzh")
    public Result get_pzh(@RequestBody YwCgRkdhzQueryJson ywCgRkdhzQueryJson) {
        Map<String,String> param=new HashMap<String,String>();

        if(StringUtils.isNotEmpty(ywCgRkdhzQueryJson.getSixCode())&&StringUtils.isNotEmpty(ywCgRkdhzQueryJson.getDjlx())){
            DaQxYhda daYgda = RequestUtils.getRequestUserId();
            param.put("yhbh",daYgda.getYgbh());
            param.put("djlx",ywCgRkdhzQueryJson.getDjlx());
            List<String> list = ywCgRkdhzService.getPzh(ywCgRkdhzQueryJson.getSixCode(),param);
            return ResultGenerator.genSuccessResult(list);
        }else{
            return ResultGenerator.genParamsErrorResult();
        }

    }
    @PostMapping("/add")
    @JSON(type = TImportErrorList.class, include = "msg")
    public Result add1(@RequestBody YwCgRkdhz ywCgRkdhz) {
        Result result=null;
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        if(StringUtils.isEmpty(ywCgRkdhz.getCgy())){
            ywCgRkdhz.setCgy(daYgda.getYgbh());
        }
        ywCgRkdhz.setKdr(daYgda.getYgbh());
        if(StringUtils.isNotEmpty(ywCgRkdhz.getSixCode())&&StringUtils.isNotEmpty(ywCgRkdhz.getBmbh())&&ywCgRkdhz.getKdrq()!=null&&StringUtils.isNotEmpty(ywCgRkdhz.getGysbh())&&ywCgRkdhz.getTmbhAndsl()!=null&&StringUtils.isNotEmpty(ywCgRkdhz.getDjlx())){
            AddReturnJson addReturnJson= ywCgRkdhzService.saveBean(ywCgRkdhz.getSixCode(),ywCgRkdhz);
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
    public Result delete(@RequestBody YwCgRkdhz ywCgRkdhz) {
        Result result=null;
        Integer flag= ywCgRkdhzService.deleteByPzh(ywCgRkdhz.getSixCode(),ywCgRkdhz.getPzh());
        if(flag>0){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorResult();
        }
        return result;
    }
}

