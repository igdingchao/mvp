package com.example.myapplication.mvp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.base.BaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MsgCenterAdapter;
import com.example.myapplication.bean.MsgCenterBean;
import com.example.myapplication.mvp.contact.IMainContact;
import com.example.myapplication.mvp.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

//todo 购物车代码，模仿尚学堂的购物车，进行重构，主要是思考，他是怎么设计的，而不是直接拿了代码就写
public class SecondActivity extends BaseActivity<MainPresenter> implements IMainContact.IMainFView, MsgCenterAdapter.TotalCheck {

    public static final String TAG = SecondActivity.class.getSimpleName();
    TextView mTvSelectAll;
    TextView mTvDelete;
    TextView mTvEdit;
    TextView mTvBack;
    TextView mTvTest;
    TextView mTvNoData;
    ImageView mImgBack;
    boolean mIsCheck = false;
    String mTvSelect;
    RecyclerView mRecyclerView;
    MsgCenterAdapter mMsgCenterAdapter;
    private RelativeLayout mRlNoMsg;

    @Override
    protected void initData() {

        mTvSelectAll = findViewById(R.id.tv_select_all_or_not);
        mImgBack = findViewById(R.id.img_back);
        mTvBack = findViewById(R.id.tv_back);
        mTvTest = findViewById(R.id.tv_test);
        mTvEdit = findViewById(R.id.tv_edit);
        mTvDelete = findViewById(R.id.tv_delete);
        mRlNoMsg = findViewById(R.id.rl_no_msg);
        mRecyclerView = findViewById(R.id.rv_message);
        mTvNoData = findViewById(R.id.tv_no_data);
        List<MsgCenterBean> data = new ArrayList<>();

        mMsgCenterAdapter = new MsgCenterAdapter(this, data);

        mMsgCenterAdapter.setHasStableIds(false);
        mMsgCenterAdapter.setTotalCheck(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mMsgCenterAdapter);
        mTvSelect = mTvSelectAll.getText().toString();

        for (int i = 0; i < 10; i++) {
            MsgCenterBean msgCenterBean = new MsgCenterBean();
            msgCenterBean.setContent("内容" + i);
            msgCenterBean.setTitle("标题" + i);
            data.add(msgCenterBean);
            mMsgCenterAdapter.addData(msgCenterBean);
        }

        if (data.size() == 0) {
            noDataView();
        }

        mImgBack.setOnClickListener(v -> {
            if (mMsgCenterAdapter.mSlideItemView != null) {
                mMsgCenterAdapter.mSlideItemView.closeDelItem();
            }

            if (mMsgCenterAdapter.isEnableEdit()) {
                mTvEdit.setVisibility(View.VISIBLE);
                mTvSelectAll.setVisibility(View.GONE);
                mTvDelete.setVisibility(View.GONE);
                mTvBack.setText(getString(R.string.app_name));
                mMsgCenterAdapter.setAllCheck(false);
                mMsgCenterAdapter.setIsEnableEdit(false);
                return;
            }
            finish();
        });

        mTvSelectAll.setOnClickListener(v -> {

            if (mTvSelect.equals(getString(R.string.tv_select_all))) {
                mTvSelectAll.setText(getString(R.string.tv_cancel_select_all));
                mIsCheck = true;
            } else if (mTvSelect.equals(getString(R.string.tv_cancel_select_all))) {
                mTvSelectAll.setText(getString(R.string.tv_select_all));
                mIsCheck = false;

            }
            mMsgCenterAdapter.setAllCheck(mIsCheck);
        });

        mTvDelete.setOnClickListener(v -> showDeleteDialog());

        mTvEdit.setOnClickListener(view -> {
            Log.i(TAG, "onClick: ");
            mTvEdit.setVisibility(View.GONE);
            mTvSelectAll.setVisibility(View.VISIBLE);
            mTvDelete.setVisibility(View.VISIBLE);
            mTvBack.setText(getString(R.string.tv_cancel_edit));
            mMsgCenterAdapter.setIsEnableEdit(true);
        });

        mTvTest.setOnClickListener(v -> {
            showMsgView();
            //         测试消息列表的代码
            for (int i = 0; i < 10; i++) {
                MsgCenterBean msgCenterBean = new MsgCenterBean();
                msgCenterBean.setContent("内容" + i);
                msgCenterBean.setTitle("标题" + i);
                mMsgCenterAdapter.addData(msgCenterBean);
            }
        });


    }

