package com.springboot.dao.user.a8;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.user.BaseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseUserMapper extends BaseMapper<BaseUser> {



    Integer findByUserName(@Param("id") String id, @Param("userName") String userName);

    public List<BaseUser> getList(Map<String, String> param);


}