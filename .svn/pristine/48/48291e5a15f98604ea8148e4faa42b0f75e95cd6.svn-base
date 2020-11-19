package com.springboot.web.stock;


import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKctzdMx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.stock.YwKcKctzdMxService;
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
@RequestMapping("/api/ywKcKctzdMx")
public class YwKcKctzdMxController {

    @Resource
    private YwKcKctzdMxService ywKcKctzdMxService;

    @PostMapping("/list")
    @JSON(type = YwCgRkdmxJson.class, filter = "yhbh,sixCode,pageNo,pageSize")
    public Result list(@RequestBody YwKcKctzdMx ywKcKctzdMx) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(ywKcKctzdMx.getPzh())&&ywKcKctzdMx.getPageNo()!=null&&ywKcKctzdMx.getPageSize()!=null){
            param.put("pzh",ywKcKctzdMx.getPzh());
            param.put("pageNo",String.valueOf(ywKcKctzdMx.getPageNo()));
            ywKcKctzdMx.setPageSize(SystemConstant.pageSize);
            param.put("pageSize",String.valueOf(ywKcKctzdMx.getPageSize()));

            Map<String,Object> map = ywKcKctzdMxService.getPage(ywKcKctzdMx.getSixCode(),param);
            List<YwCgRkdmxJson> ywKcRkdmxList= (List<YwCgRkdmxJson>) map.get("list");
            if(ywKcRkdmxList!=null&&ywKcRkdmxList.size()>0){
                return ResultGenerator.genSuccessResult(map);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }
    }

}

