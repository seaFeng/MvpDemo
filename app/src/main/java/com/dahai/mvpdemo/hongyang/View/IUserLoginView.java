package com.dahai.mvpdemo.hongyang.View;

import com.dahai.mvpdemo.hongyang.bean.User;

/**
 * Created by ASUS on 2017/5/3.
 */

public interface IUserLoginView {
    String getUserName();
    String getPassword();
    void clearUserName();
    void clearPassword();
    void showLoading();
    void hideLoading();
    void toMainActivity(User user);
    void showFailedError();
}
