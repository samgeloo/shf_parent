package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author hljstart
 * @create 2022-05-24-22:02
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    public static final String SUCCESS_PAGE="common/successPage";

    @Reference
    private PermissionService permissionService;
    @Reference
    private RoleService roleService;
    /*@RequestMapping
    public String index(Map map){
        List<Role> roleList = roleService.findAll();
        //将所有角色放入request中
        map.put("list",roleList);
        return "role/index";
    }*/

    //分页及带条件的查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放到request域中
        map.put("filters",filters);
        //调用RoleService中分页及带条件查询的方法
        PageInfo<Role> pageInfo=roleService.findPage(filters);

        //将pageInfo对象放到request中
        map.put("page",pageInfo);
        return "role/index";
    }
    //去添加角色的页面

    @RequestMapping("/create")
    @PreAuthorize("hasAuthority('role.create')")
    public String goAddPage(){
        return "role/create";
    }
    //添加角色
    @RequestMapping("/save")
    @PreAuthorize("hasAuthority('role.create')")
    public String save(Role role){
        //调用RoleService中添加的方法
        roleService.insert(role);
        //重定向到查询所有的角色的方法
        return SUCCESS_PAGE;
    }

    //删除角色
    @PreAuthorize("hasAuthority('role.delete')")//此时只有delete的权限
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        //调用RoleService
        roleService.delete(roleId);
        //重定向到所有角色的方法
        return "redirect:/role";
    }

    //去修改页面的方法
    @RequestMapping("/edit/{roleId}")
    @PreAuthorize("hasAuthority('role.edit')")
    public String goEditPage(@PathVariable("roleId")Long roleId,Map map){
        //调用RoleService中根据id查询的方法
        Role role=roleService.getById(roleId);
        //将查询的Role对象放到Request域中
        map.put("role",role);
        //去修改的页面
        return "role/edit";
    }
    //更新角色
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('role.edit')")
    public String update(Role role){
        //调用RoleService中update方法
        roleService.update(role);
        //去common下successPage页面
        return SUCCESS_PAGE;
    }


    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return
     */
    public Map<String, Object> getFilters(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 3);
        }
        return filters;
    }


    //去分配权限的页面
    @RequestMapping("/assignShow/{roleId}")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String goAssignShowPage(@PathVariable("roleId") Long roleId,Map map) {
        //将角色id放到requests域中
        map.put("roleId", roleId);
    //调用RoleService中根据id查询的方法
        Role role = roleService.getById(roleId);
        map.put("role", role);
        //调用PermissionService中根据角色id获取权限的方法
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        //将zNodes放到request域中
        map.put("zNodes", zNodes);
        return "role/assignShow";
    }

    //分配权限
    @RequestMapping("/assignPermission")
    @PreAuthorize("hasAuthority('role.assgin')")
    public String assignPermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIds") Long[] permissionIds) {
        //调用PermissionService中分配权限的方法
        permissionService.assignPermission(roleId, permissionIds);
        return "common/successPage";

    }

}
