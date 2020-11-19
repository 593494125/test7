package com.springboot.dao.employee;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.MoKuaiAuthJson;
import com.springboot.model.employee.DaYgda;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.role.GroupUserJson;
import com.springboot.model.user.DaQxYhda;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工档案 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-22
 */
public interface DaYgdaMapper extends BaseMapper<DaYgda> {

    List<DaYgdaJson> findCgy();

    List<MoKuaiAuthJson> findMoKuaiAuth(@Param("yhbh") String yhbh);

    List<MoKuaiAuthJson> findMoKuaiAuth1(@Param("groupId") String groupId);

    List<String> getGroupIds();

    List<GroupUserJson> getGroupUserList();

    List<DaQxYhda> getList();

}
