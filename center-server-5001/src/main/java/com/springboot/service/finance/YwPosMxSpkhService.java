package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.YwPosMxSpkh;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-09-12
 */
public interface YwPosMxSpkhService extends IService<YwPosMxSpkh> {

    YwCgRkdPrintmx getYwPosMxSpkhDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType);


}
