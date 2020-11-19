package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.goods.DaSpBxda;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public interface DaSpBxdaService extends IService<DaSpBxda> {

    String findByBxbh(String sixCode,String bxbh);

    List<DaSpBxda> findList(String sixCode);

    DaSpBxbt findSystemQybz(String sixCode);

}
