package com.springboot.dao.stock;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.stock.TInitImportTxtKcyw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
public interface TInitImportTxtKcywMapper extends BaseMapper<TInitImportTxtKcyw> {

    boolean saveBatch(List<TInitImportTxtKcyw> list);

    boolean deleteOldData(@Param("yhbh") String yhbh, @Param("ywflag") String ywflag);
}
