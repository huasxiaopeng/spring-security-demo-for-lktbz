package com.lktbz.spring.security.demo.springsecuritydemo.basic;

/**
 * @ClassName PermissonEnumConfugration
 * @Description 权限集合
 * @Author lktbz
 * @Date 2020/6/23
 */
public enum  PermissonEnumConfugration {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");
    private final  String permssion;

    PermissonEnumConfugration(String permssion) {
        this.permssion = permssion;
    }

    public String getPermssion() {
        return permssion;
    }
}
