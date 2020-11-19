package com.springboot.dao.finance;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.finance.CwNewXhskd;
import com.springboot.model.wholesale.YwPfRkdhz;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 财务销货收款单 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
public interface CwNewXhskdMapper extends BaseMapper<CwNewXhskd> {

    CwNewXhskd findByPzh(@Param("pzh") String pzh);

    CwNewXhskd findByLydh(@Param("lydh") String lydh);

}
