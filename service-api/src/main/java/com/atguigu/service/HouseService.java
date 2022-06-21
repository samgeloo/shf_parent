package com.atguigu.service;

import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-06 21:10
 **/
public interface HouseService extends BaseService<House>{

    void publish(Long houseId, Integer status);
    //调用HouseService中前端分页及带条件查询的方法
    PageInfo<HouseVo> findList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
