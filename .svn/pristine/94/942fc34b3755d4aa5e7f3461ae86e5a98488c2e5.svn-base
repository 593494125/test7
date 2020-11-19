package com.springboot.dao.purchase;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-06
 */
public interface YwCgRkdmxMapper extends BaseMapper<YwCgRkdmx> {

    List<YwCgRkdmxJson> getList(@Param("pzh") String pzh, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwCgRkdmxJson> selectZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectYsHzList(@Param("pzh") String pzh);

    Integer batchDeleteByPzh(@Param("pzh") String pzh);
}
