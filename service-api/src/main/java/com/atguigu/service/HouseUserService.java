package com.atguigu.service;

import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-09 17:06
 **/
public interface HouseUserService extends BaseService<HouseUser> {
    //根据房源id查询该房源的房东
    List<HouseUser> getHouseUsersByHouseId(Long houseId);
}
