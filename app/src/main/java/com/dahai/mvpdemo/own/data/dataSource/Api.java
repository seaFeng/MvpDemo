package com.dahai.mvpdemo.own.data.dataSource;

import com.dahai.mvpdemo.own.data.bean.BaseBeanList;
import com.dahai.mvpdemo.own.data.bean.HoistoryBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * retrofit请求接口
 * Created by 张海洋 on 2017-07-31.
 */

public interface Api {

    @GET(UrlManage.HOISTROY)
    Observable<BaseBeanList<HoistoryBean>> getHository(@QueryMap Map<String,String> map);
}
