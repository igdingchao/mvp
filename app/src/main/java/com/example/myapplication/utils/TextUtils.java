package com.example.myapplication.utils;

import android.widget.TextView;

public class TextUtils {

    public static String getText(TextView view) {
        String s = view.getText().toString();
        return s;
    }
}
