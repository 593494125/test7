package com.springboot.dao.purchase;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgDdmx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * (采购退货申请明细单)采购明细单 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface YwCgDdmxMapper extends BaseMapper<YwCgDdmx> {

    boolean insertBatch(List<YwCgDdmx> ywCgDdmxList);

    Double getJjTotal(@Param("pzh") String pzh);

    Double getZjje(@Param("pzh") String pzh);

}
