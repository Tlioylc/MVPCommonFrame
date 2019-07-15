package com.tlioylc.client.http;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface  HttpService {
    @GET
    Observable<JSONObject> get(@Url String url);

    @GET
    Observable<JSONObject> get(@Url String url, @QueryMap Map<String, String> map);

    @POST
    Observable<JSONObject> post(@Url String url, @Body RequestBody requestBody);

    @PUT
    Observable<JSONObject> put(@Url String url, @Body RequestBody requestBody);

    @PUT
    Observable<JSONObject> put(@Url String url);

    @POST
    Observable<JSONObject> post(@Url String url);

    @PATCH
    Observable<JSONObject> patch(@Url String url);

    @PATCH
    Observable<JSONObject> patch(@Url String url, @Body RequestBody requestBody);

    @DELETE
    Observable<JSONObject> delete(@Url String url);

//    @DELETE
//    Observable<JSONObject> delete(@Url String url, @Body RequestBody requestBody);

    /**
     * 示例delete
     * @param path
     * @param requestBody
     * @return
     *  @HTTP(method = "DELETE",path = "/api/v2/im/chatgroups/{path}/members/kick",hasBody = true)
        Observable<JSONObject> deleteUserFromGroup(@Path("path") String path, @Body RequestBody requestBody);
     */

}
