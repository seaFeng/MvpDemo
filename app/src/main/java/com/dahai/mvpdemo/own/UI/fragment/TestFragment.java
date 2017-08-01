package com.dahai.mvpdemo.own.UI.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dahai.mvpdemo.R;
import com.dahai.mvpdemo.own.UI.adapter.TestAdatper;
import com.dahai.mvpdemo.own.UI.iView.IViewTest;
import com.dahai.mvpdemo.own.present.PresentTest;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements IViewTest,View.OnClickListener{
    private View rootView;
    private EditText et_date;
    private Button btn_select;
    private RecyclerView recyclerView;

    private String date;
    private List<String> list;
    private PresentTest present = new PresentTest(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        et_date = (EditText) rootView.findViewById(R.id.et_date);
        btn_select = (Button) rootView.findViewById(R.id.btn_select);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_select.setOnClickListener(this);
    }

    @Override
    public String getEditString() {
        Editable text = et_date.getText();
        return text.toString();
    }

    @Override
    public void showData(List<String> list) {
        recyclerView.setAdapter(new TestAdatper(list));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_select:
                Log.e("test","点击事件执行了");
                present.requestHoistoryDate();
                break;
        }
    }
}
