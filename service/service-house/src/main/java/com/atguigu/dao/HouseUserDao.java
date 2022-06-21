package com.atguigu.dao;

import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-09 16:29
 **/
public interface HouseUserDao extends BaseDao<HouseUser> {
    //根据房源id查询该房源的房东
    List<HouseUser> getHouseUsersByHouseId(Long houseId);
}
