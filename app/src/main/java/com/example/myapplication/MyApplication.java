package com.example.myapplication;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //该方法相当于ondestory，只在模拟器里面回调，真机不会回调
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
