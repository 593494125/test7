package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.CwNewXhfyd;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * 财务销货费用单 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewXhfydService extends IService<CwNewXhfyd> {

    CwNewXhfyd findByPzh(String sixCode, String pzh);


}
