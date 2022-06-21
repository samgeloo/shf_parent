package com.atguigu.service;

import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author hljstart
 * @create 2022-05-24-21:59
 */
public interface RoleService extends BaseService<Role>{
    List<Role> findAll();


    Map<String, Object> findRoleByAdmin(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
