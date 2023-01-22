package com.example.myapplication;

import com.example.myapplication.contact.IMainContact;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.presenter.MainPresenter;

public class SecondActivity extends BaseActivity<MainPresenter> implements IMainContact.IMainView{
    @Override
    protected void initData() {
    }

    @Override
    protected int initView() {
        return R.layout.activity_second;
    }

    @Override
    public void showData(String s1) {

    }
}
