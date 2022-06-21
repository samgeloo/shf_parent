package com.atguigu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu
 * @Description :
 * @date : 2022-06-18 15:16
 **/

@ContextConfiguration(locations = "classpath:spring/spring-mvc.xml")
@RunWith(SpringRunner.class)
public class PasswordEncoderTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testBcryptPasswordEncoder() {
        String encode = bCryptPasswordEncoder.encode("111111");
        System.out.println("encode = " + encode);

        /*
      第一次允许的结果：encode = $2a$10$Fna0HYzqOdQ9nr7QammPE.7KoXr9exOyFlZuoAawlGaK6YCIctLBm
      第二次运行的结果：encode = $2a$10$vQ1oSwP.XXyoIiNLLm0sDubEY.3qL/E85di3vfuKbI32eEyHN9Fym
      第三次运行的结果：encode = $2a$10$XR5.2c5C/DI87MZW9BL6yuAjygPXFwBGMghLxisBMltfAxn.7R55i
       */

        boolean matches = bCryptPasswordEncoder.matches("111111", "$2a$10$Fna0HYzqOdQ9nr7QammPE.7KoXr9exOyFlZuoAawlGaK6YCIctLBm");
        System.out.println("matches = " + matches);

        boolean matches1 = bCryptPasswordEncoder.matches("111111", "$2a$10$vQ1oSwP.XXyoIiNLLm0sDubEY.3qL/E85di3vfuKbI32eEyHN9Fym");
        System.out.println("matches1 = " + matches1);

        boolean matches2 = bCryptPasswordEncoder.matches("111111", "$2a$10$XR5.2c5C/DI87MZW9BL6yuAjygPXFwBGMghLxisBMltfAxn.7R55i");
        System.out.println("matches2 = " + matches2);
    }




}
