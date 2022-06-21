package com.atguigu.dao;

import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-09 15:57
 **/
public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    //根据房源id查询该房源的经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
