package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.contact.MvpProxyImpl;
import com.example.myapplication.presenter.IBasePresenter;

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private MvpProxyImpl<BaseActivity, P> vMvpProxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
        Log.i(TAG, "onCreate: " + this);
        vMvpProxy = new MvpProxyImpl(this);
        vMvpProxy.bindPresenter();
        initData();
    }

    protected abstract void initData();

    protected abstract int initView();

    public P getP() {
        return vMvpProxy.getP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vMvpProxy.unBindPresenter();
    }
}
