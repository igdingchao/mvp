package com.example.myapplication.base;

public interface IBasePresenter<V> {

    void onAttach(V v);

    void onDetach();
}
