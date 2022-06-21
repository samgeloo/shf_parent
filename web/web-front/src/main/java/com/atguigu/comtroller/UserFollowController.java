package com.atguigu.comtroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.comtroller
 * @Description :
 * @date : 2022-06-14 16:52
 **/
@RestController
@RequestMapping("/userFollow")
public class UserFollowController  {


    @Reference
    private UserFollowService userFollowService;

    //关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpSession session) {
        //获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用UserFollowService中关注房源的方法
        userFollowService.follow(userInfo.getId(), houseId);
        return Result.ok();
    }


    //查询我关注的房源
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result myFollowed(@PathVariable("pageNum") Integer pageNum, @PathVariable() Integer pageSize,HttpSession session) {
        //从Session域中获取UserInfo对象
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        //调用UserFollowService中分页查询的方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());
        return Result.ok(pageInfo);
    }

    //取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id) {
        //调用UserFollowService中取消关注的方法
        userFollowService.cancelFollow(id);
        return Result.ok();
    }

}
