/**
 * @作者 徐振博
 * @创建时间 2024/2/28 15:57
 */
package com.guidian.usercenter.exception;

import com.guidian.usercenter.exception.http.HttpException;

public class UpdateSuccess extends HttpException {

    public UpdateSuccess(int code) {

        this.httpStatusCode = 200;
        this.code = code;
    }
}
