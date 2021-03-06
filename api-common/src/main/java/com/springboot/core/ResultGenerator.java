package com.springboot.core;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "成功";

    private static final String DEFAULT_ERROR_MESSAGE = "失败";


    public static final String ID_NOT = "Required Integer parameter 'id' is not present";

    public static final String MESSAGE_NOT_USERNAME_OR_GROUPID = "Required String parameter 'groupId' is not present or String parameter 'username' is not present";


    public static final String ID_ERROR = "找不到对应数据";

    public static final String Token_ERROR = "Token为空";


    //超时时间
    public static final int USER_OVER_TIME = 20;



    /**
     * 图片占位符开始
     */
    public static final String API_PLACEHOLDER_IMAGE_BEGIN = "_img_start_";
    /**
     * 图片占位符结束
     */
    public static final String API_PLACEHOLDER_IMAGE_END = "_img_end_";
    /**
     * 视频占位符开始
     */
    public static final String API_PLACEHOLDER_VIDEO_BEGIN = "_video_start_";
    /**
     * 视频占位符结束
     */
    public static final String API_PLACEHOLDER_VIDEO_END = "_video_end_";
    /**
     * API接口调用状态-成功
     */
    public static final String API_STATUS_SUCCESS = "true";
    public static final String G_API_STATUS_SUCCESS = "true";
    /**
     * API接口调用状态失败
     */
    public static final String API_STATUS_FAIL = "false";
    public static final String G_API_STATUS_FAIL = "false";
    /**
     * API接口消息-成功
     */
    public static final String API_MESSAGE_SUCCESS = "success";
    public static final String G_API_MESSAGE_SUCCESS = "success";
    /**
     * API接口消息-缺少参数
     */
    public static final String API_MESSAGE_PARAM_REQUIRED = "param_required";
    public static final String G_API_MESSAGE_PARAM_REQUIRED = "param_required";
    /**
     * API接口消息-参数错误
     */
    public static final String API_MESSAGE_PARAM_ERROR = "param_error";
    public static final String G_API_MESSAGE_PARAM_ERROR = "param_error";

    /**
     * API接口消息-appId参数错误
     */
    public static final String API_MESSAGE_APP_PARAM_ERROR = "appId_not_exist_or_appId_disabled";
    /**
     * API接口消息-用户未找到
     */
    public static final String API_MESSAGE_USER_NOT_FOUND = "user_not_found";
    /**
     * API接口消息-内容未找到
     */
    public static final String API_MESSAGE_CONTENT_NOT_FOUND = "content_not_found";
    /**
     * API接口消息-用户未登录
     */
    public static final String API_MESSAGE_USER_NOT_LOGIN = "user_not_login";
    /**
     * API接口消息-SESSION错误
     */
    public static final String API_MESSAGE_SESSION_ERROR = "session_error";
    /**
     * API接口消息-密码错误
     */
    public static final String API_MESSAGE_PASSWORD_ERROR = "用户名或者密码错误";
    /**
     * API接口消息-用户名已存在
     */
    public static final String API_MESSAGE_USERNAME_EXIST = "username_exist";
    /**
     * API接口消息-原密码错误已存在
     */
    public static final String API_MESSAGE_ORIGIN_PWD_ERROR = "origin_password_invalid";
    /**
     * API接口消息-上传失败
     */
    public static final String API_MESSAGE_UPLOAD_ERROR = "upload_file_error";
    /**
     * API接口消息-订单编号已经使用
     */
    public static final String API_MESSAGE_ORDER_NUMBER_USED = "order_number_used";
    /**
     * API接口消息-订单编号错误
     */
    public static final String API_MESSAGE_ORDER_NUMBER_ERROR = "order_number_error";
    /**
     * API接口消息-订单金额不足
     */
    public static final String API_MESSAGE_ORDER_AMOUNT_NOT_ENOUGH = "order_amount_not_enough";
    /**
     * API接口消息-重复请求API
     */
    public static final String API_MESSAGE_REQUEST_REPEAT = "request_api_repeat";

    /**
     * API接口消息-用户没有权限
     */
    public static final String API_MESSAGE_USER_NOT_HAS_PERM = "user_has_not_perm";
    /**
     * API接口消息-用户超时
     */
    public static final String API_MESSAGE_USER_OVER_TIME = "user_over_time";
    /**
     * API接口消息-用户身份伪造
     */
    public static final String API_MESSAGE_USER_FORGERY = "user_identity_forgery";

    public static final String API_MESSAGE_ACCOUNT_DISABLED = "disabled";
    /**
     * API接口消息 -对象未找到
     */
    public static final String API_MESSAGE_OBJECT_NOT_FOUND = "object_not_found";
    /**
     * API接口调用服务器响应错误
     */
    public static final String API_STATUS_APPLICATION_ERROR = "application_error";
    /**
     * API接口消息-文件不存在
     */
    public static final String API_MESSAGE_FILE_NOT_FOUNT = "file_not_found";
    /**
     * API接口消息-数据引用错误
     */
    public static final String API_MESSAGE_DATA_INTERGER_VIOLATION = "DataIntegrityViolation";
    /**
     * API接口消息-sql错误
     */
    public static final String API_MESSAGE_SQL_ERROR = "sql_error";
    /**
     * API接口消息-参数类型错误
     */
    public static final String API_MESSAGE_PARAM_TYPE_ERROR = "param_type_error";
    /**
     * API接口消息-参数绑定错误
     */
    public static final String API_MESSAGE_PARAM_BIND_ERROR = "param_bind_error";
    /**
     * API接口消息 -删除错误
     */
    public static final String API_MESSAGE_DELETE_ERROR = "delete_error";
    /**
     * API接口消息 -有关连数据删除错误
     */
    public static final String API_MESSAGE_CONSTRAINT_DELETE_ERROR = "constraint_delete_error";
    /**
     * API接口消息-模型已存在
     */
    public static final String API_MESSAGE_MODEL_EXIST = "model_is_exist";
    /**
     * API接口消息-字段已存在
     */
    public static final String API_MESSAGE_FIELD_EXIST = "field_is_exist";
    /**
     * API接口消息-访问路径已存在
     */
    public static final String API_MESSAGE_ACCESSPATH_EXIST = "accessPath_is_exist";
    /**
     * API接口消息-域名已存在
     */
    public static final String API_MESSAGE_DOMAIN_EXIST = "domain_is_exist";
    /**
     * API接口消息-角色等级错误
     */
    public static final String API_MESSAGE_ROLE_LEVEL_ERROR = "role_level_error";
    /**
     * API接口消息-创建微信菜单错误
     */
    public static final String API_MESSAGE_CREATE_MENU_ERROR = "create_menu_error";
    /**
     * API接口消息-发送微信消息错误
     */
    public static final String API_MESSAGE_SEND_TO_WEXIN_ERROR = "send_to_weixin_error";
    /**
     * API接口消息-内容类型ID已存在
     */
    public static final String API_MESSAGE_CONTENTTYPE_ID_EXIST = "contentType_id_is_exist";
    /**
     * API接口消息-模板未找到
     */
    public static final String API_MESSAGE_TEMPLATE_NOT_FOUNT = "template_not_found";
    /**
     * API接口消息-模板错误
     */
    public static final String API_MESSAGE_TEMPLATE_PARESE_ERROR = "template_parese_exception";
    /**
     * API接口消息-静态化未开启
     */
    public static final String API_MESSAGE_STATIC_PAGE_NOT_OPEN = "static_page_not_open";
    /**
     * API接口消息-内容未终审
     */
    public static final String API_MESSAGE_CONTENT_NOT_CHECKED = "content_not_checked";
    /**
     * API接口消息-部门名称已存在
     */
    public static final String API_MESSAGE_DEPARTMENT_NAME_EXIST = "department_name_exist";
    /**
     * API接口消息-生成错误
     */
    public static final String API_MESSAGE_CREATE_ERROR = "create_exception";
    /**
     * API接口消息-tag名称已存在
     */
    public static final String API_MESSAGE_TAG_NAME_EXIST = "tag_name_exist";
    /**
     * API接口消息-恢复错误
     */
    public static final String API_MESSAGE_DB_REVERT_ERROR = "db_revert_error";
    /**
     * API接口消息-导出错误
     */
    public static final String API_MESSAGE_EXPORT_ERROR = "export_error";

    /**
     * API接口消息-超时
     */
    public static final String API_MESSAGE_USER_STATUS_OVER_TIME = "over_time";

    /**
     * API接口消息-登陆状态
     */
    public static final String API_MESSAGE_USER_STATUS_LOGIN = "login";
    /**
     * API接口消息-未登陆状态
     */
    public static final String API_MESSAGE_USER_STATUS_NOT_LOGIN = "no_login";

    /**
     * API接口消息-防火墙禁止访问
     */
    public static final String API_MESSAGE_FIREWALL_FORBID = "fire_wall_forbid";

    /**
     * API接口消息-AES128解密错误
     */
    public static final String API_MESSAGE_AES128_ERROR = "aes128_error";
    /**
     * API接口消息-转账失败
     */
    public static final String API_MESSAGE_PAY_ERROR = "pay_error";

    public static final String API_ARRAY_SPLIT_STR = ",";
    public static final String API_LIST_SPLIT_STR = ";";

    public static final String API_MESSAGE_NOT_CONTENT_ERROR = "not_content_error";

    public static final int DISTRIBUTE_THREAD_COUNT = 10;

    /**
     * 最大静态化数量
     */
    public static final int MAX_STATIC_COUNT = 10;
    /**
     * 分页符
     */
    public static final String NEXT_PAGE = "[NextPage][/NextPage]";
    /**
     * 二维码错误
     */
    public static final String API_MESSAGE_CAPTCHA_CODE_ERROR = "captcha_code_error";
    /**
     * 短信发送间隔时间不足
     */
    public static final String API_MESSAGE_INTERVAL_NOT_ENOUGH = "interval_time_not_enough";
    /**
     * 短信服务未设置
     */
    public static final String API_MESSAGE_SMS_NOT_SET = "sms_not_set";
    /**
     * 短信服务每日发送限制
     */
    public static final String API_MESSAGE_SMS_LIMIT = "sms_send_count_limit";
    /**
     * 手机号已被注册或绑定
     */
    public static final String API_MESSAGE_MOBILE_PHONE_EXIST = "mobile_phone_exist";
    /**
     * 短信服务请求错误
     */
    public static final String API_MESSAGE_SMS_ERROR = "sms_error";
    /**
     * 找回密码短信服务禁用
     */
    public static final String API_MESSAGE_SMS_IS_DISABLE = "sms_is_disable";
    /**
     * 未设置手机号码
     */
    public static final String API_MESSAGE_NOT_MOBILE = "未设置手机号码";
    /**
     * 手机号码不匹配
     */
    public static final String API_MESSAGE_MOBILE_MISMATCHING = "mobile_mismatching";

    /**
     * edit  内容状态修改失败
     */
    public static final String CONTENT_STATUS_EDIT_ERROR = "content_edit_error";
    /**
     * edit 审核失败
     */
    public static final String API_MESSAGE_CONTENT_CHECK_ERROR = "部分内容审核失败";

    /**
     * 失效的Token
     */
    public static final String LOSE_EFFECT_TOKEN = "无效的Token";
    /**
     * 用户所属组织或上级组织已经被禁用
     */
    public static final String USERNAME_LOSE_EFFECT = "用户所属组织或上级组织已经被禁用";
    /**
     * 通用判断
     *
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 无参数调用默认信息
     *
     * @return
     */
    public static Result genErrorResult() {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(DEFAULT_ERROR_MESSAGE);
    }

    /**
     * 有参数调用传入参数
     *
     * @return
     */
    public static Result genErrorSetMsgErrorResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    /**
     * 无参数调用默认信息
     *
     * @return
     */
    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 有参数调用传入参数
     *
     * @return
     */
    public static Result genSuccessSetMsgResult(String message) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message);
    }
    //用户名可用
    public static Result genIsUserNameMsgResult(String message) {
        return new Result()
                .setCode(ResultCode.USERNAME_IS)
                .setMessage(message);
    }
    //用户名不可用
    public static Result genNotUserNameMsgResult(String message) {
        return new Result()
                .setCode(ResultCode.USERNAME_NOT)
                .setMessage(message);
    }
    //用户所属组织或上级组织已经被禁用
    public static Result genUserNameIsNotLoginMsgResult() {
        return new Result()
                .setCode(ResultCode.USERNAME_NOT_USER)
                .setMessage(USERNAME_LOSE_EFFECT);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setBody(data)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static <T> DownLoadResult<T> genSuccessResult1(T data,String url,String areaServiceUrl) {
        DownLoadResult downLoadResult= new DownLoadResult();
        downLoadResult.setDownurl(url);
        downLoadResult.setAreaServiceUrl(areaServiceUrl);
        downLoadResult.setCode(ResultCode.SUCCESS);
        downLoadResult.setBody(data);
        downLoadResult.setMessage(DEFAULT_SUCCESS_MESSAGE);

        return downLoadResult;
    }
    public static <T> DownLoadResult<T> genErrorSetMsgErrorResult1(String message) {
        DownLoadResult downLoadResult= new DownLoadResult();
        downLoadResult.setCode(ResultCode.FAIL);
        downLoadResult.setMessage(message);

        return downLoadResult;
    }
    public static <T> Result<T> genErrorResult(T data) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setBody(data)
                .setMessage(DEFAULT_ERROR_MESSAGE);
    }

    /**
     * Token信息为空
     *
     * @return
     */
    public static Result genTokenErrorResult() {
        return new Result()
                .setCode(ResultCode.Token_IS_NULL)
                .setMessage(Token_ERROR);
    }
    /**
     * 参数信息为空
     *
     * @return
     */
    public static Result genParamsErrorResult() {
        return new Result()
                .setCode(ResultCode.PARAM_IS_NULL)
                .setMessage(API_MESSAGE_PARAM_REQUIRED);
    }
    /**
     * 无效的Token
     *
     * @return
     */
    public static Result genTokenLoseErrorResult() {
        return new Result()
                .setCode(ResultCode.Token_IS_LOSE)
                .setMessage(LOSE_EFFECT_TOKEN);
    }
    public static Result API_CODE_PASSWORD_ERROR() {
        return new Result()
                .setCode(ResultCode.API_CODE_PASSWORD_ERROR)
                .setMessage(API_MESSAGE_PASSWORD_ERROR);
    }
}
