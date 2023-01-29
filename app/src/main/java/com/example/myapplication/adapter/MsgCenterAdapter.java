package com.example.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.common.base.BaseAdapter;
import com.example.myapplication.bean.MsgCenterBean;
import com.example.myapplication.view.SlideItemView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MsgCenterAdapter extends BaseAdapter<MsgCenterBean> {
    int mCheckNumber = 0;
    private boolean mIsEnableEdit;
    public SlideItemView mSlideItemView;

    private ImageView mImgSelect;
    private ImageView mImg;
    private TextView mTvTitle;
    private TextView mDataTime;
    private TextView mTvContent;
    private ImageView mImgDelete;

    public MsgCenterAdapter(Context context, List<MsgCenterBean> arrayList) {
        super(context, arrayList);
    }

    @Override
    protected int initView() {
        return R.layout.item_msg_center;
    }

    @Override
    protected void bindHolder(BaseHolder holder, MsgCenterBean msgCenterBean, int position) {
        mImgSelect = holder.findViewById(R.id.img_select);
        mImg = holder.findViewById(R.id.image_item);
        mTvTitle = holder.findViewById(R.id.tv_title_item);
        mTvContent = holder.findViewById(R.id.tv_content_item);
        mDataTime = holder.findViewById(R.id.tv_data_time);
        mImgDelete = holder.findViewById(R.id.img_delete);

        mImgSelect.setTag(position);
        mImgSelect.setVisibility(isEnableEdit() ? View.VISIBLE : View.GONE);
        mImgSelect.setSelected(msgCenterBean.getIsSelected());

        mTvContent.setText(msgCenterBean.getContent());
        mImg.setImageResource(msgCenterBean.getIcon());
        mTvTitle.setText(msgCenterBean.getTitle());
        mDataTime.setText("" + msgCenterBean.getTime());

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

        mImgDelete.setOnClickListener(v -> {

            if (mCheckNumber > 0) {
                if (msgCenterBean.getIsSelected()) {
                    mCheckNumber--;
                }
            }
            notifyItemRemoved(position);
            datas.remove(position);
            notifyItemRangeChanged(position, getItemCount() - position);
            if (mSlideItemView != null) {
                mSlideItemView.closeDelItem();
            }
            mTotalCheck.total(mCheckNumber, mCheckNumber == datas.size(), datas.size());
        });

        mImgSelect.setOnClickListener(view -> {
            if (!datas.get(position).getIsSelected()) {
                datas.get(position).setIsSelected(true);
                mCheckNumber++;
            } else {
                datas.get(position).setIsSelected(false);
                mCheckNumber--;
            }
            mTotalCheck.total(mCheckNumber, mCheckNumber == datas.size(), datas.size());
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

        for (int i = 0; i < datas.size(); i++) {
            if (isAllCheck && datas.get(i).getIsSelected() != isAllCheck) {
                datas.get(i).setIsSelected(isAllCheck);
            } else if (datas.get(i).getIsSelected()) {
                datas.get(i).setIsSelected(isAllCheck);
            }
        }

        if (isAllCheck) {
            mCheckNumber = datas.size();
        } else {
            mCheckNumber = 0;
        }
        mTotalCheck.total(mCheckNumber, mCheckNumber == datas.size(), datas.size());
        notifyDataSetChanged();
    }

    public void deleteData() {

        if (mSlideItemView != null) {
            mSlideItemView.closeDelItem();
        }
        Iterator<MsgCenterBean> iterator = datas.iterator();
        while (iterator.hasNext()) {
            MsgCenterBean next = iterator.next();
            boolean check = next.getIsSelected();
            if (check) {
                iterator.remove();
                mCheckNumber--;
            }
        }
        mTotalCheck.total(mCheckNumber, mCheckNumber == datas.size(), datas.size());
        //todo，这里只需要删除某一个，更新的是后面的，而不是所有的，把notify系列的代码，都找一下
        notifyDataSetChanged();
    }


    public void addData(MsgCenterBean msgCenterBean) {
        Log.i("TAG", "addData: " + msgCenterBean.getTitle());
        if (mSlideItemView != null) {
            mSlideItemView.closeDelItem();
        }
        datas.add(msgCenterBean);
        Collections.sort(datas);
        notifyDataSetChanged();
    }


    public void deleteAllData() {
        Iterator<MsgCenterBean> iterator = datas.iterator();
        while (iterator.hasNext()) {
            MsgCenterBean next = iterator.next();
            iterator.remove();
        }
        mCheckNumber = 0;
    }


    public interface TotalCheck {
        void total(int number, boolean isSelectAll, int arraySize);
    }

}
