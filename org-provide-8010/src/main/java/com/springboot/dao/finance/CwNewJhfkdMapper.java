package com.springboot.dao.finance;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.finance.CwNewJhfkd;
import com.springboot.model.finance.CwNewXhskd;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 财务进货付款单 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewJhfkdMapper extends BaseMapper<CwNewJhfkd> {

    CwNewJhfkd findByPzh(@Param("pzh") String pzh);
}
