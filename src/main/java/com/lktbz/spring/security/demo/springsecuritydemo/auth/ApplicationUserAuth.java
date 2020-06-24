package com.lktbz.spring.security.demo.springsecuritydemo.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @ClassName ApplicationUserAuth
 * @Description 弄个实现类
 * @Author lktbz
 * @Date 2020/6/24
 */
public class ApplicationUserAuth  implements UserDetails {
    private final Set<? extends GrantedAuthority> authorities;
    private final String password;
    private final String userName;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public ApplicationUserAuth(Set<? extends GrantedAuthority> authorities,
                               String password,
                               String userName,
                               boolean accountNonExpired,
                               boolean accountNonLocked,
                               boolean credentialsNonExpired,
                               boolean enabled) {
        this.authorities = authorities;
        this.password = password;
        this.userName = userName;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
