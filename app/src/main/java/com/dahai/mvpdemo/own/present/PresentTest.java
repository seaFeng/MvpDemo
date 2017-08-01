package com.dahai.mvpdemo.own.present;

import android.util.Log;

import com.dahai.mvpdemo.own.UI.iView.IViewTest;
import com.dahai.mvpdemo.own.data.bean.BaseBeanList;
import com.dahai.mvpdemo.own.data.bean.HoistoryBean;
import com.dahai.mvpdemo.own.data.dataSource.Api;
import com.dahai.mvpdemo.own.data.dataSource.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 张海洋 on 2017-07-31.
 */

public class PresentTest {
    private IViewTest view;

    private List<String> list = new ArrayList<>();

    public PresentTest(IViewTest view){
        this.view = view;
    }

    // 请求网络
    public void requestHoistoryDate(){
        Map<String, String> requestMap = getRequestMap();

        Api api = new RetrofitClient().createApi();
        api.getHository(requestMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBeanList<HoistoryBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("test", "onSubscribe: " + "执行了");
                    }

                    @Override
                    public void onNext(@NonNull BaseBeanList<HoistoryBean> hoistoryBeanBaseBeanList) {
                        Log.e("test", "onNext: " + "执行了。");
                        List<HoistoryBean> result = hoistoryBeanBaseBeanList.result;
                        for (HoistoryBean bean : result) {
                            list.add(bean.getTitle());
                        }
                        view.showData(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("test","错误信息：" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Map<String,String> getRequestMap() {
        //v=1.0&month=10&day=06&key=e5d427da62e36f2b5751bb23de1b66eb
        TreeMap<String,String> map = new TreeMap<>();
        map.put("v","1.0");
        map.put("month","10");
        map.put("day",view.getEditString());
        map.put("key","e5d427da62e36f2b5751bb23de1b66eb");
        return map;
    }
}
