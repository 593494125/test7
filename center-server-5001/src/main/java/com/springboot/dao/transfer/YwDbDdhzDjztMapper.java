package com.springboot.dao.transfer;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.transfer.YwDbDdhzDjzt;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface YwDbDdhzDjztMapper extends BaseMapper<YwDbDdhzDjzt> {

    Integer batchDeleteByPzh(@Param("pzh") String pzh);

}
