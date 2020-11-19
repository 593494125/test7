package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.CwNewXhfydMx;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * 销货费用单明细 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewXhfydMxService extends IService<CwNewXhfydMx> {

    YwCgRkdPrintmx getCwXhfydDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType);
}
