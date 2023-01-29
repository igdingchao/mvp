package com.example.common.mvp;

public interface IMvpProxy {
    void bindPresenter();

    void unBindPresenter();

    Object createM();
}
