package com.example.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ Author     ：johnbarrowman65
 * @ Date       ：Created in 11:06 2019/8/15
 * @ Description：
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 在D:/res/pic下如果有一张 luffy.jpg.jpg的图片，那么：
    // 1 访问：http://localhost:8080/img/luffy.jpg 可以访问到
    // 2 html 中 <img src="http://localhost:8080/img/luffy.jpg">

    @Value("${file.problemimg}")
    private String problemimgpath;
    @Value("${file.yanghuimg}")
    private String yanghuimgpath;
    @Value("${file.outadmin}")
    private String outadminpath;
    @Value("${file.outfinish}")
    private String outfinishpath;
    @Value("${file.sgxcadmin}")
    private String sgxcadminpath;
    @Value("${file.sgxcuser}")
    private String sgxcuserpath;

//    @Value("${file.staticAccessPath}")
//    private String staticAccessPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //System.out.println("##############");
        registry.addResourceHandler("/problemimg/**").addResourceLocations("file:/"+problemimgpath+"/");
        registry.addResourceHandler("/yanghuimg/**").addResourceLocations("file:/"+yanghuimgpath+"/");
        registry.addResourceHandler("/outadmin/**").addResourceLocations("file:/"+outadminpath+"/");
        registry.addResourceHandler("/outfinish/**").addResourceLocations("file:/"+outfinishpath+"/");
        registry.addResourceHandler("/sgxcadmin/**").addResourceLocations("file:/"+sgxcadminpath+"/");
        registry.addResourceHandler("/sgxcuser/**").addResourceLocations("file:/"+sgxcuserpath+"/");

    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:/root/feedback/");
    }*/
}