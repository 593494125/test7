package com.springboot.service.error;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.error.TImportErrorList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-28
 */
public interface TImportErrorListService extends IService<TImportErrorList> {

    List<TImportErrorList> getList(String sixCode, String tgflag);

}
