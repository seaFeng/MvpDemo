package com.dahai.mvpdemo.own.UI.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dahai.mvpdemo.R;
import com.dahai.mvpdemo.own.UI.fragment.TestFragment;

public class MvpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initView();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_content,new TestFragment()).commit();
    }
}
