package com.springboot.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.system.DaQxZyda;
import com.springboot.model.system.ZyzfcJson;
import com.springboot.model.system.ZyzfcUserJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-07-06
 */
public interface DaQxZydaMapper extends BaseMapper<DaQxZyda> {

    List<DaQxZyda> findAll();

    ZyzfcJson findZyzfc();

    List<ZyzfcUserJson> newFindZyzfc();

    ZyzfcJson findUserZyzfc(@Param("ygbh") String ygbh);

    List<ZyzfcUserJson> newFindUserZyzfc(@Param("ygbh") String ygbh);


}
