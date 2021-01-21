package com.yqfsoft.servicecenter.product.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yqfsoft.servicecenter.productCategory.mapper")
public class ProductCategoryConfig {
}
