package com.lktbz.spring.security.demo.springsecuritydemo.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName PasswordEncodingConfugration
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/23
 */
@Configuration
public class PasswordEncodingConfugration {
    @Bean
  public PasswordEncoder passwordEncoder(){
      return  new BCryptPasswordEncoder();
  }
}
