package com.springboot.web.purchase;


import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgRkdmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.purchase.YwCgRkdmxService;
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
@RequestMapping("/api/ywCgRkdmx")
public class YwCgRkdmxController {

    @Resource
    private YwCgRkdmxService ywCgRkdmxService;

    @PostMapping("/list")
    @JSON(type = YwCgRkdmxJson.class, filter = "yhbh,sixCode,hjhrq,pageNo,pageSize")
    public Result list(@RequestBody YwCgRkdmx ywCgRkdmx) {
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(ywCgRkdmx.getPzh())&&ywCgRkdmx.getPageNo()!=null&&ywCgRkdmx.getPageSize()!=null){
            param.put("pzh",ywCgRkdmx.getPzh());
            param.put("pageNo",String.valueOf(ywCgRkdmx.getPageNo()));
            ywCgRkdmx.setPageSize(SystemConstant.pageSize);
            param.put("pageSize",String.valueOf(ywCgRkdmx.getPageSize()));
//            Page<YwCgRkdmxJson> page = new Page<YwCgRkdmxJson>(ywCgRkdmx.getPageNo(), ywCgRkdmx.getPageSize());
//            Page<YwCgRkdmxJson> ywCgDdhzList = ywCgRkdmxService.getPage(ywCgRkdmx.getSixCode(),page,param);
            Map<String,Object> map = ywCgRkdmxService.getPage(ywCgRkdmx.getSixCode(),param);
            List<YwCgRkdmxJson> ywCgDdhzList= (List<YwCgRkdmxJson>) map.get("list");
            if(ywCgDdhzList!=null&&ywCgDdhzList.size()>0){
                return ResultGenerator.genSuccessResult(map);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }
    }

}

