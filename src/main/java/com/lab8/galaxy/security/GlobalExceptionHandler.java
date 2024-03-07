package com.lab8.galaxy.security;

import com.lab8.galaxy.entity.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData handleAllExceptions(Exception ex, WebRequest request) {
        // 使用 log 来记录异常信息，包括堆栈跟踪。
        // 通常建议至少记录异常信息和关联的请求路径。
        log.error("Unhandled exception caught: {}", request.getDescription(false), ex);
        // 构建返回结果
        ResultData resultData = new ResultData();
        resultData.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resultData.setMsg("An unexpected error occurred.");
        // Optionally, include more info depending on your needs
        // resultData.setDebugMessage(ex.getLocalizedMessage());
        return resultData;
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResultData handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Method argument type mismatch: {}", request.getDescription(false), ex);

        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        // 检测到恶意用户输入的情况
        if (ex.getValue() instanceof String && ((String) ex.getValue()).contains("..")) {
            error = "Invalid path or argument detected";
        }

        ResultData resultData = new ResultData();
        resultData.setCode(HttpStatus.BAD_REQUEST.value());
        resultData.setMsg(error);
        return resultData;
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultData handleMissingParams(MissingServletRequestParameterException ex) {
        ResultData resultData = new ResultData();
        resultData.setCode(HttpStatus.BAD_REQUEST.value());
        resultData.setMsg("Missing request parameter: " + ex.getParameterName());
        return resultData;
    }
}
