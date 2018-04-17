package com.youngmanster.collectionlibrary.network;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by yangyan
 * on 2018/4/2.
 */

public interface RequestService{

    /**
     *找回密码
     */
    @GET
   Observable<ResponseBody> getObservableWithQueryMap(@Url String url, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST
   Observable<ResponseBody> getObservableWithFieldMap(@Url String url, @FieldMap Map<String,Object> map);

    @Multipart
    @POST
    Observable<ResponseBody> getObservableWithImage(@Url String url,@QueryMap Map<String,Object> map,
                                                @Part MultipartBody.Part image);

}
