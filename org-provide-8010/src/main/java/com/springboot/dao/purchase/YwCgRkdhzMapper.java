package com.springboot.dao.purchase;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgRkdhz;
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
public interface YwCgRkdhzMapper extends BaseMapper<YwCgRkdhz> {


    List<YwCgRkdhz> getList(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwCgRkdhz> getQueryPage(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("bmbh") String bmbh, @Param("gysbh") String gysbh, @Param("cgy") String cgy, @Param("pzh") String pzh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> getPzh(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx);

    Integer delete(@Param("pzh") String pzh);

    YwCgRkdhz findByPzh(@Param("pzh") String pzh);
}
