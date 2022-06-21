package com.atguigu.service;

import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-05 14:45
 **/
public interface CommunityService extends BaseService<Community>{

    List<Community> findAll();
}
