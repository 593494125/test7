package com.springboot.dao.wholesale;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.wholesale.TInitImportPfywTxt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface TInitImportPfywTxtMapper extends BaseMapper<TInitImportPfywTxt> {

    boolean saveBatch(List<TInitImportPfywTxt> list);

    boolean deleteOldData(@Param("yhbh") String yhbh, @Param("ywflag") String ywflag);
}
