package com.springboot.service.purchase;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.purchase.YwCgDdhz;

import java.util.Map;

/**
 * <p>
 * (采购退货申请清单)采购清单 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface YwCgDdhzService extends IService<YwCgDdhz> {

     Page<YwCgDdhz> getPage(String sixCode, Page<YwCgDdhz> page,Map<String,String> param);

     Boolean saveBean(String sixCode,YwCgDdhz ywCgDdhz);

}
