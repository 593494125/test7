package com.springboot.service.impl.upload;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.upload.BaseUploadMapper;
import com.springboot.model.upload.BaseUpload;
import com.springboot.service.upload.BaseUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-06-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BaseUploadServiceImpl extends ServiceImpl<BaseUploadMapper, BaseUpload> implements BaseUploadService {

    @Resource
    private BaseUploadMapper baseUploadMapper;
    @Override
    public void insertBean(String sixCode, BaseUpload bean) {
        baseUploadMapper.insert(bean);
    }

    @Override
    public BaseUpload findNewBean(String sixCode,String type) {
        return baseUploadMapper.findNewBean(type);
    }

    @Override
    public void deleteById(String sixCode,String id) {
        baseUploadMapper.deleteById(id);
    }
}
