package com.springboot.dao.posparm;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.posparm.YwPosMx;
import com.springboot.model.posparm.YwPosSaleDay;
import com.springboot.model.purchase.YwCgRkdmxJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * POS明细表 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
public interface YwPosMxMapper extends BaseMapper<YwPosMx> {

    List<YwCgRkdmxJson> selectZxList(@Param("pzh") String pzh);

    List<YwCgRkdmxJson> selectYsHzList(@Param("pzh") String pzh);

    YwPosSaleDay findYwSaleDayDetail(@Param("bmbh") String bmbh, @Param("jqbh") String jqbh, @Param("rq") String rq);

}
