package com.dahai.mvpdemo.hongyang.biz;

import com.dahai.mvpdemo.hongyang.bean.User;

/**
 * Created by ASUS on 2017/4/28.
 */

public class UserBiz implements IUserBiz {
    @Override
    public void login(final String userName, final String password, final OnLoginListener loginListener) {
        // 模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 模拟登录成功
                if ("zhy".equals(userName) && "123".equals(password)) {
                    User user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);
                    loginListener.loginSuccess(user);
                } else {
                    loginListener.loginFailed();
                }
            }
        }.start();
    }
}
