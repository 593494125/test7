package com.springboot.web.stock;


import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKcpcdMx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.stock.YwKcKcpcdMxService;
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
@RequestMapping("/api/ywKcKcpcdMx")
public class YwKcKcpcdMxController {


    @Resource
    private YwKcKcpcdMxService ywKcKcpcdMxService;

    @PostMapping("/list")
    @JSON(type = YwCgRkdmxJson.class, filter = "yhbh,sixCode,pageNo,pageSize")
    public Result list(@RequestBody YwKcKcpcdMx ywKcKcpcdMx) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(ywKcKcpcdMx.getPzh())&&ywKcKcpcdMx.getPageNo()!=null&&ywKcKcpcdMx.getPageSize()!=null&&StringUtils.isNotEmpty(ywKcKcpcdMx.getSgdj())){
            param.put("pzh",ywKcKcpcdMx.getPzh());
            param.put("sgdj",ywKcKcpcdMx.getSgdj());
            param.put("pageNo",String.valueOf(ywKcKcpcdMx.getPageNo()));
            ywKcKcpcdMx.setPageSize(SystemConstant.pageSize);
            param.put("pageSize",String.valueOf(ywKcKcpcdMx.getPageSize()));

//            Page<YwCgRkdmxJson> page = new Page<YwCgRkdmxJson>(ywPfRkdmx.getPageNo(), ywPfRkdmx.getPageSize());

            Map<String,Object> map = ywKcKcpcdMxService.getPage(ywKcKcpcdMx.getSixCode(),param);
            List<YwCgRkdmxJson> ywKcDdhzList= (List<YwCgRkdmxJson>) map.get("list");
            if(ywKcDdhzList!=null&&ywKcDdhzList.size()>0){
                return ResultGenerator.genSuccessResult(map);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }
    }
}

