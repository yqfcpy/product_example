package com.yqfsoft.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutofillHandler implements MetaObjectHandler {

  // 创建的时候运行的方法
  @Override
  public void insertFill(MetaObject metaObject){
    // 自动填充
    this.setFieldValByName("bloqueado",false,metaObject);
    // 自动填充添加和修改时间
    this.setFieldValByName("createTime", new Date(), metaObject);
    this.setFieldValByName("updateTime", new Date(), metaObject);
    // 用户注册时的登录时间 这里可写可不写 也可以写在前端
    this.setFieldValByName("lastLoginTime", new Date(), metaObject);
  }
  // 修改的时候运行的方法
  @Override
  public void updateFill(MetaObject metaObject){
    // 修改时间自动填充
    this.setFieldValByName("updateTime", new Date(), metaObject);
    // 自动修改登录时间 自动登录时间需要在登录时设置时间不是在这里设置
    // this.setFieldValByName("lastLoginTime", new Date(), metaObject);
  }
}
