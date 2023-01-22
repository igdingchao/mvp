package com.example.myapplication.view;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.R;
import com.example.myapplication.SecondActivity;
import com.example.myapplication.contact.IMainContact;
import com.example.myapplication.fragment.MainFragment;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.presenter.MainPresenter;

//todo 加入一个banner和recycleview的网络请求进来试试
public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener, IMainContact.IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button button;

    @Override
    protected void initData() {
        button = findViewById(R.id.btn_content);
        button.setOnClickListener(this);

        MainFragment mainFragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, mainFragment);
        ft.commit();
    }

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {
        getP().changeModel(button.getText().toString());
    }

    @Override
    public void showData(String s1) {
        Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}