package com.springboot.service.transfer;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.model.purchase.YwCgRkdhzQueryJson;
import com.springboot.model.transfer.YwDbRkdhz;
import com.springboot.model.transfer.YwDbRkdhzQueryJson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface YwDbRkdhzService extends IService<YwDbRkdhz> {

    Page<YwDbRkdhz> getPage(String sixCode, Page<YwDbRkdhz> page, YwDbRkdhz ywDbRkdhz, Map<String,String> param);

    Page<YwDbRkdhz> getQueryPage(String sixCode, Page<YwDbRkdhz> page, YwDbRkdhzQueryJson ywDbRkdhzQueryJson, Map<String,String> param);

    AddReturnJson saveBean(String sixCode, YwDbRkdhz ywDbRkdhz);

    Integer deleteByPzh(String sixCode,String pzh);

    List<String> getPzh(String sixCode, Map<String,String> param);

    YwDbRkdhz findByPzh(String sixCode,String pzh);
}
