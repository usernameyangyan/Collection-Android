package com.youngmanster.collectionlibrary.network;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yangyan
 * on 2018/4/2.
 */

public interface RequestService{

    @GET
   Observable<ResponseBody> getObservableWithQueryMap(@Url String url, @QueryMap Map<String, Object> map);

    @GET
    Observable<ResponseBody> getObservableWithQueryMapWithHeaders(@Url String url, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> header);

    @POST
    Observable<ResponseBody> getObservableWithQueryJsonParam(@Url String url, @Body RequestBody json);

    @POST
    Observable<ResponseBody> getObservableWithQueryJsonParamWithHeaders(@Url String url, @Body RequestBody json, @HeaderMap Map<String, String> headers);

    @POST
    Observable<ResponseBody> getObservableWithQueryMapByPost(@Url String url, @QueryMap Map<String, Object> map);

    @POST
    Observable<ResponseBody> getObservableWithQueryMapByPostWithHeaders(@Url String url, @QueryMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST
   Observable<ResponseBody> getObservableWithFieldMap(@Url String url, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> getObservableWithFieldMapWithHeaders(@Url String url, @FieldMap Map<String, Object> map, @HeaderMap Map<String, String> headers);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @QueryMap Map<String, Object> map,
                                                    @Part MultipartBody.Part image);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFileWithHeaders(@Url String url, @QueryMap Map<String, Object> map,
                                                               @Part MultipartBody.Part image, @HeaderMap Map<String, String> headers);


 @Multipart
 @POST
 Observable<ResponseBody> uploadFile(@Url String url, @QueryMap Map<String, Object> map,
                                                  @Part() MultipartBody.Part[] images);

 @Multipart
 @POST
 Observable<ResponseBody> uploadFileWithHeaders(@Url String url, @QueryMap Map<String, Object> map,
                                                             @Part() MultipartBody.Part[] images, @HeaderMap Map<String, String> headers);


 @GET
 @Streaming
 Observable<ResponseBody> downloadFile(@Url String url,@HeaderMap Map<String, String> headers);

}
