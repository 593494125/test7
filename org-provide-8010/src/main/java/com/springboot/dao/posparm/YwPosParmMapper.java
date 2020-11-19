package com.springboot.dao.posparm;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.posparm.YwPosParm;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-07-02
 */
public interface YwPosParmMapper extends BaseMapper<YwPosParm> {

    String findXsmrjg(@Param("jqbh") String jqbh);

    String findBmbhByQtjqm(@Param("jqbh") String jqbh);

    String isAllowStock(@Param("bmbh") String bmbh);
}
