package com.tlioylc.client.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

//post
public class HttpPostParameters {

    public static RequestBody common(@NonNull JSONObject data) {
        return RequestBody.create(MediaType.parse("application/json"), data.toString());
    }

    /**
     * 示例方法
    public static RequestBody demoMethod() {

        JSONObject jsonObject = new JSONObject();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        return requestBody;
    }

     */

    public static RequestBody demoMethod() {

        JSONObject jsonObject = new JSONObject();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        return requestBody;
    }



}
