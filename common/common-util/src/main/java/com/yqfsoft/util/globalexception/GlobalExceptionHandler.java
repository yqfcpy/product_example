package com.yqfsoft.util.globalexception;

import com.yqfsoft.util.commonresult.CodeEnum;
import com.yqfsoft.util.commonresult.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
  // 这里要把异常处理的类文件传进去
  // Exception.class 这个是最高的异常了 全局异常 只要有异常就走这个方法
  @ExceptionHandler(Exception.class)
  public CommonResult error(Exception e){
    e.printStackTrace();
    log.error(CodeEnum.UNKNOWN_ERROR.getMessage());
    return CommonResult.error().setCode(CodeEnum.UNKNOWN_ERROR.getCode())
                               .setMessage(CodeEnum.UNKNOWN_ERROR.getMessage());
  }
  // 特定异常处理 这个用的比较少因为 运行时异常有很多 没必要每个都写 用最高的异常处理所有异常
  // 很少人用这个异常 为了学习基础知识这里就演示一下
  @ExceptionHandler(ArithmeticException.class)
  public CommonResult error(ArithmeticException e){
    e.printStackTrace();
    return CommonResult.error().setMessage("0不能做被除数");
  }
  // 自定义异常 先创建一个自定义异常类 比如MyException
  // 类的内容写在下边的代码
  @ExceptionHandler(MyException.class)
  public CommonResult error(MyException e){
    e.printStackTrace();
    // 请注意 e.getErrorCode() e.getErrorMessage() 这个是MyException中的方法
    // 感觉这个地方用warm可能很好 自己写一下统一配置类中的warm方法吧
    // log.error(CodeEnum.UNKNOWN_ERROR.getMessage());
    return CommonResult.error().setCode(e.getErrorCode()).setMessage(e.getErrorMessage());
  }
  // 在自己自定义的统一返回结果可以添加几个错误异常 cantidadError数量错误等等
  // 这个异常可以实现一个接口 然后写一个枚举类 统一异常 的错误代码和错误信息
  // 可以用我们自定义统一返回类型里 实现的接口 枚举类

  /**
   * Controller层 数据检验的异常处理
   * @param e
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResult error(MethodArgumentNotValidException e){
    e.printStackTrace();
    // 在e中可以拿到异常的值 BindingResult有值 校验信息较多 我们给它变成一个map
    Map<String,String> argumentException = e.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
    log.error(CodeEnum.PARAM_VALIDATION_ERROR.getMessage());
    return CommonResult.error().setCode(CodeEnum.PARAM_VALIDATION_ERROR.getCode())
            .setMessage(CodeEnum.PARAM_VALIDATION_ERROR.getMessage())
            .data("checkError",argumentException);
  }

  /**
   * service服务层数据校验异常
   * @param e
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public CommonResult error(ConstraintViolationException e){
    e.printStackTrace();
    Map<Path,Object> map = e.getConstraintViolations().stream()
            .collect(Collectors.toMap(ConstraintViolation::getPropertyPath,ConstraintViolation::getMessage));
    log.error(CodeEnum.PARAM_VALIDATION_ERROR.getMessage());
    return CommonResult.error().setCode(CodeEnum.PARAM_VALIDATION_ERROR.getCode())
            .setMessage(CodeEnum.PARAM_VALIDATION_ERROR.getMessage())
            .data("checkError",map);
  }
}
