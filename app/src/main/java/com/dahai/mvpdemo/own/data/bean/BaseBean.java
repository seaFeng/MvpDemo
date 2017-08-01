package com.dahai.mvpdemo.own.data.bean;

import java.util.List;

/**
 * Created by 张海洋 on 2017-07-31.
 */

public class BaseBean<T> {
    public int error_code;
    public String reason;
    public T result;
}
