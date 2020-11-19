package com.springboot.service.purchase;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.user.DaQxYhda;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-06
 */
public interface YwCgRkdmxService extends IService<YwCgRkdmx> {

    Map<String,Object> getPage(String sixCode, Map<String,String> param);

    YwCgRkdPrintmx getCgDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda,BaseOrg org);

    Integer batchDeleteByPzh(String sixCode,String pzh);

    List<YwCgRkdmxJson> getNewList(String sixCode, List<YwCgRkdmxJson> list);

}
