# springsecurity

## 入门

#### helloworld(基于表单）

```java
@Configuration
public class MySpringsecurityConfig extends WebSecurityConfigurerAdapter {
  //这是加密解密
   @Bean
   public PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //  super.configure(http);
       http.formLogin()  //这是表单验证
       //  http.httpBasic()  这是传统验证方式
             .and()
             .authorizeRequests() //拦截任何请求都需要认证
             .anyRequest()//任何请求
             .authenticated();  //都需要身份认证
    }
}
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/world?&useSSL=true
spring.datasource.username=root
spring.datasource.password=xiao
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.session.store-type=none
#security.basic.enabled=false
spring.data.redis.repositories.enabled=false

server.port=9092
```

```java
/**
* 模拟 从数据去取到数据，需要做验证
**/
@Component
public class OwnUserDetail implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * 自定义实现，从数据库获取数据，查询到数据
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名"+username);
//        return new User(username,"123456" ,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        /**
         * 这是带权限信息的
         *
         */
        String password= passwordEncoder.encode("xiao");
        logger.info("加密的密码为"+password);
         return  new User(username, password,
              true, true, true, true,//实际开发中需要确认这四个ture的真是权限，需要查询后台数据库 
              AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
```

```java
@RestController("/user2")
@EnableSwagger2
//@Api(value = "这是用户功能的总总控制器")
public class HeController {
//    @ApiOperation(value = "helloworld")
    @GetMapping("/index")
    public  String sayhe(){
        return  "hello servurity";
    }
//
    /**
     * 这是接受参数
     * @param name
     * @return
     */
    @GetMapping("/se")
    public List<User> getUser(@PathVariable String name){
        System.out.println("这是我接收到的参数"+name);
        List<User> luser =new ArrayList<>();
        luser.add(new User());
        luser.add(new User());
        luser.add(new User());
        return  luser;
    }
    }
```

```java

@SpringBootApplication(exclude={
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class,args);
    }
}
```

### 个性化认证

#### 自定义登录页面

 在实际开发中，需要登录到某个页面去做登录

```java
@Configuration
public class MySpringsecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.formLogin()
             .loginPage("/login.html")   //登录页面所载的url
              // .successForwardUrl("")
       //  http.httpBasic()
             .and()
             .authorizeRequests() //拦截任何请求都需要认证
               //这个请求不需要认证
              .antMatchers("/login.html").permitAll()  //新添加
                .anyRequest()//任何请求
             .authenticated();//都需要身份认证
    }
}
```

   创建login.html页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
hahah
</body>
</html>
```

随便出入一个请求

http://localhost:9092/login.html



下面做一个表单登录页面

页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>标准登录页面</h2>
<h3>表单登录</h3>
<form action="/authentication/form" method="post">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password"></td>
        </tr>
        <!--<tr>-->
            <!--<td>图形验证码:</td>-->
            <!--<td>-->
                <!--<input type="text" name="imageCode">-->
                <!--<img src="/code/image?width=200">-->
            <!--</td>-->
        <!--</tr>-->
        <!--<tr>-->
            <!--<td colspan='2'><input name="remember-me" type="checkbox" value="true" />记住我</td>-->
        <!--</tr>-->
        <tr>
            <td colspan="2"><button type="submit">登录</button></td>
        </tr>
    </table>
</form>
</body>
</html>
```



代码

```java
package com.xiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MySpringsecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http.formLogin()
             .loginPage("/login.html")   //登录页面所载的url
               /**
                * security 有一个默认/login/post 方法，指定这个，就会走自定义的
                */
             .loginProcessingUrl("/authentication/form")   
       //  http.httpBasic()
             .and()
             .authorizeRequests() //拦截任何请求都需要认证
               //这个请求不需要认证
              .antMatchers("/login.html").permitAll()
                .anyRequest()//任何请求
             .authenticated()//都需要身份认证
             .and()
               //  不上这段会报这个错Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-CSRF-TOKEN'.
             .csrf().disable();
    }
}
```

浏览器输入

http://localhost:9092/user/1

查看返回结果是html

**重要**

**在rest 服务中返回的是json格式和状态码，传统方式返回html 这种方式不合理，需要改造**



在前后端的项目中，我不知道用户的从哪个登录界面过啦的，所以需要做适配，要是没有登录就进来了，需要对用户进行错误返回（也就是自己去配置登录页面）

**通过配置文件的方式，如果配置文件里的属性不存在，则不会读取配置文件，直接读取默认的值**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/world?&useSSL=true
spring.datasource.username=root
spring.datasource.password=xiao
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.session.store-type=none
#security.basic.enabled=false
spring.data.redis.repositories.enabled=false

server.port=9092

#spring.mvc.view.prefix=/templates/
#spring.mvc.view.suffix=.html

#这是添加的
xiao.security.brower.locationName=/login.html
```

新创建的页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>肖的登录页面</h2>
</body>
</html>
```

```java
package com.xiao.controller;

import com.xiao.config.XiaoProperties;
import com.xiao.dto.SimpleResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class XiaoSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());
   private   RequestCache  requestCache= new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 通过配置文件读取动态的读取
     */
@Autowired
   private   XiaoProperties xiaoProperties;
    //默认请求到这个路径下
    @RequestMapping("/loginContorllerReq")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public  SimpleResponse loginContorller(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String locationName = xiaoProperties.getLocationName();
        System.out.println("读取的配置文件内容是"+locationName);
        //从缓存中拿到请求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest!=null){
            String url = savedRequest.getRedirectUrl();
            logger.info("请求的路径是："+url);
            if(StringUtils.endsWithIgnoreCase(url, ".html")){
                /**
                 * 这个地址不好弄，因为不确定是返回自己所写的路径，还是返回用户自定义的路径。
                 * 需要用户去设置
                 */
                redirectStrategy.sendRedirect(request, response, xiaoProperties.getLocationName());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
}

```

