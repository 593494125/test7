package com.springboot.service.purchase;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.purchase.YwCgDdhzDjzt;

/**
 * <p>
 * 单据状态表 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface YwCgDdhzDjztService extends IService<YwCgDdhzDjzt> {

    Integer save(String sixCode,YwCgDdhzDjzt ywCgDdhzDjzt);

    Integer batchDeleteByPzh(String sixCode,String pzh);
    //该接口供测试使用
    Integer batchDeleteByPzh1(String sixCode,String pzh);

}
