package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.stock.YwKcKcpcdHz;
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
public interface YwKcKcpcdHzMapper extends BaseMapper<YwKcKcpcdHz> {

    List<YwKcKcpcdHz> getList(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwKcKcpcdHz> getQueryPage(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("bmbh") String bmbh, @Param("cgy") String cgy, @Param("pzh") String pzh, @Param("sgdj") String sgdj, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> getPzh(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx);

    Integer delete(@Param("pzh") String pzh);

    YwKcKcpcdHz findByPzh(@Param("pzh") String pzh, @Param("sgdj") String sgdj);

    YwKcKcpcdHz findSyByPzh(@Param("pzh") String pzh);
}
