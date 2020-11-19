package com.springboot.dao.goods;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.goods.DaSpBxda;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public interface DaSpBxdaMapper extends BaseMapper<DaSpBxda> {

    String findByBxbh(@Param("bxbh") String bxbh);

    List<DaSpBxda> findList();

    DaSpBxbt findSystemQybz();

}
