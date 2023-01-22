package com.example.myapplication.presenter;

public interface IBasePresenter<V> {

    void onAttach(V v);

    void onDetach();
}
