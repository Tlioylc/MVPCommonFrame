package com.tlioylc.client.http;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

public class HttpHandleException {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    public static HttpCustomException handleException(Throwable e){
        HttpCustomException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;

            int errorCode = httpException.code();
            if (errorCode == UNAUTHORIZED) {
                ex = new HttpCustomException(e, ERROR.UNAUTHORIZED_ERROR);
                ex.message = "用户验证错误";
                ex.body = "登录已过期";
            }else if (errorCode >= 500 && errorCode < 600) {
                ex = new HttpCustomException(e, ERROR.SERVER_ERROR);
                ex.message = "服务器错误";
                ex.body = "服务器网络错误";
            }else {
                ex = new HttpCustomException(e, ERROR.HTTP_ERROR);
                ex.message = "网络错误";  //均视为网络错误
                ex.body = "服务器网络错误";
            }
        } else if(e instanceof SocketTimeoutException){
            ex = new HttpCustomException(e, ERROR.NETWORD_ERROR);
            ex.message = "链接超时";            //均视为解析错误
        }
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new HttpCustomException(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";            //均视为解析错误
        }else if(e instanceof ConnectException || e instanceof UnknownHostException){
            ex = new HttpCustomException(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";  //均视为网络错误
        }else {
            ex = new HttpCustomException(e, ERROR.UNKNOWN);
            ex.message = "未知错误";          //未知错误
        }

        try {
            ResponseBody body = ((HttpException) e).response().errorBody();
            String errorString = body.string();
            if(!TextUtils.isEmpty(errorString))
                ex.body = errorString;
        }  catch (Exception IOe) {
            IOe.printStackTrace();
            ex.body = "连接失败";
        }
        return ex;
    }


    /**
     * 约定异常
     */

    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 用户验证错误
         */
        public static final int UNAUTHORIZED_ERROR = 1004;
        /**
         * 服务端异常
         */
        public static final int SERVER_ERROR = 1005;
    }
}
