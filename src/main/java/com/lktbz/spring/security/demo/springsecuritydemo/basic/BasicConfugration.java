package com.lktbz.spring.security.demo.springsecuritydemo.basic;

import com.lktbz.spring.security.demo.springsecuritydemo.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName BasicConfugration
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/22
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//使用注解方式
public class BasicConfugration  extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationUserService applicationUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 用户名密码
     * @return
     *
     * //步骤五：替换默认生成的password ，改成去数据库查询的数据
     */
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        //创建一个普通角色的权限
//        UserDetails lktbz = User.builder().username("lk")
//                .password(passwordEncoder.encode("lk"))
//                //现在需要动态的读取权限
//                //roles(RolesEnumConfugration.STUDENT.name()) //ROLE_STUDENT
//                .authorities(RolesEnumConfugration.STUDENT.getGrantedAuthorities())
//                .build();
//        //创建一个admin角色的权限
//        UserDetails lindaAdmin = User.builder().username("l")
//                .password(passwordEncoder.encode("l"))
//                //.roles(RolesEnumConfugration.ADMIN.name()) //相当于从数据库查询linda有什么权限  ROLE_ADMIN
//                .authorities(RolesEnumConfugration.ADMIN.getGrantedAuthorities())
//                .build();
//        //创建ADMINTRANCE 角色
//        UserDetails tomAdmin = User.builder().username("tom")
//                .password(passwordEncoder.encode("lktbz"))
//                //.roles(RolesEnumConfugration.ADMINTRANCE.name()) //相当于从数据库查询linda有什么权限
//                .authorities(RolesEnumConfugration.ADMINTRANCE.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(lktbz,lindaAdmin,tomAdmin
//        );
//    }


        @Bean
      public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return  provider;
      }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/js/*","/css/*")//什么样的请求，被允许
                .permitAll()//这个全部允许
                //api/下面所有的路径，需要student 权限访问
                .antMatchers("/api/**/**").hasRole(RolesEnumConfugration.STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                // 加permitAll，防止重定向过多
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("username") //绑定参数
                    .passwordParameter("password")
                //设置登录成功页面
                .defaultSuccessUrl("/courses",true)
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("dsjadkshsdjsds")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember")
                    .logoutSuccessUrl("/login")


               ;

    }
}
