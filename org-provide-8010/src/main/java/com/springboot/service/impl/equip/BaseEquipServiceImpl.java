package com.springboot.service.impl.equip;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.equip.BaseEquipMapper;
import com.springboot.model.equip.BaseEquip;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.user.BaseAuth;
import com.springboot.service.equip.BaseEquipService;
import com.springboot.service.org.BaseOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseEquipServiceImpl extends ServiceImpl<BaseEquipMapper, BaseEquip> implements BaseEquipService {

    @Resource
    private BaseEquipMapper baseEquipMapper;
    @Resource
    private BaseOrgService baseOrgService;

    @Override
    public Page<BaseEquip> getPage(String sixCode, Page<BaseEquip> page, Map<String, String> param) {
        EntityWrapper<BaseEquip> entityWrapper=new EntityWrapper<BaseEquip>();
        if(StringUtils.isNotEmpty(param.get("equipManufacturer"))){
            entityWrapper.eq("equip_manufacturer",param.get("equipManufacturer"));
        }
        if(StringUtils.isNotEmpty(param.get("orgId"))){
            entityWrapper.eq("org_id",param.get("orgId"));
        }
        List<BaseEquip> list=baseEquipMapper.selectPage(page,entityWrapper);

        List<BaseOrg> orgList=baseOrgService.getList("",param);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i < k; i++) {
                BaseEquip baseEquip=list.get(i);
                if(orgList!=null&&orgList.size()>0){
                    int h=orgList.size();
                    ok:
                    for (int j = 0; j < h; j++) {
                        BaseOrg org=orgList.get(j);
                        if(baseEquip.getOrgId().equals(org.getId())){
                            baseEquip.setOrgName(org.getOrgName());
                            break ok;
                        }
                    }
                }
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public Integer insertBean(String sixCode, BaseEquip bean) {
        Integer flag= baseEquipMapper.insert(bean);
        return flag;
    }

    @Override
    public BaseEquip findByUuidAndOrgId(String sixCode, String uuid, String orgId) {
//        BaseEquip queryBean=new BaseEquip();
//        queryBean.setOrgId(orgId);
//        queryBean.setUuid(uuid);
        BaseEquip bean=baseEquipMapper.findByUuidAndorgId(uuid,orgId);
        return bean;
    }

    @Override
    public void deleteById(String sixCode, String id) {
        baseEquipMapper.deleteById(id);
    }

    @Override
    public int batchDeleteByIds(String sixCode,List<String> ids) {
        return baseEquipMapper.batchDeleteByIds(ids);
    }

    @Override
    public void deleteByOrgId(String sixCode, String orgId) {
        baseEquipMapper.deleteByOrgId(orgId);
    }

    @Override
    public Integer getCountByOrgId(String sixCode, String orgId) {
        Integer num=baseEquipMapper.getCountByOrgId(orgId);
        return num;
    }

    @Override
    public BaseEquip findBySn(String sixCode, String sn) {
        BaseEquip bean=baseEquipMapper.findBySn(sn);
        return bean;
    }
}
