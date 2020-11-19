package com.springboot.dao.transfer;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.transfer.YwDbRkdhz;
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
public interface YwDbRkdhzMapper extends BaseMapper<YwDbRkdhz> {

    List<YwDbRkdhz> getList(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<YwDbRkdhz> getQueryPage(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("bmbh1") String bmbh1, @Param("bmbh2") String bmbh2, @Param("cgy") String cgy, @Param("pzh") String pzh, @Param("djlx") String djlx, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> getPzh(@Param("xxly") String xxly, @Param("yhbh") String yhbh, @Param("djlx") String djlx);

    Integer delete(@Param("pzh") String pzh);

    YwDbRkdhz findByPzh(@Param("pzh") String pzh);

}
