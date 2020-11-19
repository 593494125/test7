package com.springboot.dao.transfer;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.transfer.YwDbRkdmx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface YwDbRkdmxMapper extends BaseMapper<YwDbRkdmx> {

    List<YwCgRkdmxJson> getList(@Param("pzh") String pzh, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer batchDeleteByPzh(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectSaveZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectYsHzList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectSaveYsHzList(@Param("pzh") String pzh);

    Integer getCount(@Param("pzh") String pzh);

}
