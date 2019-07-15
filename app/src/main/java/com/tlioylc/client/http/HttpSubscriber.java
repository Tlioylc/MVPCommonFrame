package com.tlioylc.client.http;


import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.tlioylc.client.R;
import com.tlioylc.client.bean.ErrorData;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import static com.tlioylc.client.http.HttpHandleException.ERROR.NETWORD_ERROR;
import static com.tlioylc.client.http.HttpHandleException.ERROR.SERVER_ERROR;
import static com.tlioylc.client.http.HttpHandleException.ERROR.UNAUTHORIZED_ERROR;

public abstract class HttpSubscriber<T> extends Subscriber<T> {

    public abstract void success(T o);

    public abstract void fail(Throwable e);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T o) {
        ErrorData errorData = JSON.parseObject(o.toString(), ErrorData.class);
        if (errorData != null && errorData.getStatus() != 200) {
//            ToastUtils.showShort(getToastString(errorData.getError().getStatus()));
            int errorCode = 403;
            Response<String> response = error(errorCode, ResponseBody.create(MediaType.parse("application/json"), o.toString()));
            HttpException httpException = new HttpException(response);
            handleError(httpException);
        } else {
            if (o instanceof JSONObject)
                success((T) ((JSONObject) o).getJSONObject("data"));
            else
                success(o);
        }
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
    }

    public static <T> Response<T> error(int code, ResponseBody body) {
        if (code < 400) throw new IllegalArgumentException("code < 400: " + code);
        return Response.error(body, new okhttp3.Response.Builder() //
                .code(code)
                .message("error")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("http://localhost/").build())
                .build());
    }

    private void handleError(Throwable e) {
        HttpCustomException exception = HttpHandleException.handleException(e);
        if (!TextUtils.isEmpty(exception.body)) {
            try {
                if (Looper.myLooper() == Looper.getMainLooper()) { // UI主线程
                    if (exception.code == UNAUTHORIZED_ERROR) {
                        hasLogOut();
                    } else if (exception.code == NETWORD_ERROR) {
                        ToastUtils.showShort(R.string.net_error_toast);
                    } else if (exception.code == SERVER_ERROR) {
                        ToastUtils.showShort(R.string.server_error_toast);
                    } else {
                        ErrorData errorData = JSON.parseObject(exception.body, ErrorData.class);
                        exception.code = errorData.getStatus();
                        String toast = errorData.getMessage();
                        int errorCode = errorData.getStatus();
                        if (!TextUtils.isEmpty(toast)) {
                            exception.setMessage(toast);
                            if (errorCode == 401) {
                                hasLogOut();
                            }

                        }
                    }

                } else {
                    ErrorData errorData = JSON.parseObject(exception.body, ErrorData.class);
                    exception.code = errorData.getStatus();
//                    if (errorData != null) {
                    String toast = errorData.getMessage();
                    if (!TextUtils.isEmpty(toast)) {
                        exception.setMessage(toast);
                    }
//                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                ToastUtils.showShort(exception.message);
            }
        }
        ToastUtils.showShort(exception.message);
        fail(exception);
    }

    private void hasLogOut() {
        ToastUtils.showShort("您的登录已过期");
        HttpRequestClient.getClient().clearCookie();
    }


}
