package com.yqfsoft.util.commonresult;

public enum CodeEnum {
  // 自定义的变量 在一个工程有很多要写的状态码 都定义在这里
  // 这个地方有一个坑啊 要用","逗号隔开属性
  SUCCESS(200,"Success"),
  FAIL(500,"Fail"),
  ERROR(1002,"Error"),
  EMPTY(1000,"IsEmpty"),
  UNAUTHORIZED(1003,"Unauthorized"),
  PARAM_VALIDATION_ERROR(1110,"ParameterException"),
  UNKNOWN_ERROR(9999,"GlobalException"),
  USERNAMEANDPASSWORD_ERROR(1019,"UsernameOrPasswordError"),
  USERNAME_ERROR(1010,"用户名异常"),
  PASSOWRD_ERROR(1011,"密码异常"),
  EXISTE(1012,"existe"),
  EMAIL_ERROR(1013,"EmailException"),
  TELEFONO_ERROR(1012,"电话号码异常"),
  COMMIT_ERROR(1001,"提交异常"),
  NUMBER_ERROR(1020,"数字异常"),
  STRING_ERROR(1021,"字符串异常"),
  EXCEL_ERROR(1022,"ExcelError"),
  FILE_ERROR(1013,"文件不合法");


  private Integer code;
  private String message;

  private CodeEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}