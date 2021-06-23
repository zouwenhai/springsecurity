package com.example.springsecurity.common.config;

import com.example.springsecurity.common.filter.JwtAuthenticationTokenFilter;
import com.example.springsecurity.common.handler.RestfulAccessDeniedHandler;
import com.example.springsecurity.common.provider.UserNameAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserNameAuthenticationProvider userNameAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    /**
     * 密码加密规则 强散列哈希加密实现(密码加密算法)
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 认证管理器
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //账号密码登陆验证
        auth.authenticationProvider(userNameAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/api/web/v1/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                //关闭跨域请求拦截器
                .csrf().disable()
                .cors().and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //异常处理
                .exceptionHandling()
                //未经授权
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //token失效
                .authenticationEntryPoint(restAuthenticationEntryPoint);
             /*   .and()
                .exceptionHandling().authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
            log.info("url:{}", httpServletRequest.getRequestURI());
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            JSONObject respBean = new JSONObject();
            if (e instanceof InsufficientAuthenticationException) {
                respBean.put("error", "该接口未经授权");
            }
            out.write(new ObjectMapper().writeValueAsString(respBean));
            out.flush();
            out.close();
        });*/
    }


    /**
     * 注册自定义JWT登录授权过滤器
     *
     * @return JwtAuthenticationTokenFilter
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}
