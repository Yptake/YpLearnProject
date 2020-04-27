package com.yptake.yplearnproject.mvp.model.api;

/**
 * 接口和错误码存放处
 */
public interface Api {

    //    String APP_DOMAIN = "http://47.106.69.80";
//    String APP_DOMAIN = "http://audio-api.jiangzi.com";
    String APP_DOMAIN = "http://tapi.oscaches.com";

    //  成功
    int REQUEST_SUCCESS = 0;
    //  服务器繁忙
    int REQUEST_BUSY_SERVER = -1;
    // 接口路径错误
    int REQUEST_INTERFACE_PATH_ERROR = 1001;
    // 参数错误
    int REQUEST_PARAMETER_ERROR = 1003;
    // 未找到相关信息
    int REQUEST_NOT_FOUND_RELATED = 1004;
    // 服务器异常
    int REQUEST_SERVER_EXCEPTION = 1005;
    // device-uuid不能为空
    int REQUEST_DEVICE_UUID_EMPTY = 2001;
    // app-version不能为空
    int REQUEST_APP_VERSION_EMPTY = 2002;
    // device-type不能为空
    int REQUEST_DEVICE_TYPE_EMPTY = 2003;
    // 用户Token不能为空
    int REQUEST_TOEKN_EMPTY = 2004;
    // 用户Token错误
    int REQUEST_TOEKN_ERROR = 2005;
    // app_id错误
    int REQUEST_APP_ID_ERROR = 3001;
    // timestamp不能为空
    int REQUEST_TIMESTAMP_EMPTY = 3002;
    // sign不能为空
    int REQUEST_SIGN_EMPTY = 3004;
    // 签名错误
    int REQUEST_SIGN_ERROR = 3005;
    // timestamp已经失效
    int REQUEST_TIMESTAMP_EXPIRED = 3006;
    // 未授权登陆
    int REQUEST_UNAUTHORIZED_LOGIN = 4001;




}
