package com.springboot.service.purchase;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.purchase.TInitImportCgywTxt;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-29
 */
public interface TInitImportCgywTxtService extends IService<TInitImportCgywTxt> {

    boolean saveBatch(String sixCode, List<TInitImportCgywTxt> list);

    boolean threadPoolSaveData(String sixCode,List<TInitImportCgywTxt> sunList);

    boolean deleteOldData(String sixCode, String yhbh,String ywflag);
}

