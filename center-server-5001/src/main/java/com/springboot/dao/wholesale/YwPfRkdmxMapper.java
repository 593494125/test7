package com.springboot.dao.wholesale;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.wholesale.YwPfRkdmx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface YwPfRkdmxMapper extends BaseMapper<YwPfRkdmx> {

    List<YwCgRkdmxJson> getList(@Param("pzh") String pzh, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer batchDeleteByPzh(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectYsHzList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectLsZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectLsysHzList(@Param("pzh") String pzh);
}
