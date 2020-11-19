package com.springboot.dao.finance;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.finance.CwNewXhfydMx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销货费用单明细 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewXhfydMxMapper extends BaseMapper<CwNewXhfydMx> {

    List<CwNewXhfydMx> selectZxList(@Param("pzh") String pzh);

}
