package com.lktbz.spring.security.demo.springsecuritydemo.basic;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName RolesEnumConfugration
 * @Description  角色
 * @Author lktbz
 * @Date 2020/6/23
 */
public enum  RolesEnumConfugration {
    //student 空权限
    STUDENT(Sets.newHashSet()),
    //admin 默认这些权限
    ADMIN(Sets.newHashSet
            (PermissonEnumConfugration.STUDENT_READ,
             PermissonEnumConfugration.COURSE_READ,
             PermissonEnumConfugration.STUDENT_WRITE,
             PermissonEnumConfugration.COURSE_WRITE
            )),
    ADMINTRANCE(Sets.newHashSet
            (PermissonEnumConfugration.STUDENT_READ,
             PermissonEnumConfugration.COURSE_READ
            ));

    //角色有很多权限
    private  final Set<PermissonEnumConfugration> permissions;

    RolesEnumConfugration(Set<PermissonEnumConfugration> permissions) {
        this.permissions = permissions;
    }

    public Set<PermissonEnumConfugration> getPermissions() {
        return permissions;
    }
    //定义需要动态权限获取
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissons = getPermissions().stream()
                .map(permisson -> new SimpleGrantedAuthority(permisson.getPermssion()))
                .collect(Collectors.toSet());
        permissons.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return  permissons;
    }
}
