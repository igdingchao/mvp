package com.example.common.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.common.mvp.IBasePresenter;
import com.example.common.mvp.MvpProxyImpl;

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
