package com.dahai.mvpdemo.own.data.dataSource;

import com.dahai.mvpdemo.BuildConfig;
import com.dahai.mvpdemo.own.data.bean.BaseBeanList;
import com.dahai.mvpdemo.own.data.bean.HoistoryBean;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dahai.mvpdemo.own.data.dataSource.UrlManage.HOISTORYROOT;

/**
 *  retrofit 提供客户端。
 * Created by 张海洋 on 2017-07-31.
 */

public class RetrofitClient {
    private Retrofit client;

    public RetrofitClient(){
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9,TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        client = new Retrofit.Builder().baseUrl(HOISTORYROOT)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getClient(){
        return client;
    }

    public Api createApi() {
        Api api = getClient().create(Api.class);
        return api;
    }
}
