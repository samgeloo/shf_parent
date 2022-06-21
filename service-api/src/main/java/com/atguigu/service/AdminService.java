package com.atguigu.service;

import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author hljstart
 * @create 2022-05-26-9:47
 */
public interface AdminService extends BaseService<Admin>{

    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
