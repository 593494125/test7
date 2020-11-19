package com.springboot.service.finance;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.finance.CwNewJhfyd;

/**
 * <p>
 * 财务进货费用单 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewJhfydService extends IService<CwNewJhfyd> {

    CwNewJhfyd findByPzh(String sixCode, String pzh);
}
