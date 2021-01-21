package com.yqfsoft.servicecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.yqfsoft")
@EnableCaching
public class ServiceCenterBoot {

  public static void main(String[] args) {
    SpringApplication.run(ServiceCenterBoot.class,args);
  }
}
