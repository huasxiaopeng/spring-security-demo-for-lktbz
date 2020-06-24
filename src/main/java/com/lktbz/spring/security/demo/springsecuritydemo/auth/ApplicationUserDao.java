package com.lktbz.spring.security.demo.springsecuritydemo.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @ClassName ApplicationUserDao
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/24
 */
public interface ApplicationUserDao {
    Optional<ApplicationUserAuth> getUserByUserName(String userName);
}
