package com.atguigu.service;

import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-09 17:03
 **/
public interface HouseImageService extends BaseService<HouseImage> {
    //根据房源id和类型查询房源或房产图片
    List<HouseImage> getHouseImagesByHouseIdAndType(@Param("houseId") Long houseId, @Param("type") Integer type);

}
