package com.yqfsoft.util.commonresult;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;


public class CommonResult {

  @ApiModelProperty(value = "是否成功")
  private boolean success;

  @ApiModelProperty(value = "返回码")
  private Integer code;

  @ApiModelProperty(value = "返回消息")
  private String message;

  @ApiModelProperty(value = "返回数据")
  private Map<String, Object> data = new HashMap<>();

  /**
   * 构造函数 不让其他类new
   * 这样可以做统一管理
   */
  private CommonResult(){}

  /**
   * 成功的返回
   * @return
   */
  public static CommonResult ok(){
    CommonResult r = new CommonResult();
    r.setSuccess(true);
    r.setCode(CodeEnum.SUCCESS.getCode());
    r.setMessage(CodeEnum.SUCCESS.getMessage());
    return r;
  }

  /**
   * 失败的返回
   * @return
   */
  public static CommonResult fail(){
    CommonResult r = new CommonResult();
    r.setSuccess(false);
    r.setCode(CodeEnum.FAIL.getCode());
    r.setMessage(CodeEnum.FAIL.getMessage());
    return r;
  }

  /**
   * 错误的返回
   * @return
   */
  public static CommonResult error(){
    CommonResult r = new CommonResult();
    r.setSuccess(false);
    r.setCode(CodeEnum.ERROR.getCode());
    r.setMessage(CodeEnum.ERROR.getMessage());
    return r;
  }

  /**
   * 没有权限的返回
   * @return
   */
  public static CommonResult unthorized(){
    CommonResult r = new CommonResult();
    r.setSuccess(false);
    r.setCode(CodeEnum.UNAUTHORIZED.getCode());
    r.setMessage(CodeEnum.UNAUTHORIZED.getMessage());
    return r;
  }

  public static CommonResult errorUsernameOrPassword(){
    CommonResult r = new CommonResult();
    r.setSuccess(false);
    r.setCode(CodeEnum.USERNAMEANDPASSWORD_ERROR.getCode());
    r.setMessage(CodeEnum.USERNAMEANDPASSWORD_ERROR.getMessage());
    return r;
  }

  public CommonResult setSuccess(boolean success) {
    this.success = success;
    return this;
  }


  public CommonResult setCode(Integer code) {
    this.code = code;
    return this;
  }


  public CommonResult setMessage(String message) {
    this.message = message;
    return this;
  }


  public CommonResult data(String key, Object value){
    this.data.put(key, value);
    return this;
  }

  public CommonResult data(Map<String, Object> map){
    this.setData(map);
    return this;
  }
  /* getter */

  public boolean isSuccess() {
    return success;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }
}