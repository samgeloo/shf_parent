package com.atguigu.service;

import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-09 16:59
 **/
public interface HouseBrokerService extends BaseService<HouseBroker> {
    //根据房源id查询该房源的经纪人
    List<HouseBroker> getHouseBrokerByHouseId(Long houseId);
}
