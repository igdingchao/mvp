package com.msgcenter.permissionx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

//todo 还需要完善的是，用户禁止后，跳转到设置界面(工作量比较大，非必须，则不做)
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
        //不为空，就代表有的权限没给
        if (!mDenied.isEmpty()) {
            mPermissionCallBack.requestResult(mDenied + "已被禁用，请打开相应权限");
        }
        mDenied = "";
    }
}
