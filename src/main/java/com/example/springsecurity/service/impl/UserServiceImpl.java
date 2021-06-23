package com.example.springsecurity.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UserService
 * @Author zouwenhai
 * @Date 2021/6/6 14:39
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("userName:{}", userName);
        //通过用户名查询用户信息，先省略掉
        //自定义密码
        String password = "123456";
        //自定义角色
        List<GrantedAuthority> roleList = Arrays.asList(new SimpleGrantedAuthority("admin"));
        return new User(userName, password, roleList);
    }
}
