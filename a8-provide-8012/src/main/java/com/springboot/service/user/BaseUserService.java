package com.springboot.service.user;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.user.BaseUser;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * Created by CodeGenerator on 2019/10/18.
 */
public interface BaseUserService extends IService<BaseUser> {
    /**
     * 查询列表
     * @param id
     * @param userName
     * @return
     */
    String findByUserName(@RequestParam("id") String id, @RequestParam("userName") String userName);

    Page<BaseUser> getPage(String orgId, Page<BaseUser> page, Map<String, String> param);

    void insertBean(String orgId, BaseUser bean);

    Integer updateById(String orgId, BaseUser bean);

    Integer deleteById(String orgId, String id);

    BaseUser findByName(String orgId, String userName);

    BaseUser findByNameAndPassWord(String orgId, String userName, String passWord);
}
