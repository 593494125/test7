package com.springboot.service.impl.system;

import com.alibaba.fastjson.JSONObject;
import com.springboot.common.RedisUtil;
import com.springboot.dao.goods.DaSpBxdaMapper;
import com.springboot.dao.system.DaQjCsbMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.system.DaQjCsb;
import com.springboot.model.system.DaQjCsbJson;
import com.springboot.model.system.DaQjCsbKeyJson;
import com.springboot.model.system.DynamicBean;
import com.springboot.service.goods.DaSpBxdaService;
import com.springboot.service.system.DaQjCsbService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaQjCsbServiceImpl extends ServiceImpl<DaQjCsbMapper, DaQjCsb> implements DaQjCsbService {


    @Resource
    private DaQjCsbMapper daQjCsbMapper;
    @Resource
    private DaSpBxdaService daSpBxdaService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Object findBean(String sixCode) {
        Object obj =null;
        List<DaQjCsb> list=daQjCsbMapper.findAll();
        DaSpBxbt daSpBxbt=daSpBxdaService.findSystemQybz(sixCode);
        if(daSpBxbt!=null&&StringUtils.isNotEmpty(daSpBxbt.getQybz())){
            //添加商品属性管理
            DaQjCsb bxDaQjCsb=new DaQjCsb();
            bxDaQjCsb.setCsbh("goods_property");
            bxDaQjCsb.setCsfl("spbx");
            bxDaQjCsb.setBzxx("商品属性管理");
            bxDaQjCsb.setCsmc("");
            bxDaQjCsb.setQybz(daSpBxbt.getQybz());
            list.add(bxDaQjCsb);
            redisUtil.set(sixCode,sixCode+"_goodsBxProperty", JSONObject.toJSONString(daSpBxbt));//往缓存中存放
        }
        //生成key
        if(list!=null&&list.size()>0){
            int k=list.size();
            HashMap propertyMap = new HashMap();
            HashMap resultMap = new HashMap();
            DaQjCsbKeyJson daQjCsbKeyJson=new DaQjCsbKeyJson();
            for (int i = 0; i < k; i++) {
                DaQjCsb daQjCsb=list.get(i);
                String key=daQjCsb.getCsbh().toLowerCase()+"#"+daQjCsb.getCsfl().toLowerCase();
                // 设置类成员属性
                try {
                    propertyMap.put(key, Class.forName("com.springboot.model.system.DaQjCsbKeyJson"));
                    daQjCsbKeyJson= (DaQjCsbKeyJson) daQjCsbKeyJson.clone();
                    daQjCsbKeyJson.setQybz(daQjCsb.getQybz());
                    daQjCsbKeyJson.setBzxx(daQjCsb.getBzxx());
                    daQjCsbKeyJson.setCsmc(daQjCsb.getCsmc());
                    resultMap.put(key,daQjCsbKeyJson);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //map转换成实体对象
            DynamicBean bean = new DynamicBean(propertyMap);
            //赋值
            Set keys = propertyMap.keySet();
            for (Iterator it = keys.iterator(); it.hasNext(); ) {
                String key = (String) it.next();
                bean.setValue(key, resultMap.get(key));
            }
            obj = bean.getObject();
        }
        return obj;
    }

    @Override
    public List<DaQjCsb> findAll(String sixCode) {
        List<DaQjCsb> list=daQjCsbMapper.findAll();
        return list;
    }

    @Override
    public String findIsStartTmFangan(String sixCode) {
        return daQjCsbMapper.findIsStartTmFangan();
    }

    @Override
    public String findSystemYsxqybz(String sixCode) {
        String ysxqybz=daQjCsbMapper.findSystemYsxqybz();
        return ysxqybz;
    }

    @Override
    public List<DaQjCsb> findByCsfl(String sixCode, String csfl) {
        List<DaQjCsb> list=daQjCsbMapper.findByCsfl(csfl);
        return list;
    }

    @Override
    public boolean saveBatch(String sixCode, List<DaQjCsb> list) {
        boolean flag=daQjCsbMapper.saveBatch(list);
        return flag;
    }

    @Override
    public Integer findMaxLsh(String sixCode) {
        Integer lsh=daQjCsbMapper.findMaxLsh();
        return lsh;
    }

    @Override
    public DaQjCsb getBean(String sixCode,String csbh,String csfl) {
        DaQjCsb bean=daQjCsbMapper.getBean(csbh,csfl);
        return bean;
    }

    @Override
    public Integer updateByBean(String sixCode, DaQjCsb bean) {
        Integer flag=daQjCsbMapper.updateByBean(bean.getBzxx(),bean.getCsbh(),bean.getCsfl());
        return flag;
    }

}
