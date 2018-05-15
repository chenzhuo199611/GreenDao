package com.bawie.chenzhuo.greendao_02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2018/5/15.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
    private List<User> list;
    private Context context;

    public MyAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.rlv_item,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyHolder holder, int position) {
        holder.tv_content.setText("ID："+list.get(position).getId()+"  名字："+list.get(position).getName()+"  年龄："+list.get(position).getAge()+"  性别："+list.get(position).getSex()+"  薪资："+list.get(position).getSalary());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final TextView tv_content;
        public MyHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
