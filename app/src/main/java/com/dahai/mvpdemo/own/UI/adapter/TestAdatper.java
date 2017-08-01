package com.dahai.mvpdemo.own.UI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dahai.mvpdemo.R;

import java.util.List;

/**
 * Created by 张海洋 on 2017-08-01.
 */

public class TestAdatper extends RecyclerView.Adapter<TestAdatper.MViewHolder> {
    private List<String> list;

    public TestAdatper(List<String> list){
        this.list = list;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.item_test, parent, false);

        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;

        public MViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_test_title);
        }
    }
}
