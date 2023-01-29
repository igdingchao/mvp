package com.example.myapplication.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.base.BaseFragment;
import com.example.myapplication.R;
import com.example.myapplication.mvp.contact.IMainContact;
import com.example.myapplication.mvp.presenter.MainPresenter;
import com.msgcenter.permissionx.PermissionUtil;
import com.msgcenter.permissionx.PermissionX;

public class MainFragment extends BaseFragment<MainPresenter> implements View.OnClickListener, IMainContact.IMainView {

    private Button button;
    private Button mBtnPermission;

    //专门用于findViewById的方法
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.btn_content);
        mBtnPermission = view.findViewById(R.id.btn_permission);
        button.setOnClickListener(this);
        mBtnPermission.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int initView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_content:
                getP().changeModel(button.getText().toString());
                break;
            case R.id.btn_permission:
                PermissionX.request(getActivity(), new String[]{Manifest.permission.CAMERA}, denied -> {
                    Toast.makeText(getContext(), denied, Toast.LENGTH_SHORT).show();
                    PermissionUtil.enterWhiteListSetting(getContext());
                });
                break;
        }
    }

    @Override
    public void showData(String s1) {
        Toast.makeText(getActivity(), s1, Toast.LENGTH_SHORT).show();
    }
}
