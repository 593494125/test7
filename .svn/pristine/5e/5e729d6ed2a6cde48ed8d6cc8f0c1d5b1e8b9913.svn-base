package com.springboot.dao.transfer;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.transfer.TInitImportDbywTxt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface TInitImportDbywTxtMapper extends BaseMapper<TInitImportDbywTxt> {

    boolean saveBatch(List<TInitImportDbywTxt> list);

    boolean deleteOldData(@Param("yhbh") String yhbh, @Param("ywflag") String ywflag);

}
