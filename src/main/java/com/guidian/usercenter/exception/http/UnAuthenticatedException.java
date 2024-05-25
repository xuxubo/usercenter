/**
 * @作者 徐振博
 * @创建时间 2024/2/17 22:12
 */
package com.guidian.usercenter.exception.http;

public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(Integer code) {
        this.code = code;
        this.httpStatusCode = 401;
    }
}
