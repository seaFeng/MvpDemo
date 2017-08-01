package com.dahai.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dahai.mvpdemo.hongyang.View.IUserLoginView;
import com.dahai.mvpdemo.hongyang.bean.User;
import com.dahai.mvpdemo.hongyang.presenter.UserLoginPresenter;

public class HongYangActivity extends AppCompatActivity implements IUserLoginView,View.OnClickListener{
    private EditText mEtUsername,mEtPassword;
    private Button mBtnLogin,mBtnClear;
    private ProgressBar mPbLoading;

    private UserLoginPresenter presenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    /**
     *  初始化View
     */
    private void initViews() {
        mEtUsername = (EditText) findViewById(R.id.id_et_username);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mBtnClear = (Button) findViewById(R.id.id_btn_clear);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mPbLoading = (ProgressBar) findViewById(R.id.id_pb_loading);
        mBtnLogin.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.id_btn_login:
                presenter.login();
                break;
            case R.id.id_btn_clear:
                presenter.clear();
                break;

        }
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        mEtUsername.setText("");
    }

    @Override
    public void clearPassword() {
        mEtPassword.setText("");
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        Toast.makeText(this, "前往MainActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }

}
