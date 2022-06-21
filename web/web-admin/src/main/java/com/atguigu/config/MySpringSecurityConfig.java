package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.config
 * @Description :
 * @date : 2022-06-18 14:38
 **/

@Configuration//声明当前类是一个配置类，注意：当前配置类也需要被扫描
@EnableWebSecurity//开启spring security的自动配置，会给我们生成一个登陆页面
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启Controller中方法的权限控制
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {


/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //在内存中设置一个认证的用户名和密码
        auth.inMemoryAuthentication().withUser("admin")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles("");
    }*/

    //创建一个密码加密器
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //目前必须调用父类configure方法，否则认证过程将失效
//        super.configure(http);
        //配置允许iframe标签访问
        http.headers().frameOptions().sameOrigin();
        //配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**", "/login").permitAll().anyRequest().authenticated();
        //自定义登录页面
        http.formLogin().loginPage("/login")//配置去自定义页面的访问的路径
                .defaultSuccessUrl("/");//配置登录成功之前去往的地址

        //配置登出的地址及登出成功之后去往的地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        //关掉跨域请求伪造
        http.csrf().disable();
        //配置自定义的无权限访问的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());



    }
}
