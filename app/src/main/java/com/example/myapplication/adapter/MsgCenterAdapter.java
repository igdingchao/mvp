package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bean.MsgCenterBean;
import com.example.myapplication.view.SlideItemView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MsgCenterAdapter extends RecyclerView.Adapter<MsgCenterAdapter.ViewHolder> {

    List<MsgCenterBean> mArrayList;
    Context mContext;
    int mCheckNumber = 0;
    public SlideItemView mSlideItemView;
    private boolean mIsEnableEdit;

    public MsgCenterAdapter(Context context, List<MsgCenterBean> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_msg_center, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImgSelect.setTag(position);
        holder.mImgSelect.setVisibility(isEnableEdit() ? View.VISIBLE : View.GONE);
        holder.mImgSelect.setSelected(mArrayList.get(position).getIsSelected());

        holder.mTvContent.setText(mArrayList.get(position).getContent());
        holder.mImg.setImageResource(mArrayList.get(position).getIcon());
        holder.mTvTitle.setText(mArrayList.get(position).getTitle());
        holder.mDataTime.setText("" + mArrayList.get(position).getTime());

        SlideItemView slideItemView = (SlideItemView) holder.itemView;
        slideItemView.setOnStateChangeListener(new SlideItemView.OnStateChangeListener() {
            @Override
            public void onDown(SlideItemView view) {
                if (mSlideItemView != null && mSlideItemView != view) {
                    mSlideItemView.closeDelItem();
                }
            }

            @Override
            public void onOpen(SlideItemView view) {
                mSlideItemView = view;
            }

            @Override
            public void onClose(SlideItemView view) {
                if (mSlideItemView == view) {
                    mSlideItemView = null;
                }
            }
        });

        holder.mImgDelete.setOnClickListener(v -> {

            if (mCheckNumber > 0) {
                MsgCenterBean msgCenterBean = mArrayList.get(position);
                if (msgCenterBean.getIsSelected()) {
                    mCheckNumber--;
                }
            }
            notifyItemRemoved(position);
            mArrayList.remove(position);
            notifyItemRangeChanged(position, getItemCount() - position);
            if (mSlideItemView != null) {
                mSlideItemView.closeDelItem();
            }
            mTotalCheck.total(mCheckNumber, mCheckNumber == mArrayList.size(), mArrayList.size());
        });

        holder.mImgSelect.setOnClickListener(view -> {
            if (!mArrayList.get(position).getIsSelected()) {
                mArrayList.get(position).setIsSelected(true);
                mCheckNumber++;
            } else {
                mArrayList.get(position).setIsSelected(false);
                mCheckNumber--;
            }
            mTotalCheck.total(mCheckNumber, mCheckNumber == mArrayList.size(), mArrayList.size());
//                notifyDataSetChanged();这样会有问题，会造成勾选错乱，如果只是改变某一个，就不需要把所有的都进行刷新。
            notifyItemChanged(position);

        });

    }

    TotalCheck mTotalCheck;

    public boolean isEnableEdit() {
        return mIsEnableEdit;
    }

    public void setIsEnableEdit(boolean IsEnableEdit) {
        this.mIsEnableEdit = IsEnableEdit;
        notifyDataSetChanged();
    }

    public void setTotalCheck(TotalCheck TotalCheck) {
        this.mTotalCheck = TotalCheck;
    }

    public void setAllCheck(boolean isAllCheck) {
        if (mSlideItemView != null) {
            mSlideItemView.closeDelItem();
        }

        for (int i = 0; i < mArrayList.size(); i++) {
            if (isAllCheck && mArrayList.get(i).getIsSelected() != isAllCheck) {
                mArrayList.get(i).setIsSelected(isAllCheck);
            } else if (mArrayList.get(i).getIsSelected()) {
                mArrayList.get(i).setIsSelected(isAllCheck);
            }
        }

        if (isAllCheck) {
            mCheckNumber = mArrayList.size();
        } else {
            mCheckNumber = 0;
        }
        mTotalCheck.total(mCheckNumber, mCheckNumber == mArrayList.size(), mArrayList.size());
        notifyDataSetChanged();
    }


    public void deleteData() {

        if (mSlideItemView != null) {
            mSlideItemView.closeDelItem();
        }
        Iterator<MsgCenterBean> iterator = mArrayList.iterator();
        while (iterator.hasNext()) {
            MsgCenterBean next = iterator.next();
            boolean check = next.getIsSelected();
            if (check) {
                iterator.remove();
                mCheckNumber--;
            }
        }
        mTotalCheck.total(mCheckNumber, mCheckNumber == mArrayList.size(), mArrayList.size());
        //todo，这里只需要删除某一个，更新的是后面的，而不是所有的，把notify系列的代码，都找一下
        notifyDataSetChanged();
    }

    public void addData(MsgCenterBean msgCenterBean) {
        Log.i("TAG", "addData: " + msgCenterBean.getTitle());
        if (mSlideItemView != null) {
            mSlideItemView.closeDelItem();
        }
        mArrayList.add(msgCenterBean);
        Collections.sort(mArrayList);
        notifyDataSetChanged();
    }

    public void deleteAllData() {
        Iterator<MsgCenterBean> iterator = mArrayList.iterator();
        while (iterator.hasNext()) {
            MsgCenterBean next = iterator.next();
            iterator.remove();
        }
        mCheckNumber = 0;
    }

    public interface TotalCheck {
        void total(int number, boolean isSelectAll, int arraySize);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgSelect;
        private final ImageView mImg;
        private final TextView mTvTitle;
        private final TextView mDataTime;

        private final TextView mTvContent;
        private final ImageView mImgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgSelect = itemView.findViewById(R.id.img_select);
            mImg = itemView.findViewById(R.id.image_item);
            mTvTitle = itemView.findViewById(R.id.tv_title_item);
            mTvContent = itemView.findViewById(R.id.tv_content_item);
            mDataTime = itemView.findViewById(R.id.tv_data_time);
            mImgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
