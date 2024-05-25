/**
    * @作者 徐振博
    * @创建时间 2024/5/10 21:16
    */
package com.guidian.usercenter.core;

import com.guidian.usercenter.core.configuration.ExceptionCodeConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class UnifyResponse<T> {
    private int code;
    private String message;
    private T data;


    public UnifyResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public UnifyResponse(int code, T data) {
        this.code = code;
        this.message = "";
        this.data = data;
    }

    public UnifyResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public UnifyResponse(int code){
        this.code = code;
        this.message = "";
        this.data = null;
    }
}
