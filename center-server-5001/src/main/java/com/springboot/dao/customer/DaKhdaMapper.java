package com.springboot.dao.customer;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.customer.DaKhda;
import com.springboot.model.customer.DaKhdaJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface DaKhdaMapper extends BaseMapper<DaKhda> {

    List<DaKhdaJson> findKhda(@Param("yhbh") String yhbh);

}
