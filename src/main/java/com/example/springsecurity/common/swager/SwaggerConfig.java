package com.example.springsecurity.common.swager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


   /* @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //Controller所在包(必须新建包)
                .apis(RequestHandlerSelectors.basePackage("com.example.springsecurity"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //接口文档的名字
                .title("demo api")
                //接口文档的描述
                .description("demo api接口文档")
                //服务条款网址
                .termsOfServiceUrl("http://localhost/")
                //接口文档的版本
                .version("1.0.0")
                // 接口文档维护联系信息
                .contact(new Contact("Jakieonway", "", "jackieonway@outlook.com"))
                .build();
    }*/


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springsecurity"))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;
    }

    private List<ApiKey> securitySchemes() {

        return new ArrayList(
                Collections.singleton(new ApiKey("Authorization", "Authorization", "header")));
    }
    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(
                Collections.singleton(new SecurityReference("Authorization", authorizationScopes)));
    }

}
