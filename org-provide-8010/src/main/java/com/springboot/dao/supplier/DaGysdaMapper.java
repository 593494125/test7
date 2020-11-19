package com.springboot.dao.supplier;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.supplier.DaGysda;
import com.springboot.model.supplier.DaGysdaJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 供应商档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface DaGysdaMapper extends BaseMapper<DaGysda> {

    List<DaGysdaJson> findByDaGysda(@Param("yhbh") String yhbh);

}
