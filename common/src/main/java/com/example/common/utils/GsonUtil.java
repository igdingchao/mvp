package com.example.common.utils;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

public class GsonUtil {

    private static WeakReference<Gson> weakReference;
    private static Gson gson;

    private GsonUtil() {

    }

    //不需要每次都去new一个gson，所以通过单例来获取就行了，
    //只需要new一个弱引用去包裹就行了，相当于gson永久保存在了弱引用里面，然后就直接拿来用
    public static Gson get() {
        if (weakReference == null) {
            Gson gson = new Gson();
            weakReference = new WeakReference<>(gson);
            //todo 这里为什么使用弱引用呢？我对弱引用的理解都是在背诵，但是没有实际的去应用和理解
            //不用弱引用也可以，但是考虑到性能，如果没做网络请求，那么长期持有gson是耗性能的。
        }
        return weakReference.get();
    }
}
