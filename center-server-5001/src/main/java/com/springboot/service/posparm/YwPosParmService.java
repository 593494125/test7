package com.springboot.service.posparm;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.posparm.YwPosParm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-07-02
 */
public interface YwPosParmService extends IService<YwPosParm> {

    YwPosParm findByJqbh(String sixCode,String jqbh,String bmbh);

    String findXsmrjg(String sixCode,String jqbh);

    String findBmbhByQtjqm(String sixCode,String jqbh);

    String isAllowStock(String sixCode,String bmbh);

}
