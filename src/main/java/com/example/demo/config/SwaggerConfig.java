/*
 * Copyright (c) 2016 gaeasoft.co.kr to Present. All rights reserved.
 */
package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .paths(PathSelectors.any())
                    .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Demo", "데모"))
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()));
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo("Demo REST API"
                , "REST API Document"
                , "1.0.0"
                , "Terms of service"
                , new Contact("", "", "")
                , ""
                , "");
    }
    
    @Bean
    UiConfiguration uiConfig() {
      return new UiConfiguration (
          "",   // url
          "list",           // docExpansion          => none | list
          "alpha",          // apiSorter             => alpha
          "schema",         // defaultModelRendering => schema
          true,             // enableJsonEditor      => true | false
          true);            // showRequestHeaders    => true | false
    }
    
    private ApiKey apiKey() {
        return new ApiKey("luncheeKey", "wldjthvmxm22@@", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/api/.*"))
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("luncheeKey", authorizationScopes));
    }

}
