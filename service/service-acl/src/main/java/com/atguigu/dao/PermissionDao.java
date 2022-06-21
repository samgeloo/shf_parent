package com.atguigu.dao;

import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-17 12:12
 **/
public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<String> getAllPermissionCodes();

    List<String> getPermissionCodesByAdminId(Long id);
}
