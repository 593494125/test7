package com.springboot.service.wholesale;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.wholesale.YwPfDdhzDjzt;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-13
 */
public interface YwPfDdhzDjztService extends IService<YwPfDdhzDjzt> {

    Integer batchDeleteByPzh(String sixCode,String pzh);
}
