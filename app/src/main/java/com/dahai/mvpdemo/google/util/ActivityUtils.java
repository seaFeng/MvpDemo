package com.dahai.mvpdemo.google.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 张海洋 on 2017/7/9.
 */

public class ActivityUtils {
    /**
     *  the {@code fragment} is aded to the container view with id {@code frameId}.the operation is
     *   performed by the {@code fragmentManager}
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment,int frameId){
        checkNotNull(fragment);
        checkNotNull(fragmentManager);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId,fragment);
        transaction.commit();
    }
}