    private void showMsgView() {
        //1.无数据时，来了一个消息
        if (mTvEdit.getVisibility() == View.GONE && mTvSelectAll.getVisibility() == View.GONE && mTvDelete.getVisibility() == View.GONE) {
            mTvEdit.setVisibility(View.VISIBLE);
        } else if (mTvEdit.getVisibility() == View.GONE && mTvSelectAll.getVisibility() == View.VISIBLE && mTvDelete.getVisibility() == View.VISIBLE
                && mTvSelectAll.getText().toString().equals(getString(R.string.tv_cancel_select_all))) {
            //4。有数据，且在全选的状态下，来了消息
            mTvSelectAll.setText(getString(R.string.tv_select_all));
            mTvSelect = mTvSelectAll.getText().toString();
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        mRlNoMsg.setVisibility(View.GONE);
    }

    @Override
    public void total(int number, boolean isSelectAll, int arraySize) {
        if (isSelectAll && mTvSelect.equals(getString(R.string.tv_select_all))) {
            mTvSelectAll.setText(getString(R.string.tv_cancel_select_all));
            mTvDelete.setTextColor(getResources().getColor(R.color.white));
            mTvDelete.setText(String.format(getResources().getString(R.string.tv_delete_with_count), String.valueOf(number)));
            mTvDelete.setEnabled(true);
        } else if (!isSelectAll && mTvSelect.equals(getString(R.string.tv_cancel_select_all)) && number == 0) {
            mTvSelectAll.setText(getString(R.string.tv_select_all));
            mTvDelete.setTextColor(getResources().getColor(R.color.tv_delete_nothing));
            mTvDelete.setText(getResources().getString(R.string.tv_delete));
            mTvDelete.setEnabled(false);
        } else if (!isSelectAll && mTvSelect.equals(getString(R.string.tv_select_all)) && number == 0) {
            mTvDelete.setTextColor(getResources().getColor(R.color.tv_delete_nothing));
            mTvDelete.setText(getResources().getString(R.string.tv_delete));
            mTvDelete.setEnabled(false);
        } else {
            mTvSelectAll.setText(getString(R.string.tv_select_all));
            mTvDelete.setTextColor(getResources().getColor(R.color.white));
            mTvDelete.setText(String.format(getResources().getString(R.string.tv_delete_with_count), String.valueOf(number)));
            mTvDelete.setEnabled(true);
        }
        mTvSelect = mTvSelectAll.getText().toString();
        if (arraySize == 0) {
            noDataView();
        }
    }

    /**
     * 是否删除所有内容
     */
    private void showDeleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("消息")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mMsgCenterAdapter.deleteData();
//                        alertDialog.dismiss();
                    }
                }).show();
    }

    private void noDataView() {
        mRecyclerView.setVisibility(View.GONE);
        mTvEdit.setVisibility(View.GONE);
        mTvSelectAll.setVisibility(View.GONE);
        mTvSelectAll.setText(getString(R.string.tv_select_all));
        mTvSelect = mTvSelectAll.getText().toString();
        mTvDelete.setVisibility(View.GONE);
        mTvDelete.setText(getString(R.string.tv_delete));
        mTvDelete.setEnabled(false);
        mTvDelete.setTextColor(getResources().getColor(R.color.tv_delete_nothing));

        mRlNoMsg.setVisibility(View.VISIBLE);
        mTvBack.setText(getString(R.string.app_name));
        if (mMsgCenterAdapter != null) {
            mMsgCenterAdapter.setIsEnableEdit(false);
        }
    }

    @Override
    protected int initView() {
        return R.layout.activity_second;
    }
}
