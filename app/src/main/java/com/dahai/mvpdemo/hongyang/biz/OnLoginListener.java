package com.dahai.mvpdemo.hongyang.biz;

import com.dahai.mvpdemo.hongyang.bean.User;

/**
 * Created by ASUS on 2017/4/28.
 */

public interface OnLoginListener {
    void loginSuccess(User user);
    void loginFailed();
}
