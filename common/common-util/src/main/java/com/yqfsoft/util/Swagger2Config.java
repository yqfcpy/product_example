package com.yqfsoft.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
  // api接口包扫描路径
  public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.yqfsoft.servicecenter";// 这里写要扫描的包
  // 在项目中每个子模块 新建的包都要统一 com.qingfeng
  public static final String VERSION = "v1.0.0";

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("RestaurantePedidoOnline") // 这里不要使用中文
            .apiInfo(apiInfo())
            .enable(true) // 是否启用Swagger
            .select()
            .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
            .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("网上订餐系统") //设置文档的标题
            .contact(new Contact("QINGFENG",
                    "https://www.yqfsoft.com",
                    "9629601@qq.com"))        // 联系人信息
            .description("网上订餐系统 API 接口文档") // 设置文档的描述
            .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
            .termsOfServiceUrl("http://www.yqfsoft.com") // 设置文档的License信息->1.3 License information
            .build();
  }
  // 设置Swagger2 UI models不显示
  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            // 隐藏UI上的Models模块
            //.defaultModelsExpandDepth(-1)
            .defaultModelExpandDepth(0)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            // 显示请求数据的事件
            .displayRequestDuration(true)
            // 设置 api接口是不是都展开
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .validatorUrl(null)
            .build();
  }
}