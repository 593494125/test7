package com.springboot.dao.wholesale;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.wholesale.YwPfRkdhz;
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
public interface YwPfRkdhzMapper extends BaseMapper<YwPfRkdhz> {

    List<YwPfRkdhz> getList(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwPfRkdhz> getQueryPage(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("bmbh") String bmbh, @Param("khbh") String khbh, @Param("cgy") String cgy, @Param("pzh") String pzh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> getPzh(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx);

    Integer delete(@Param("pzh") String pzh);

    YwPfRkdhz findByPzh(@Param("pzh") String pzh);

    YwPfRkdhz findLsByPzh(@Param("pzh") String pzh);
}
