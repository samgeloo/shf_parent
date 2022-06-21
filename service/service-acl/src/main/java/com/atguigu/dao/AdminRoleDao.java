package com.atguigu.dao;

import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-16 19:02
 **/
public interface AdminRoleDao extends BaseDao<AdminRole>{

    List<Long> findRoleIdByAdminId(Long adminId);

    void deleteRoleIdsByAdminId(Long adminId);

    void addRoleIdAndAdmin(@Param("roleId") Long roleId, @Param("adminId") Long adminId);
}
