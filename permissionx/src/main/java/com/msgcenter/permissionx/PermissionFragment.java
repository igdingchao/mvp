package com.msgcenter.permissionx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

//权限适配，去打开不同的设置界面，百度上是可以搜到对应厂商的页面的，直接copy就行了，所以我这里，是不需要用到这个字段，去toast这个字段的
//因为一般的用户，看到了这个字段，也不知道怎么办。所以最好的办法就是去跳转页面了，之后有机会的话，再完善一下自己的这个权限库。
//百度搜索  Android  获取各个厂商的设置界面
public class PermissionFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    HashMap mPermissionMap = new HashMap<String, String>();

    private String mDenied = "";
    private PermissionCallBack mPermissionCallBack;

    public PermissionFragment() {
        mPermissionMap.put(Manifest.permission.CAMERA, "相机权限");
        mPermissionMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "读写权限");
        mPermissionMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储权限");
        mPermissionMap.put(Manifest.permission.CALL_PHONE, "电话权限");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestPermission(PermissionCallBack permissionCallBack, String[] permissions) {
        this.mPermissionCallBack = permissionCallBack;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //granted 允许，denied 拒绝
//        requestCode: Int,请求的响应码
//        permissions: Array<out String>,请求的所有权限
//        grantResults: IntArray 请求的响应码的数组，[0,-1,-1] 元素为0代表允许，为-1代表不允许
        if (requestCode == REQUEST_CODE) {
            if (mDenied.isEmpty()) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        mDenied += mPermissionMap.get(permissions[i]);
                    }
                }
            }
        }
        boolean allGrant = mDenied.isEmpty();//不包含权限字段，代表全部通过
        mPermissionCallBack.requestResult(allGrant, "请打开" + mDenied);
        mDenied = "";
    }
}
