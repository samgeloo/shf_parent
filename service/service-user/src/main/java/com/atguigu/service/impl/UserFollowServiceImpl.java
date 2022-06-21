package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.House;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.HouseService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service.impl
 * @Description :
 * @date : 2022-06-14 16:56
 **/

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private HouseService houseService;


    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        //创建UserFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        //调用UserFollowDao中添加的方法
        userFollowDao.insert(userFollow);
    }


    @Override
    public Boolean isFollow(Long userId, Long houseId) {
        //调用UserFollowDao中查询是否关注该房源的方法
        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId, houseId);
        if (count > 0) {
            //表示已关注
            return true;
        } else {
            return false;
        }

    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //调用UserFollowDao中分页的方法
        Page<UserFollowVo> page = userFollowDao.findPageList(userId);
        for (UserFollowVo userFollowVo : page) {

            House house = houseService.getById(userFollowVo.getHouseId());
            String houseTypeName = house.getHouseTypeName();
            String floorName = house.getFloorName();
            String directionName = house.getDirectionName();
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(directionName);
        }

        return new PageInfo<>(page,5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
