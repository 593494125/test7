package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.stock.YwKcDjzt;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
public interface YwKcDjztMapper extends BaseMapper<YwKcDjzt> {

    Integer batchDeleteByPzh(@Param("pzh") String pzh);
}
