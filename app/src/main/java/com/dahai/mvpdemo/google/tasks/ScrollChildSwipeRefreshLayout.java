package com.dahai.mvpdemo.google.tasks;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 张海洋 on 2017/7/9.
 */

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {
    private View mScrollUpChild;

    public ScrollChildSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild,-1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUPChild(View view) {
        mScrollUpChild = view;
    }
}