```java
import org.springframework.context.annotation.Configuration;
//配置文件类,也可以使用@ENableConfigproperties方式获取值

/**
 *
 */
@Configuration
public class XiaoProperties {
    @Value("${xiao.security.brower.locationName}")
    private String locationName;
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
```



```java
package com.xiao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class MySpringsecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }
   @Autowired
   private  XiaoProperties xiaoProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable();
         http.formLogin()
             .loginPage("/loginContorllerReq")   //当需要权限认证跳转的controller
               /**
                * security 有一个默认/login/post 方法，指定这个，就会走自定义的
                */
             .loginProcessingUrl("/authentication/form")
       //  http.httpBasic()
             .and()
             .authorizeRequests() //拦截任何请求都需要认证
               //这个请求不需要认证
              .antMatchers("/loginContorllerReq").permitAll()
               //因为获取poperties时报错，故改成vlue方式获取，
               .antMatchers( xiaoProperties.getLocationName()).permitAll()
                .anyRequest()//任何请求
             .authenticated();//都需要身份认证
        

    }

}

```







#### 自定义登录成功处理

登录成功，可以返回json 也可以返回html所以需要有两种方式去处理	

只需要实现一个接口 `AuthenticationSuccessHandler`，重写方法，但是接口中并没有想要的东西

`SavedRequestAwareAuthenticationSuccessHandler`子类去继承实现

```java
@Component("ixiaoSuccessHandler")
public class XiaoSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 private Logger logger = LoggerFactory.getLogger(getClass());
   //回写类型
    @Autowired
    private LoginType loginType;
   //转换类
    @Autowired
   private ObjectMapper objectMapper;
    //自定义的properties
   @Autowired
   private XiaoProperties xiaoProperties;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
                logger.info("登录成功");

        if (loginType.JSON.equals(xiaoProperties.getLoginType())) {
            //会写json 所有 数据都在
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else{
            //返回html 需要让父类去回写
             super.onAuthenticationSuccess(request, response, authentication);
            }
    }
}

```



```java
public enum LoginType {
    /**
     * 跳转
     */
    DIR,
    /**
     * Json
     */
    JSON
}
```



```java

/**
 *  配置类
 */
@Configuration
public class XiaoProperties {
    @Value("${xiao.security.brower.locationName}")
    private String locationName;
    private  LoginType loginType =LoginType.JSON;
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public LoginType getLoginType() {
        return loginType;
    }
    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}

```

```java
package com.xiao.config;

import com.xiao.SuccessAndFailRedo.XiaoFailExceptionHandler;
import com.xiao.SuccessAndFailRedo.XiaoSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class MySpringsecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder passwordEncoder(){
       return  new BCryptPasswordEncoder();
   }
   @Autowired
   private  XiaoProperties xiaoProperties;
    /**
     * 自己写的成功处理器
     */
   @Autowired
   private XiaoSuccessHandler ixiaoSuccessHandler;
   @Autowired
   private XiaoFailExceptionHandler  ixiaoFailHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable();
         http.formLogin()
             .loginPage("/loginContorllerReq")   //当需要权限认证跳转的controller
               /**
                * security 有一个默认/login/post 方法，指定这个，就会走自定义的
                */
             .loginProcessingUrl("/authentication/form")
                //成功的处理返回
             .successHandler(ixiaoSuccessHandler)
                 //失败的处理返回
             .failureHandler(ixiaoFailHandler)
       //  http.httpBasic()
             .and()
             .authorizeRequests() //拦截任何请求都需要认证
               //这个请求不需要认证
              .antMatchers("/loginContorllerReq").permitAll()
               //不知道为啥，这个在启动时会报错
               .antMatchers( xiaoProperties.getLocationName()).permitAll()
                .anyRequest()//任何请求
             .authenticated();//都需要身份认证

               //  不上这段会报这个错Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-CSRF-TOKEN'.





    }



}

```

```java

public class SimpleResponse {
    private  Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
```



#### 自定义登录失败处理

```java
package com.xiao.SuccessAndFailRedo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.config.LoginType;
import com.xiao.config.XiaoProperties;
import com.xiao.dto.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 此方法是登录失败抛异常，
 *
 */
@Component("ixiaoFailHandler")
public class XiaoFailExceptionHandler extends SimpleUrlAuthenticationFailureHandler {
 private Logger logger = LoggerFactory.getLogger(getClass());
   @Autowired
   private ObjectMapper objectMapper;
    @Autowired
    private XiaoProperties xiaoProperties;
    @Autowired
    private LoginType loginType;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
      //  super.onAuthenticationFailure(request, response, exception);
        logger.info("登录失败。。。。"+exception);
        /**
         *这里需要分析，如果是返回html自己不需要处理，只需要交给父类去处理
         *
         */
        if (loginType.JSON.equals(xiaoProperties.getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}

```

### 图形验证码

开发生成图形验证码接口



在认证流程中加入图形校验码



