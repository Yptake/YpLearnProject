package com.yptake.yplearnproject.mvp.model.api.service;


import com.yptake.yplearnproject.mvp.model.entity.BaseJson;
import com.yptake.yplearnproject.mvp.model.entity.BaseListResponse;
import com.yptake.yplearnproject.mvp.model.entity.ToutiaoEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface UserService {

    /**
     * @param url http://v.juhe.cn/toutiao/index
     * @param key ef7d16c079f23e32b8d51fe6509cbc14
     * @param type 类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱 乐),
     *             tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     * @return
     */
    @POST
    Observable<BaseJson<ToutiaoEntity>> getToutiaoNews(@Url String url, @Query("key") String key, @Query("type") String type);

    /**
     * @param fileUrl 地址
     * @return 下载apk
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downloadApk(@Url String fileUrl);

}
