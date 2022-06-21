package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-17 21:29
 **/
@Controller
@RequestMapping("/permission")
@SuppressWarnings({"unchecked", "rawtypes"})
public class PermissionController {
    private final static String LIST_ACTION = "redirect:/permission";

    private final static String PAGE_INDEX = "permission/index";
    private final static String PAGE_CREATE = "permission/create";
    private final static String PAGE_EDIT = "permission/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    @Reference
    private PermissionService permissionService;

     /**
     * 去菜单页面
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(ModelMap model) {
        List<Permission> list = permissionService.findAllMenu();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }


    @GetMapping("/create")
    public String create(ModelMap model, Permission permission) {
        model.addAttribute("permission", permission);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Permission permission) {
        permissionService.insert(permission);
        return PAGE_SUCCESS;
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission", permission);
        return PAGE_EDIT;
    }


    @PostMapping(value="/update")
    public String update(Permission permission) {
        permissionService.update(permission);
        return PAGE_SUCCESS;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);
        return LIST_ACTION;
    }


}
