package com.example.common.mvp;

public abstract class BasePresenter<V, M> implements IBasePresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();
    private V v;
    private M m;

    public BasePresenter() {
        MvpProxyImpl<V, BasePresenter> mvpProxy = new MvpProxyImpl<>(this);
        m = (M) mvpProxy.createM();
    }

    @Override
    public void onAttach(V v) {
        this.v = v;
    }

    @Override
    public void onDetach() {
        v = null;
    }

    public V getV() {
        return v;
    }

    public M getM() {
        return m;
    }
}
