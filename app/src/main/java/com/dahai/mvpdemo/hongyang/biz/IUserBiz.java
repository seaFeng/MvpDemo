package com.dahai.mvpdemo.hongyang.biz;

/**
 * Created by ASUS on 2017/4/28.
 */

public interface IUserBiz {
    public void login(String userName,String password,OnLoginListener loginListener);
}
