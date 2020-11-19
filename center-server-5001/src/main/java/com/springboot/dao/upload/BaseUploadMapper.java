package com.springboot.dao.upload;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.upload.BaseUpload;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-06-15
 */
public interface BaseUploadMapper extends BaseMapper<BaseUpload> {

    BaseUpload findNewBean(@Param("type") String type);
}
