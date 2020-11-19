package com.springboot.service.upload;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.upload.BaseUpload;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-06-15
 */
public interface BaseUploadService extends IService<BaseUpload> {

    void insertBean(String sixCode, BaseUpload bean);

    BaseUpload findNewBean(String sixCode,String type);

    void deleteById(String sixCode,String id);
}
