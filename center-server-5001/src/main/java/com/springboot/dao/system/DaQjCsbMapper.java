package com.springboot.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.system.DaQjCsb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-07-06
 */
public interface DaQjCsbMapper extends BaseMapper<DaQjCsb> {

    DaQjCsb findBean();

    List<DaQjCsb> findAll();

    String findIsStartTmFangan();

    String findSystemYsxqybz();

    List<DaQjCsb> findByCsfl(@Param("csfl") String csfl);

    boolean saveBatch(List<DaQjCsb> list);

    Integer findMaxLsh();

    DaQjCsb getBean(@Param("csbh") String csbh,@Param("csfl") String csfl);

    Integer updateByBean(@Param("bzxx") String bzxx,@Param("csbh") String csbh,@Param("csfl") String csfl);
}
