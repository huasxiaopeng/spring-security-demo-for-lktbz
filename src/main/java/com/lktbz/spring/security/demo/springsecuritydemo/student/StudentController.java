package com.lktbz.spring.security.demo.springsecuritydemo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StudentController
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/22
 */
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private  static final List<Student>STUDENT= Arrays.asList(
            new Student(1,"lktbz"),
            new Student(2,"YAN"),
            new Student(3,"zsz")
    );
    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId){
        return  STUDENT.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("studennt is not found"));

    }
}
