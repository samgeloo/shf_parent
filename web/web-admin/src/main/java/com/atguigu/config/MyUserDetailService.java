package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.config
 * @Description :
 * @date : 2022-06-19 9:01
 **/

@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private PermissionService permissionService;

    @Reference
    private AdminService adminService;
    //登录时，SpringSecurity会自动调用该方法，并将用户名传入到该方法中

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用AdminService中根据用户名查询Admin对象的方法
        Admin admin = adminService.getAdminByUserName(username);
        if (null == admin) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //调用PermissionService中获取当前用户权限码的方法
      List<String> permissionList = permissionService.getPermissionCodeByAdminId(admin.getId());
        //创建一个用户授权的集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //遍历得到每个权限码
        for (String permissionCode : permissionList) {
            if (!StringUtils.isEmpty(permissionCode)) {
                //创建GrantedAuthority对象
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
                //SimpleGrantedAuthority对象放到集合中
                grantedAuthorities.add(simpleGrantedAuthority);
            }
        }
        //给用户授权
                    /*
                    * 权限有两种标识方式
                    * 1、通过角色的方式表示，例如：ROLE_ADMIN
                    * 2、直接设置权限，例如：delete、Query、update
                    *
                    * */

//        return new User(username, admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return new User(username, admin.getPassword(), grantedAuthorities);
    }
}
