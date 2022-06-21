package com.atguigu.dao;

import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author hljstart
 * @create 2022-05-26-9:51
 */
public interface AdminDao extends BaseDao<Admin>{

    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
