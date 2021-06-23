package com.example.springsecurity.common.swager;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;

/**
 * 打印swagger地址
 *
 * @author zouwenhai
 * @date 2021/3/23 11:23
 */
@Component
@Slf4j
public class SwaggerPrintConfig implements ApplicationListener<WebServerInitializedEvent> {

    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        //获取IP
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();
        //获取端口号
        String applicationName = event.getApplicationContext().getApplicationName();
        log.info("项目启动成功！接口文档地址: http://" + hostAddress + ":" + event.getWebServer().getPort() + applicationName + "/swagger-ui.html");


    }
}
