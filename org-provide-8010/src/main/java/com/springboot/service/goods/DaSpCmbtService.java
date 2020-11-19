package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.goods.DaSpCmbt;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-07
 */
public interface DaSpCmbtService extends IService<DaSpCmbt> {

    DaSpCmbt findByCmzbh(String sixCode,String cmzbh);

    List<DaSpCmbt> getList(String sixCode);

}
