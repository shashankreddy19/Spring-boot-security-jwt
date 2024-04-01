package com.shanks.springbootsecurityjwt.security.jwt.service;

import com.shanks.springbootsecurityjwt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsImpl implements UserDetails {

    Integer id;
    String password;
    String username;
    String email;
    public UserDetailsImpl(String password,String username,String email,Integer id) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.id=id;
    }

    public static UserDetailsImpl build(User user)
    {
        return new UserDetailsImpl(user.getPassword(),user.getName()
        , user.getEmail(),user.getId());
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
