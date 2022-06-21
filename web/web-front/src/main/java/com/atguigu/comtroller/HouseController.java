package com.atguigu.comtroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.comtroller
 * @Description :
 * @date : 2022-06-12 13:50
 **/
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private UserFollowService userFollowService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseService houseService;

    //分页及带条件查询的方法
    @RequestMapping(("/list/{pageNum}/{pageSize}"))
    public Result findPageList(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo) {
        //调用HouseService中前端分页及带条件查询的方法
        PageInfo<HouseVo> pageInfo = houseService.findList(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }


    //查询房源详情
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session) {
        //调用HouseService中查询房源的方法
        House house = houseService.getById(id);
        //获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //获取房源的照片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        //获取房源的经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(id);
        //创建一个Map
        Map map = new HashMap<>();
        //将房源信息，小区信息，房源图片和经纪人放到map中
        map.put("house", house);
        map.put("community", community);
        map.put("houseImage1List", houseImage1List);
        map.put("houseBrokerList", houseBrokerList);
        //设置默认没有关注该房源
//        map.put("inFollow", false);
        //设置一个变量
        Boolean isFollow = false;
        //从session域中获取UserIfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        if (null != userInfo) {
            //证明已登录，调用userFollowService中查询是否关注该房源
            isFollow = userFollowService.isFollow(userInfo.getId(), id);
        }
        //将isFollow放到map中
        map.put("isFollow", isFollow);
        return Result.ok(map);
    }


}
