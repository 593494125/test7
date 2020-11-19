package com.springboot.dao.purchase;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.purchase.YwCgDdhz;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * (采购退货申请清单)采购清单 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
public interface YwCgDdhzMapper extends BaseMapper<YwCgDdhz> {

    List<YwCgDdhz> getList(Map<String, String> param);

}
