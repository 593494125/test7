package com.springboot.service.wholesale;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.wholesale.TInitImportPfywTxt;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface TInitImportPfywTxtService extends IService<TInitImportPfywTxt> {

    boolean saveBatch(String sixCode, List<TInitImportPfywTxt> list);

    boolean deleteOldData(String sixCode, String yhbh,String ywflag);

    boolean threadPoolSaveData(String sixCode,List<TInitImportPfywTxt> sunList);
}
