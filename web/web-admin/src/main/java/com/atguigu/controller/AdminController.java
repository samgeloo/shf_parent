package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author hljstart
 * @create 2022-05-26-9:46
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Reference
    private RoleService roleService;

    @Reference
    private AdminService adminService;

    //分页及带条件查询
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放入到request域中
        map.put("filters", filters);
        //调用AdminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        //将pageInfo对象放到request域中
        map.put("page", pageInfo);

        return "admin/index";
    }

    //去添加用户的页面
    @RequestMapping("/create")
    public String goAddPage() {
        return "admin/create";
    }

    //新增用户
    @RequestMapping("/save")
    public String save(Admin admin) {
        //对Admin对象中的密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return "common/successPage";
    }

    //删除用户
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        //重定向
        return "redirect:/admin";
    }

    //去更新的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        //调用AdminService中根据id查询用户的方法
        Admin admin = adminService.getById(id);
        map.put("admin", admin);
        return "admin/edit";
    }

    //根据用户id修改用户信息
    @RequestMapping("/update")
    public String update(Admin admin) {
        adminService.update(admin);
        return "common/successPage";
    }

    //去上传头像的页面
    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(@PathVariable("id") Long id, Map map) {
        //将用户的id放到request域中
        map.put("adminId", id);
        return "admin/upload";
    }

    //开始上传头像
    @PostMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file) {


        //获取字节数组
        Admin admin = adminService.getById(id);
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename;
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        QiniuUtil.upload2Qiniu(bytes, fileName);
        admin.setHeadUrl("http://rd8s21plq.hn-bkt.clouddn.com/" + fileName);
        adminService.update(admin);

        return "common/successPage";
    }

    //去分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap modelMap) {
        //将用户的id放到request域中
        modelMap.addAttribute("adminId", adminId);
        //调用RoleService中根据用户id查询用户的角色方法
        Map<String, Object> roleByAdminId = roleService.findRoleByAdmin(adminId);
        modelMap.addAllAttributes(roleByAdminId);
        return "admin/assignShow";
    }


    //分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds) {
        //调用RoleService中分配角色的方法
        roleService.assignRole(adminId, roleIds);
        return "common/successPage";
    }


}
