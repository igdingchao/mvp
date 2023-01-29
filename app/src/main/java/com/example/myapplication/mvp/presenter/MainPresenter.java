package com.example.myapplication.mvp.presenter;

import com.example.myapplication.mvp.model.MainModel;
import com.example.myapplication.mvp.contact.IMainContact;
import com.example.common.mvp.BasePresenter;

public class MainPresenter extends BasePresenter<IMainContact.IMainView, MainModel> implements IMainContact.IMainPresenter {

    @Override
    public void changeModel(String toString) {
        String s = getM().handleModel(toString);
        getV().showData(s);
    }
}
