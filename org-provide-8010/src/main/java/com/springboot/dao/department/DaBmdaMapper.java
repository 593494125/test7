package com.springboot.dao.department;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.department.DaBmda;
import com.springboot.model.department.DaBmdaJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-24
 */
public interface DaBmdaMapper extends BaseMapper<DaBmda> {

    List<DaBmdaJson> findCgBmda(@Param("yhbh") String yhbh);

    List<DaBmdaJson> findDbcBmda(@Param("yhbh") String yhbh);

    List<DaBmdaJson> findDbrBmda(@Param("yhbh") String yhbh);

    List<DaBmdaJson> findPfBmda(@Param("yhbh") String yhbh);

    List<DaBmdaJson> findKcBmda(@Param("yhbh") String yhbh);

    List<DaBmdaJson> findLsPosBmda(@Param("yhbh") String yhbh);

    List<DaBmda> getList();
}
