package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName SpringSecurityConfiguration
 * @Description TODO
 * @Author lktbz
 * @Date 2020/12/21
 */
@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
//    配置忽略静态文件
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/plugins/**", "/images/**", "/fonts/**");
    }
    //配置自定义登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .formLogin().loginPage("/login").permitAll()
//                登录成功，跳转默认页面、
//                .formLogin().loginPage("/login").defaultSuccessUrl("/index").permitAll()
//                总是跳转
                .formLogin().loginPage("/login").defaultSuccessUrl("/user/index", true).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}