package com.dahai.mvpdemo.own.UI.iView;

import java.util.List;

/**
 * Created by 张海洋 on 2017-07-31.
 */

public interface IViewTest {
    // 获取用户数据。
    String getEditString();

    // 展示网络数据；
    void showData(List<String> list);
}
