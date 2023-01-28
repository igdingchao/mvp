package com.example.myapplication.base;

public interface IMvpProxy {
    void bindPresenter();

    void unBindPresenter();

    Object createM();
}
