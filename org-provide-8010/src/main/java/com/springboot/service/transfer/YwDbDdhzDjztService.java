package com.springboot.service.transfer;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.transfer.YwDbDdhzDjzt;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-05-12
 */
public interface YwDbDdhzDjztService extends IService<YwDbDdhzDjzt> {

    Integer batchDeleteByPzh(String sixCode,String pzh);

}
