package com.springboot.dao.goods;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.goods.DaSpYsda;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品颜色档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public interface DaSpYsdaMapper extends BaseMapper<DaSpYsda> {

    String findByYsbh(@Param("yslsh") String yslsh, @Param("ysbh") String ysbh);

    List<DaSpYsda> findList();
}
