package com.example.springsecurity.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LoginReq
 * @Author zouwenhai
 * @Date 2021/6/6 15:10
 * @Version 1.0
 */

@ApiModel("登陆req")
@Data
public class LoginReq implements Serializable {

    @ApiModelProperty(value = "登陆用户名", example = "system", required = true)
    private String userName;
    @ApiModelProperty(value = "登陆密码", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "登陆方式", example = "0", required = true)
    private Integer type;


}
