package com.atguigu.dao;

import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-05 14:22
 **/
public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();

}
