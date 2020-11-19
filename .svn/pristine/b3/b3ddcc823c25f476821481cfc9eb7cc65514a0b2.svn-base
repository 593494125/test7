package com.springboot.service.stock;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.purchase.YwCgRkdhz;
import com.springboot.model.stock.YwKcKctzdHz;
import com.springboot.model.stock.YwKcKctzdHzQueryJson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
public interface YwKcKctzdHzService extends IService<YwKcKctzdHz> {

    Page<YwKcKctzdHz> getPage(String sixCode, Page<YwKcKctzdHz> page, YwKcKctzdHz ywKcKctzdHz, Map<String,String> param);

    Page<YwKcKctzdHz> getQueryPage(String sixCode, Page<YwKcKctzdHz> page, YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson, Map<String,String> param);

    AddReturnJson saveBean(String sixCode, YwKcKctzdHz ywKcKctzdHz);

    Integer deleteByPzh(String sixCode,String pzh);

    List<String> getPzh(String sixCode, Map<String,String> param);

    YwKcKctzdHz findByPzh(String sixCode, String pzh, String sgdj);
}
