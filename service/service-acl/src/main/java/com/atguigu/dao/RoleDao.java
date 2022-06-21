package com.atguigu.dao;

import com.atguigu.entity.Role;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hljstart
 * @create 2022-05-24-20:08
 */
@Repository
public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();

}
