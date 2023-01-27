package com.example.myapplication.utils;

import android.content.Context;
import android.util.Log;

public class DensityUtil {
    public static final String TAG = DensityUtil.class.getSimpleName();

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.i(TAG, "屏幕密度: " + scale);
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
