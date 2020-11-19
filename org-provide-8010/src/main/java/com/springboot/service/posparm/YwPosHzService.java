package com.springboot.service.posparm;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.posparm.YwPosHz;

/**
 * <p>
 * POS汇总表 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
public interface YwPosHzService extends IService<YwPosHz> {

    YwPosHz findByPzh(String sixCode, String pzh);

}
