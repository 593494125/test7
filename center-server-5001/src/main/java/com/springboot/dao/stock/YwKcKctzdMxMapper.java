package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKctzdMx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
public interface YwKcKctzdMxMapper extends BaseMapper<YwKcKctzdMx> {

    List<YwCgRkdmxJson> getList(@Param("pzh") String pzh, @Param("sgdj") String sgdj, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer batchDeleteByPzh(@Param("pzh") String pzh);
}
