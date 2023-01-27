package com.msgcenter.permissionx;

import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class PermissionX {
    private static final String TAG = "PermissionFragment";

    public static void request(FragmentActivity activity, String[] permissions, PermissionCallBack permissionCallBack) {
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(TAG);
        PermissionFragment fragment;
        if (fragmentByTag != null) {
            fragment = (PermissionFragment) fragmentByTag;
        } else {
            PermissionFragment permissionFragment = new PermissionFragment();
            beginTransaction.add(permissionFragment, TAG).commitNow();
            //如果是commit，是会被搁置的，当你活动中接下来的所有初始化代码执行完以后，才会去真正add
//如果是commitNow，先把碎片那一系列的生命周期操作执行了，让你的碎片真正被“激活”了，才会按顺序执行你activity中的余下代码。
            fragment = permissionFragment;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.requestPermission(permissionCallBack, permissions);
        }

    }
}
