package com.example.myapplication.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();
    MvpProxyImpl<BaseFragment, P> vMvpProxy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(initView(), container, false);
        Log.i(TAG, "onCreateView: " + this);
        //todo  需提升的部分就是灵活的使用mvp三者之间的复用性，m可以用多个v，v可以用多个p
        vMvpProxy = new MvpProxyImpl<>(this);
        vMvpProxy.bindPresenter();
        return inflate;
    }

    protected abstract int initView();

    public P getP() {
        return vMvpProxy.getP();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vMvpProxy.unBindPresenter();
    }

}
