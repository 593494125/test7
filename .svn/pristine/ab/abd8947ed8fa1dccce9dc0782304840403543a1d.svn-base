package com.springboot.service.stock;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.stock.YwKcKcpcdHz;
import com.springboot.model.stock.YwKcKctzdHzQueryJson;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
public interface YwKcKcpcdHzService extends IService<YwKcKcpcdHz> {

    Page<YwKcKcpcdHz> getPage(String sixCode, Page<YwKcKcpcdHz> page, YwKcKcpcdHz ywKcKcpcdHz, Map<String,String> param);

    Page<YwKcKcpcdHz> getQueryPage(String sixCode, Page<YwKcKcpcdHz> page, YwKcKctzdHzQueryJson ywKcKctzdHzQueryJson, Map<String,String> param);

    AddReturnJson saveBean(String sixCode, YwKcKcpcdHz ywKcKcpcdHz);

    Integer deleteByPzh(String sixCode,String pzh);

    List<String> getPzh(String sixCode, Map<String,String> param);

    YwKcKcpcdHz findByPzh(String sixCode, String pzh,String sgdj);

    YwKcKcpcdHz findSyByPzh(String sixCode, String pzh);

}
