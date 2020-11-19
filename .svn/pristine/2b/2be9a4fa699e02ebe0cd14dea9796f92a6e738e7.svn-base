package com.springboot.dao.purchase;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.TInitImportCgywTxt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-29
 */
public interface TInitImportCgywTxtMapper extends BaseMapper<TInitImportCgywTxt> {

    boolean saveBatch(List<TInitImportCgywTxt> list);

    boolean deleteOldData(@Param("yhbh") String yhbh, @Param("ywflag") String ywflag);

}
