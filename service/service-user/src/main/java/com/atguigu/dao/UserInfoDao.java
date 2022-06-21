package com.atguigu.dao;

import com.atguigu.entity.UserInfo;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.dao
 * @Description :
 * @date : 2022-06-14 8:36
 **/
public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
