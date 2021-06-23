package com.example.springsecurity.model.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName ResultVO
 * @Author zouwenhai
 * @Date 2021/6/23 22:52
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    private static final String SUCCESS = "0";


    private static final String FAIL = "1";


    private static final String SUCCESS_DESC = "success";

    private static final String FAIL_DESC = "fail";


    private String code;

    private String desc;

    private T data;

    public ResultVO(String fail, String failDesc) {
        this.code = fail;
        this.desc = failDesc;
    }


    public static <T> ResultVO<T> ok(T data) {
        return new ResultVO<T>(SUCCESS, SUCCESS_DESC, data);

    }

    public static <T> ResultVO<T> error(T data) {
        return new ResultVO<T>(FAIL, FAIL_DESC, data);
    }

    public static <T> ResultVO<T> error() {
        return new ResultVO<T>(FAIL, FAIL_DESC);
    }


    @Override
    public String toString() {
        return "ResultVO{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
