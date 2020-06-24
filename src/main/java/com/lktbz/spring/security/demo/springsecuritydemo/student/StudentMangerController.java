package com.lktbz.spring.security.demo.springsecuritydemo.student;

import com.lktbz.spring.security.demo.springsecuritydemo.basic.RolesEnumConfugration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lktbz.spring.security.demo.springsecuritydemo.basic.RolesEnumConfugration;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StudentMangerController
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/23
 */
@RestController
@RequestMapping("/management/v1/student")
public class StudentMangerController {

    private  static final List<Student> STUDENT= Arrays.asList(
            new Student(1,"lktbz"),
            new Student(2,"YAN"),
            new Student(3,"zsz")
    );
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRANCE')")
    public  List<Student>getALLStudent(){
        System.out.println("调用getALLStudent");
        return STUDENT;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public  void registerStudent(@RequestBody Student student){
        System.out.println("调用registerStudent");
        System.out.println(student);
    }
    @DeleteMapping(path ="{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId")  Integer studentId){
        System.out.println("调用deleteStudent");
        System.out.println(studentId);
    }
    @PutMapping(path ="{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student){
        System.out.println("调用updateStudent");
        System.out.println(String.format("%s,%s",studentId,student));
    }
}
