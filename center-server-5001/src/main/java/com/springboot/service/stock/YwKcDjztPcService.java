package com.springboot.service.stock;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.stock.YwKcDjztPc;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
public interface YwKcDjztPcService extends IService<YwKcDjztPc> {

    Integer batchDeleteByPzh(String sixCode,String pzh);
}
