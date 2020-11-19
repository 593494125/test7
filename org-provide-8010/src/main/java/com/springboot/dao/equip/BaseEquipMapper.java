package com.springboot.dao.equip;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.equip.BaseEquip;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-23
 */
public interface BaseEquipMapper extends BaseMapper<BaseEquip> {

    BaseEquip findByUuidAndorgId(@Param("uuid") String uuid, @Param("orgId") String orgId);

    Integer getCountByOrgId(@Param("orgId") String orgId);

    void deleteByOrgId(@Param("orgId") String orgId);

    int batchDeleteByIds(List<String> ids);

    BaseEquip findBySn(@Param("sn") String sn);
}
