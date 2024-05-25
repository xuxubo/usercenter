/**
 * @作者 徐振博
 * @创建时间 2024/2/28 15:57
 */
package com.guidian.usercenter.exception;


import com.guidian.usercenter.exception.http.HttpException;

public class CreateSuccess extends HttpException {

    public CreateSuccess(int code) {

        this.httpStatusCode = 201;
        this.code = code;
    }
}
