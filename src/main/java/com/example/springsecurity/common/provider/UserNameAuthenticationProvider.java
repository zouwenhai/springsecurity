package com.example.springsecurity.common.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName UserNameAuthenticationProvider
 * 账号密码验证器
 * @Author zouwenhai
 * @Date 2021/6/6 14:47
 * @Version 1.0
 */
@Slf4j
@Component
public class UserNameAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 密码加密规则
     */
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("账号密码校验开始");
        //登陆名
        String userName = authentication.getName();
        //密码
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(userName);
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        log.info("密码校验结果：{}", bCryptPasswordEncoder.matches(password, user.getPassword()));
        log.info("账号密码校验结束");
        if (Objects.isNull(user) || !bCryptPasswordEncoder.matches(password, encodePassword)) {
            throw new BadCredentialsException("账号密码错误");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return usernamePasswordAuthenticationToken;
    }

    /**
     * 是否支持UsernamePasswordAuthenticationToken校验
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        boolean res = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        log.info("用户名/密码 是否进行登录验证 res:{}", res);
        return res;

    }
}
