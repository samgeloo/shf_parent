package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author hljstart
 * @create 2022-05-25-12:01
 */
@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;


 /*   //去首页
    @RequestMapping("/")
    public String index(){
        return "frame/index";
    }*/

    //去首页
    @RequestMapping("/")
    public String index(Map map){
        //设置默认的用户id
//        Long userId = 1L;
        //&#x8C03;&#x7528;AdminService&#x4E2D;&#x7684;&#x67E5;&#x8BE2;&#x65B9;&#x6CD5;
//        Admin admin = adminService.getById(userId);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      //调用AdminService中根据用户获取Admin对象的方法
        Admin admin = adminService.getAdminByUserName(user.getUsername());
        //根据用户的id调用PermissionService中获取用户权限菜单的方法
       List<Permission> permissionList = permissionService.getMenuPermissionByAdminId(admin.getId());
       //将用户和用户的权限菜单放到request域中
        map.put("admin", admin);
        map.put("permissionList", permissionList);
        return "frame/index";


    }


    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    //去登录页面
    @RequestMapping("/login")
    public String login() {
        return "frame/login.html";
    }


    //登出
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
            //让session失效
        session.invalidate();
        //重定向到登录页面
        return "redirect:/login";
    }


    //去没有权限的提示页面
    @RequestMapping("/auth")
    public String auth() {
        return "frame/auth";
    }
}

