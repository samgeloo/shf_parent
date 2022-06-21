package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.service.PermissionService;
import com.atguigu.vo.PermissionHelper;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service.impl
 * @Description :
 * @date : 2022-06-17 12:10
 **/
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        List<Permission> permissionList = permissionDao.findAll();
        //根据角色id查询已分配的权限id
        List<Long> permissionIds = rolePermissionDao.findPermissionIdByRoleId(roleId);
        //创建返回的List
        List<Map<String, Object>> returnList = new ArrayList<>();
        //遍历所有的权限
        for (Permission permission : permissionList) {
            //判断当前权限的id在不在permissionIds中
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            //判断当前权限的id在不在permissionIds中
            if (permissionIds.contains(permission.getId())) {
                //证明该权限是已经分配的权限
                map.put("checked", true);
            }
            //将map放到返回的List中
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
            //调用RolePermissionDao中根据角色id删除已分配的权限方法
        rolePermissionDao.deletePermissionIdByRoleId(roleId);
        //遍历所有的id
        for (Long permissionId : permissionIds) {
            if (null != permissionId) {
                //调用RolePermissionDao中保存权限id和角色id的方法
                rolePermissionDao.addRoleIdAndPermission(roleId, permissionId);
            }
        }

    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList = null;
        //判断是否是系统管理员
        if (userId == 1) {
            //证明是管理员
            permissionList = permissionDao.findAll();
        } else {
            //根据用户的id查询权限菜单
          permissionList =  permissionDao.getMenuPermissionByAdminId(userId);
        }
        //通过PermissionHelper工具类List转换成树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);
        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissionList = permissionDao.findAll();
        if (CollectionUtils.isEmpty(permissionList)) {
            return null;
        }
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> getPermissionCodeByAdminId(Long id) {
        List<String> permissionCodes = null;

        if (id == 1) {
            permissionCodes = permissionDao.getAllPermissionCodes();
        } else {
            //根据用户id查询权限码
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}
