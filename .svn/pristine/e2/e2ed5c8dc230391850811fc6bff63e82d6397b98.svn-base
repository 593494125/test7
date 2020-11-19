package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.stock.YwKcKctzdHz;
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
public interface YwKcKctzdHzMapper extends BaseMapper<YwKcKctzdHz> {

    List<YwKcKctzdHz> getList(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwKcKctzdHz> getQueryPage(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("bmbh") String bmbh, @Param("cgy") String cgy, @Param("pzh") String pzh, @Param("sgdj") String sgdj, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> getPzh(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx);

    Integer delete(@Param("pzh") String pzh);

    YwKcKctzdHz findByPzh(@Param("pzh") String pzh, @Param("sgdj") String sgdj);
}
