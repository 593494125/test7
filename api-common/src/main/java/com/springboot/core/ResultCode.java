package com.springboot.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(201),//失败
    Token_NOT(202),//token 验证失败
    Token_IS_NULL(203),//token
    PARAM_IS_NULL(204),//传参为空
    Token_IS_LOSE(205),//token为空
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500),//服务器内部错误
    LOGONEXPIRES(302),//未登录
    USERNAME_IS(303),//用户名可用
    USERNAME_NOT(306),//用户名已经存在
    USERNAME_NOT_USER(307),//用户所属组织或上级组织已经被禁用
//    API_CODE_USER_STATUS_LOGIN(1),//登陆状态
//    API_CODE_USER_STATUS_LOGOUT(2),//退出状态
//    API_CODE_USER_STATUS_OVER_TIME(3),//超时状态
    API_CODE_PASSWORD_ERROR(304);//密码错误


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



    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
