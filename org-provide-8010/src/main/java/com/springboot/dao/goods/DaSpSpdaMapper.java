package com.springboot.dao.goods;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.goods.DaSpSpda;
import com.springboot.model.goods.EsDaSpSpdaJson;

import java.util.List;

/**
 * <p>
 * 商品商品档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
public interface DaSpSpdaMapper extends BaseMapper<DaSpSpda> {

    List<EsDaSpSpdaJson> findEsList();
}
