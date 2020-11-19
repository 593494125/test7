package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.goods.DaSpYsda;

import java.util.List;

/**
 * <p>
 * 商品颜色档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-30
 */
public interface DaSpYsdaService extends IService<DaSpYsda> {

    String findByYsbh(String sixCode,String yslsh,String ysbh);

    List<DaSpYsda> findList(String sixCode);

}
