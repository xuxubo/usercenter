/**
 * @作者 徐振博
 * @创建时间 2024/5/10 21:06
 */
package com.guidian.usercenter.core;

import com.guidian.usercenter.core.configuration.ExceptionCodeConfiguration;
import com.guidian.usercenter.exception.http.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionAdvice{

    @Autowired
    private ExceptionCodeConfiguration codes;


    /**
     * Http 请求异常处理
     * @param request
     * @param e
     * @return UnifyResponse
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest request, Exception e) {
        UnifyResponse message = new UnifyResponse(9999, codes.getMessage(9999), null);
        return message;
    }


    /**
     * 处理自定义Http Exception
     * @param request
     * @param e
     * @return ResponseEntity
     */
    @ResponseBody
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity handleHttpException(HttpServletRequest request, HttpException e) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        UnifyResponse message = new UnifyResponse(e.getCode(), codes.getMessage(e.getCode()), null);
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message, headers, httpStatus);
        return r;

    }

}
