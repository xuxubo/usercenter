/**
 * @作者 徐振博
 * @创建时间 2024/1/18 16:17
 */
package com.guidian.usercenter.exception.http;

public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;


    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
