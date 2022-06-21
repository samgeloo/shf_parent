package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-14 16:54
 **/
public interface UserFollowService extends BaseService<UserFollow> {

    void follow(Long id, Long houseId);

    Boolean isFollow(Long userId, Long houseId);
//分页查询我关注的房源
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId);

    void cancelFollow(Long id);
}
