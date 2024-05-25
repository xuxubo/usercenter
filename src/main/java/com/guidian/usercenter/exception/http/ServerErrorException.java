/**
 * @作者 徐振博
 * @创建时间 2024/1/29 9:53
 */
package com.guidian.usercenter.exception.http;

public class ServerErrorException extends HttpException {
    public ServerErrorException(Integer code) {
        this.code = code;
        this.httpStatusCode = 500;
    }


}
