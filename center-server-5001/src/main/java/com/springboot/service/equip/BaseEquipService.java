package com.springboot.service.equip;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.equip.BaseEquip;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-23
 */
public interface BaseEquipService extends IService<BaseEquip> {

    Page<BaseEquip> getPage(String sixCode, Page<BaseEquip> page, Map<String, String> param);

    Integer insertBean(String sixCode, BaseEquip bean);

    BaseEquip findByUuidAndOrgId(String sixCode,String uuid,String orgId);

    void deleteById(String sixCode,String id);

    int batchDeleteByIds(String sixCode,List<String> ids);

    void deleteByOrgId(String sixCode,String orgId);

    Integer getCountByOrgId(String sixCode,String orgId);

    BaseEquip findBySn(String sixCode,String sn);

}
