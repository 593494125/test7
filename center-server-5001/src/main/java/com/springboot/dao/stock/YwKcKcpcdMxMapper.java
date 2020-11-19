package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKcpcdMx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
public interface YwKcKcpcdMxMapper extends BaseMapper<YwKcKcpcdMx> {

    List<YwCgRkdmxJson> getList(@Param("pzh") String pzh, @Param("sgdj") String sgdj, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer batchDeleteByPzh(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectZxList(@Param("pzh") String pzh, @Param("sgdj") String sgdj);

    List<YwCgRkdmxJson> selectYsHzList(@Param("pzh") String pzh, @Param("sgdj") String sgdj);

    List<YwCgRkdmxJson> selectSyZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectSyysHzList(@Param("pzh") String pzh);
}
