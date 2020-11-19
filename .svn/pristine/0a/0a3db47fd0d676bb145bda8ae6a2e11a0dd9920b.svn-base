package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.CwNewXhskd;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * 财务销货收款单 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewXhskdService extends IService<CwNewXhskd> {

    CwNewXhskd findByLydh(String sixCode,String lydh);

    YwCgRkdPrintmx getCwXhskdDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org,String ywType);

}
