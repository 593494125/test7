package com.springboot.service.wholesale;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.transfer.YwDbRkdhz;
import com.springboot.model.transfer.YwDbRkdhzQueryJson;
import com.springboot.model.wholesale.YwPfRkdhz;
import com.springboot.model.wholesale.YwPfRkdhzQueryJson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface YwPfRkdhzService extends IService<YwPfRkdhz> {

    Page<YwPfRkdhz> getPage(String sixCode, Page<YwPfRkdhz> page, YwPfRkdhz ywPfRkdhz, Map<String,String> param);

    Page<YwPfRkdhz> getQueryPage(String sixCode, Page<YwPfRkdhz> page, YwPfRkdhzQueryJson ywPfRkdhzQueryJson, Map<String,String> param);

    AddReturnJson saveBean(String sixCode, YwPfRkdhz ywPfRkdhz);

    Integer deleteByPzh(String sixCode,String pzh);

    List<String> getPzh(String sixCode, Map<String,String> param);

    YwPfRkdhz findByPzh(String sixCode,String pzh);

    YwPfRkdhz findLsByPzh(String sixCode,String pzh);

}
