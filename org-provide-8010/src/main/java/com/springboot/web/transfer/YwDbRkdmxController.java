package com.springboot.web.transfer;


import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.configurer.JSON;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.transfer.YwDbRkdmx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.transfer.YwDbRkdmxService;
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
@RequestMapping("/api/ywDbRkdmx")
public class YwDbRkdmxController {

    @Resource
    private YwDbRkdmxService ywDbRkdmxService;

    @PostMapping("/list")
    @JSON(type = YwCgRkdmxJson.class, filter = "yhbh,sixCode,hjhrq,pageNo,pageSize")
    public Result list(@RequestBody YwDbRkdmx ywDbRkdmx) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(ywDbRkdmx.getPzh())&&ywDbRkdmx.getPageNo()!=null&&ywDbRkdmx.getPageSize()!=null){
            param.put("pzh",ywDbRkdmx.getPzh());
            param.put("pageNo",String.valueOf(ywDbRkdmx.getPageNo()));
            ywDbRkdmx.setPageSize(SystemConstant.pageSize);
            param.put("pageSize",String.valueOf(ywDbRkdmx.getPageSize()));
            Map<String,Object> map = ywDbRkdmxService.getPage(ywDbRkdmx.getSixCode(),param);
            List<YwCgRkdmxJson> ywDbDdhzList= (List<YwCgRkdmxJson>) map.get("list");
            if(ywDbDdhzList!=null&&ywDbDdhzList.size()>0){
                return ResultGenerator.genSuccessResult(map);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult(ResultGenerator.ID_ERROR);
            }
        }else{
            return ResultGenerator.genParamsErrorResult();
        }
    }
}

