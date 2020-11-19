package com.springboot.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result<T> {




    /**
     * 返回的错误代码
     * 200 成功
     * 201 业务失败
     * 202 Token 失效
     * 401 未签名（接口签名暂时不验证）
     * 404 接口不存在
     * 500 服务器内部错误
     * 302 未登录
     * 304 登录密码错误
     */
    private int code;

    private String message;

    private T body;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getBody() {
        return body;
    }

    public Result setBody(T body) {
        this.body = body;
        return this;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
