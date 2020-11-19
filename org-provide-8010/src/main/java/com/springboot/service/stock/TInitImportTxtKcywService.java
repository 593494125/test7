package com.springboot.service.stock;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.stock.TInitImportTxtKcyw;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
public interface TInitImportTxtKcywService extends IService<TInitImportTxtKcyw> {

    boolean saveBatch(String sixCode, List<TInitImportTxtKcyw> list);

    boolean deleteOldData(String sixCode, String yhbh,String ywflag);

    boolean threadPoolSaveData(String sixCode,List<TInitImportTxtKcyw> sunList);
}
