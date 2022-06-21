package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hljstart
 * @create 2022-05-24-21:59
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao;


    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRoleByAdmin(Long adminId) {
        //获取所有角色
        List<Role> roleList = roleDao.findAll();
        //获取用户拥有的角色id
      List<Long> roleIds = adminRoleDao.findRoleIdByAdminId(adminId);
      //创建两个List，一个放未选中的角色，一个放选中的角色
        ArrayList<Role> noAssignRoleList = new ArrayList<>();
        ArrayList<Role> assignRoleList = new ArrayList<>();
        for (Role role : roleList) {
            //判断当前角色的id在不在集合roleList中
            if (roleIds.contains(role.getId())) {
                //将当前角色放到自己选中的List中
                assignRoleList.add(role);
            } else {
                //证明当前角色是选中的角色，放到未选中的List中
                noAssignRoleList.add(role);
            }
        }
        //返回的map
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssignRoleList", noAssignRoleList);
        roleMap.put("assignRoleList", assignRoleList);

        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //根据用户id将自己分配的角色删除
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        for (Long roleId : roleIds) {
            if (null != roleId) {
                //角色id和用户id插入到数据中
                adminRoleDao.addRoleIdAndAdmin(roleId, adminId);
            }
        }
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }
}
