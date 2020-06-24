package com.lktbz.spring.security.demo.springsecuritydemo.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName ApplicationUserService
 * @Description 查询数据库方法
 * @Author lktbz
 * @Date 2020/6/24
 */
@Service
public class ApplicationUserService implements UserDetailsService {
    private ApplicationUserDao applicationUserDao;

    public ApplicationUserService(ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return applicationUserDao.getUserByUserName(userName)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("userName %s not found",userName)));
    }
}
