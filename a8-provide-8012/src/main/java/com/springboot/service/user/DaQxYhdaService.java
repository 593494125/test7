package com.springboot.service.user;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.user.DaQxYhda;

/**
 * <p>
 * 用户档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
public interface DaQxYhdaService extends IService<DaQxYhda> {

    DaQxYhda findByNameAndPassWord(String sixCode, String userName, String passWord);

    DaQxYhda findByName(String sixCode, String userName);
}
