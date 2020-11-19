package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.goods.DaSpCmzb;

import java.util.List;

/**
 * <p>
 * 商品尺码组表 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-08
 */
public interface DaSpCmzbService extends IService<DaSpCmzb> {

    List<DaSpCmzb> getList(String sixCode);

}
