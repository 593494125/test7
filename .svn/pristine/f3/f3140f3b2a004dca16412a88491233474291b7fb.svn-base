package com.springboot.service.employee;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.MoKuaiAuthJson;
import com.springboot.model.employee.DaYgda;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.user.DaQxYhda;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-22
 */
public interface DaYgdaService extends IService<DaYgda> {

    DaYgda findByNameAndPassWord(String orgId, String userName,String passWord);

    List<DaYgdaJson> findCgy(String sixCode);

    Map<String,String> findMoKuaiAuth(String sixCode, String yhbh);

    Map<String,String> findMoKuaiAuth1(String sixCode,String groupId);

    List<String> getGroupIds(String sixCode);

    Map<String,String> findGnMoKuaiAuth(String sixCode, DaQxYhda daYgda,String code,String qtjqm);

    List<MoKuaiAuthJson> newFindMoKuaiAuth(String sixCode, String yhbh);

    List<DaQxYhda> getList(String sixCode);

    Map<String,String> getMap(String sixCode,JSONObject moKuaiJsonObject);

}
