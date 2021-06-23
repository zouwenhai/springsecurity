package com.example.springsecurity.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.common.utils.SHAUtils;
import com.example.springsecurity.model.req.LoginReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zouwenhai
 * @date 2021/6/8
 */
@Api(tags = "用户管理模块")
@RestController
@RequestMapping("${api.web.path:/api/web}/${api.web.version:/v1}")
@Slf4j
public class UserController {
    /**
     * 过期时间
     */

    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "privateKey";


    @Autowired
    private AuthenticationManager authenticationManager;


    @ApiOperation("登陆")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
        if (authentication.isAuthenticated()) {
            //创建token
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create().withIssuer("auth0").sign(algorithm);
            //验证token
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            log.info(jwt.getToken());
            log.info(token);
        }
        return "fail";
    }


    @ApiOperation("用户列表")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        log.info("user:{}","user");
        return "success";
    }
}
