package com.springboot.service.purchase;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.model.purchase.YwCgRkdhzQueryJson;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-06
 */
public interface YwCgRkdhzService extends IService<YwCgRkdhz> {

    Page<YwCgRkdhz> getPage(String sixCode, Page<YwCgRkdhz> page, YwCgRkdhz ywCgRkdhz,Map<String,String> param);

    Page<YwCgRkdhz> getQueryPage(String sixCode, Page<YwCgRkdhz> page, YwCgRkdhzQueryJson ywCgRkdhzQueryJson, Map<String,String> param);

    AddReturnJson saveBean(String sixCode, YwCgRkdhz ywCgRkdhz);

    Integer deleteByPzh(String sixCode,String pzh);

    //该接口供测试使用
    Integer deleteByPzh1(String sixCode,String pzh);

    List<String> getPzh(String sixCode,Map<String,String> param);


    YwCgRkdhz findByPzh(String sixCode,String pzh);

}
