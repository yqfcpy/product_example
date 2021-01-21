package com.yqfsoft.util;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalVariable implements InitializingBean {

  @Value("${path.product.imgurl}")
  private String pathProductImgUrl;
  @Value("${excel.import.size}")
  private Integer excelImportSize;
  @Value("${path.nginx}")
  private String pathNginx;

  //公开定义常量
  @Getter
  private static String PATHPRODUCTIMGURL;
  @Getter
  private static Integer EXCELIMPORTSIZE;
  @Getter
  private static String PATHNGINX;

  @Override
  public void afterPropertiesSet() throws Exception {
    PATHPRODUCTIMGURL = this.pathProductImgUrl;
    EXCELIMPORTSIZE = this.excelImportSize;
    PATHNGINX = this.pathNginx;
  }
}
