package com.example.common.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {

   public List<T> datas;
   public Context mContext;

    public BaseAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(initView(), parent, false);
        return new BaseHolder(view);
    }

    protected abstract int initView();

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        bindHolder(holder, datas.get(position),position);
    }

    protected abstract void bindHolder(BaseHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> views;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        public <T extends View> T findViewById(@IdRes int resId) {
            View view = views.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                views.put(resId, view);
            }
            return (T) view;
        }
    }

}
