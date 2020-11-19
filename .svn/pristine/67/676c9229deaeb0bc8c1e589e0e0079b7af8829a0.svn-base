package com.springboot.web.wholesale;


import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.wholesale.YwPfRkdmx;
import com.springboot.service.wholesale.YwPfRkdmxService;
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
@RequestMapping("/api/ywPfRkdmx")
public class YwPfRkdmxController {

    @Resource
    private YwPfRkdmxService ywPfRkdmxService;

    @PostMapping("/list")
    @JSON(type = YwCgRkdmxJson.class, filter = "yhbh,sixCode,pageNo,pageSize")
    public Result list(@RequestBody YwPfRkdmx ywPfRkdmx) {
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(ywPfRkdmx.getPzh())&&ywPfRkdmx.getPageNo()!=null&&ywPfRkdmx.getPageSize()!=null){
            param.put("pzh",ywPfRkdmx.getPzh());
            param.put("pageNo",String.valueOf(ywPfRkdmx.getPageNo()));
            ywPfRkdmx.setPageSize(SystemConstant.pageSize);
            param.put("pageSize",String.valueOf(ywPfRkdmx.getPageSize()));

//            Page<YwCgRkdmxJson> page = new Page<YwCgRkdmxJson>(ywPfRkdmx.getPageNo(), ywPfRkdmx.getPageSize());

            Map<String,Object> map = ywPfRkdmxService.getPage(ywPfRkdmx.getSixCode(),param);
            List<YwCgRkdmxJson> ywPfDdhzList= (List<YwCgRkdmxJson>) map.get("list");
            if(ywPfDdhzList!=null&&ywPfDdhzList.size()>0){
                return ResultGenerator.genSuccessResult(map);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }
    }
}

