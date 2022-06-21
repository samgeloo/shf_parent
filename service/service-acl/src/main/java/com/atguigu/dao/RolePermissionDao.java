package com.atguigu.dao;

import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-17 12:34
 **/
public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdByRoleId(Long roleId);

    void deletePermissionIdByRoleId(Long roleId);

    void addRoleIdAndPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
