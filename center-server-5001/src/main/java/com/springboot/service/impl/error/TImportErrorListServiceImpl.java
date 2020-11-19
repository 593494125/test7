package com.springboot.service.impl.error;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.error.TImportErrorListMapper;
import com.springboot.model.error.TImportErrorList;
import com.springboot.service.error.TImportErrorListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TImportErrorListServiceImpl extends ServiceImpl<TImportErrorListMapper, TImportErrorList> implements TImportErrorListService {

    @Resource
    private TImportErrorListMapper tImportErrorListMapper;
    @Override
    public List<TImportErrorList> getList(String sixCode, String tgflag) {
        List<TImportErrorList> list=tImportErrorListMapper.getList(tgflag);
        return list;
    }
}
