package com.springboot.service.department;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.department.DaBmda;
import com.springboot.model.department.DaBmdaJson;

import java.util.List;

/**
 * <p>
 * 部门档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-24
 */
public interface DaBmdaService extends IService<DaBmda> {

    List<DaBmdaJson> findCgBmda(String sixCode,String yhbh);

    List<DaBmdaJson> findDbcBmda(String sixCode,String yhbh);

    List<DaBmdaJson> findDbrBmda(String sixCode,String yhbh);

    List<DaBmdaJson> findPfBmda(String sixCode,String yhbh);

    List<DaBmdaJson> findKcBmda(String sixCode,String yhbh);

    List<DaBmdaJson> findLsPosBmda(String sixCode,String yhbh);

    List<DaBmda> getList(String sixCode);

//    DaBmdaJson findByBmbh(String sixCode,String bmbh);

}
