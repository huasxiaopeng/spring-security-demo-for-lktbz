package com.lktbz.spring.security.demo.springsecuritydemo.auth;

import com.google.common.collect.Lists;
import com.lktbz.spring.security.demo.springsecuritydemo.basic.RolesEnumConfugration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * @ClassName ApplicationUserDaoService
 * @Description 构造数据
 * @Author lktbz
 * @Date 2020/6/24
 */
@Repository
public class ApplicationUserDaoService implements ApplicationUserDao{
    private PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * 构建数据
     * @return
     */
    private List<ApplicationUserAuth>getAllApplicationAuth(){
       return Lists.newArrayList(
                new ApplicationUserAuth(
                        RolesEnumConfugration.STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("lk"),
                        "lk",
                        true,
                        true,
                        true,
                        true
                 ),
                new ApplicationUserAuth(
                        RolesEnumConfugration.ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("lk"),
                        "linda",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUserAuth(
                        RolesEnumConfugration.ADMINTRANCE.getGrantedAuthorities(),
                        passwordEncoder.encode("lk"),
                        "tom",
                        true,
                        true,
                        true,
                        true
                )
                );
    }
    @Override
    public Optional<ApplicationUserAuth> getUserByUserName(String userName) {
        return getAllApplicationAuth()
                .stream()
                .filter(applicationUserAuth -> userName.equals(applicationUserAuth.getUsername()))
                .findFirst();
    }
}
