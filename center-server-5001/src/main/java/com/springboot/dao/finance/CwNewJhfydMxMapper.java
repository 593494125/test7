package com.springboot.dao.finance;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.finance.CwNewJhfydMx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 进货费用单明细 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewJhfydMxMapper extends BaseMapper<CwNewJhfydMx> {

    List<CwNewJhfydMx> selectZxList(@Param("pzh") String pzh);

}
