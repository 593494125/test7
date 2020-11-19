package com.springboot.dao.wholesale;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.wholesale.YwPfDdhzDjzt;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface YwPfDdhzDjztMapper extends BaseMapper<YwPfDdhzDjzt> {

    Integer batchDeleteByPzh(@Param("pzh") String pzh);
}
