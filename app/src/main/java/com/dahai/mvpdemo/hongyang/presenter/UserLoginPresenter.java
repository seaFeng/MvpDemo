package com.dahai.mvpdemo.hongyang.presenter;

import android.os.Handler;

import com.dahai.mvpdemo.hongyang.View.IUserLoginView;
import com.dahai.mvpdemo.hongyang.bean.User;
import com.dahai.mvpdemo.hongyang.biz.IUserBiz;
import com.dahai.mvpdemo.hongyang.biz.OnLoginListener;
import com.dahai.mvpdemo.hongyang.biz.UserBiz;


/**
 * Created by ASUS on 2017/5/3.
 */

public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();


    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    public void login(){
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });
    }

    public void clear(){
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }
}
