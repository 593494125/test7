package com.springboot.service.wholesale;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.model.wholesale.YwPfRkdmx;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface YwPfRkdmxService extends IService<YwPfRkdmx> {

    Map<String,Object> getPage(String sixCode, Map<String,String> param);

    Integer batchDeleteByPzh(String sixCode,String pzh);

    YwCgRkdPrintmx getPfDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org,String ywType);
}
