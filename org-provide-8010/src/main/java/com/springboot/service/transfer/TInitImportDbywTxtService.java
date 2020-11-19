package com.springboot.service.transfer;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.purchase.TInitImportCgywTxt;
import com.springboot.model.transfer.TInitImportDbywTxt;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface TInitImportDbywTxtService extends IService<TInitImportDbywTxt> {

    boolean saveBatch(String sixCode, List<TInitImportDbywTxt> list);

    boolean deleteOldData(String sixCode, String yhbh,String ywflag);

    boolean threadPoolSaveData(String sixCode,List<TInitImportDbywTxt> sunList);

}
