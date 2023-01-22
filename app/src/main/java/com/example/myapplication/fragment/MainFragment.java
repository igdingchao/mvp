package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.contact.IMainContact;
import com.example.myapplication.presenter.MainPresenter;

public class MainFragment extends BaseFragment<MainPresenter> implements View.OnClickListener, IMainContact.IMainView {

    private Button button;

    //专门用于findViewById的方法
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=view.findViewById(R.id.btn_content);
        button.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int initView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View v) {
        getP().changeModel(button.getText().toString());
    }

    @Override
    public void showData(String s1) {
        Toast.makeText(getActivity(), s1, Toast.LENGTH_SHORT).show();
    }
}
