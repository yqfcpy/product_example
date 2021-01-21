package com.yqfsoft.util.globalexception;

import com.yqfsoft.util.commonresult.CodeEnum;

public class MyException extends RuntimeException{

  private Integer errorCode;
  private String errorMessage;

  private MyException() {
  }
  private MyException(Integer errorCode,String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
  // 帐号密码异常
  public static MyException existe(){
    MyException ex = new MyException(CodeEnum.EXISTE.getCode(), CodeEnum.EXISTE.getMessage());
    return ex;
  }
  // 帐号密码异常
  public static MyException usernameAndpassword(){
    MyException ex = new MyException(CodeEnum.USERNAMEANDPASSWORD_ERROR.getCode(), CodeEnum.USERNAMEANDPASSWORD_ERROR.getMessage());
    return ex;
  }
  // 帐号异常
  public static MyException username(){
    MyException ex = new MyException(CodeEnum.USERNAME_ERROR.getCode(),CodeEnum.USERNAME_ERROR.getMessage());
    return ex;
  }
  // 密码异常
  public static MyException password(){
    MyException ex = new MyException(CodeEnum.PASSOWRD_ERROR.getCode(),CodeEnum.PASSOWRD_ERROR.getMessage());
    return ex;
  }
  // 电子邮件异常
  public static MyException email(){
    MyException ex = new MyException(CodeEnum.EMAIL_ERROR.getCode(),CodeEnum.EMAIL_ERROR.getMessage());
    return ex;
  }
  // 电话号码异常
  public static MyException telefono(){
    MyException ex = new MyException(CodeEnum.TELEFONO_ERROR.getCode(),CodeEnum.TELEFONO_ERROR.getMessage());
    return ex;
  }
  // 提交异常
  public static MyException commit(){
    MyException ex = new MyException(CodeEnum.COMMIT_ERROR.getCode(),CodeEnum.COMMIT_ERROR.getMessage());
    return ex;
  }
  // 值不能为空
  public static  MyException empty(){
    MyException ex = new MyException(CodeEnum.EMPTY.getCode(), CodeEnum.EMPTY.getMessage());
    return ex;
  }
  // 数字异常
  public static  MyException number(){
    MyException ex = new MyException(CodeEnum.NUMBER_ERROR.getCode(), CodeEnum.NUMBER_ERROR.getMessage());
    return ex;
  }
  // 字符串异常
  public static  MyException string(){
    MyException ex = new MyException(CodeEnum.STRING_ERROR.getCode(), CodeEnum.STRING_ERROR.getMessage());
    return ex;
  }
  // 图片异常
  public static  MyException file(){
    MyException ex = new MyException(CodeEnum.FILE_ERROR.getCode(), CodeEnum.FILE_ERROR.getMessage());
    return ex;
  }
  // Excel 异常
  public static MyException excel(){
    MyException ex = new MyException(CodeEnum.EXCEL_ERROR.getCode(), CodeEnum.EXCEL_ERROR.getMessage());
    return ex;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
