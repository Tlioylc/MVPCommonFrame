package com.tlioylc.client.http;

import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.ihsanbal.logging.LoggingInterceptor;
import com.tlioylc.client.BuildConfig;
import com.tlioylc.client.LihApplication;
import com.tlioylc.client.local.SPKey;
import com.tlioylc.client.module.base.BaseConfig;
//import com.umeng.analytics.AnalyticsConfig;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.HttpUrl;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ihsanbal.logging.Level.BASIC;

/**
 * 示例代码
 * HttpRequestClient.getInstance()
 * .post(HttpUrl.obtain().xxx(), HttpPostParameters.xxx())
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * .subscribe(new HttpSubscriber<JSONObject>() {
 *
 * @Override public void success(JSONObject o) {
 * }
 * @Override public void fail(Throwable e) {
 * e.printStackTrace();
 * }
 * });
 */

public class HttpRequestClient {

    private volatile String uid = null;
    private volatile String token = null;
    private Retrofit retrofit;

    private HttpRequestClient() {

    }

    private static class LazyHolder {
        public static final HttpRequestClient INSTANCE = new HttpRequestClient();
    }

    public static final HttpRequestClient getClient() {
        return LazyHolder.INSTANCE;
    }

    public static final HttpService getInstance() {
        return LazyHolder.INSTANCE.getService();
    }

    public static final HttpService getInstance(String host) {
        return LazyHolder.INSTANCE.getService(host);
    }

    private Retrofit getRetrofit(String host) {
        final PersistentCookieStore cookieStore = new PersistentCookieStore(Objects.requireNonNull(LihApplication.Companion.obtain()));


        //初始化OkHttp
        //添加请求头
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//重试机制
                .addInterceptor(chain -> {
                    Request.Builder builder1 = chain.request().newBuilder();
                    if (!TextUtils.isEmpty(uid)) {
                        builder1.addHeader("uid", uid);
                        builder1.addHeader("token", token);
//                            builder.addHeader("Authorization","Basic "
//                                    + Base64.encodeToString((uid +":"+ token).getBytes(), Base64.NO_WRAP));
                    }
//                    String channel = AnalyticsConfig.getChannel(PCApplication.obtain());
//                    builder1.addHeader("appChannel", TextUtils.isEmpty(channel) ? "" : channel);
                    builder1.addHeader("appVersion", AppUtils.getAppVersionName());
                    builder1.addHeader("appPackageName", AppUtils.getAppPackageName());
                    builder1.addHeader("deviceId", DeviceUtils.getAndroidID());
                    builder1.addHeader("deviceType", "1");
                    builder1.addHeader("appSystem", "android_" + DeviceUtils.getSDKVersionName());
                    Request request = builder1.build();
                    return chain.proceed(request);
                })
                .cookieJar(new CookieJar() {//设置自动保存cookie
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (cookies != null && cookies.size() > 0) {
                            for (Cookie item : cookies) {
                                cookieStore.add(url, item);
                            }
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies;
                    }
                })

                .connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(10, TimeUnit.SECONDS);      //设置读取超时 10s

        //增加拦截器，打印请求/响应信息
        if (!BuildConfig.IS_RELEASE) {
            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .build();
            builder.addInterceptor(httpLoggingInterceptor);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


    //多个service时使用
    public <T> T create(Class<T> service, String host) {
        retrofit = getRetrofit(host);
        checkNotNull(service, "service is null");
        return retrofit.create(service);
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public void clearCookie() {
        uid = null;
        token = null;
    }

    private HttpService getService() {
        if (TextUtils.isEmpty(uid)) {
            uid = SPUtils.getInstance().getString(SPKey.UID,"");
            token = SPUtils.getInstance().getString(SPKey.TOKEN,"");
        }
        return getService(BaseConfig.INSTANCE.getApiHost());
    }

    private HttpService getService(String host) {
        retrofit = getRetrofit(host);
        return retrofit.create(HttpService.class);
    }
}

