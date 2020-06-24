package com.lktbz.spring.security.demo.springsecuritydemo.student;

import com.lktbz.spring.security.demo.springsecuritydemo.basic.RolesEnumConfugration;
import lombok.*;

/**
 * @ClassName Student
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/22
 */
@Builder
public class Student {
    private  final  Integer studentId;
    private  final  String studentName;

    public Student(Integer studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
