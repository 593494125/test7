package com.springboot.service.customer;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.customer.DaKhda;
import com.springboot.model.customer.DaKhdaJson;
import com.springboot.model.department.DaBmdaJson;

import java.util.List;

/**
 * <p>
 * 客户档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface DaKhdaService extends IService<DaKhda> {

    List<DaKhdaJson> findKhda(String sixCode, String yhbh);

}
