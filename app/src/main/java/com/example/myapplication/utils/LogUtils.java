package com.example.myapplication.utils;

import android.util.Log;

public class LogUtils {

    public static final String TAG = LogUtils.class.getSimpleName();

    public static boolean isOpen = false;

    public static void i(String content) {
        if (isOpen) {
            Log.i(TAG, content);
        }
    }

    public static void d(String content) {
        if (isOpen) {
            Log.d(TAG, content);
        }
    }

    public static void e(String content) {
        if (isOpen) {
            Log.e(TAG, content);
        }
    }


}
