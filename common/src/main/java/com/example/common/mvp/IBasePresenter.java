package com.example.common.mvp;

public interface IBasePresenter<V> {

    void onAttach(V v);

    void onDetach();
}
