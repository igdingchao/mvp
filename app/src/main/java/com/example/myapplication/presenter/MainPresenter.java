package com.example.myapplication.presenter;

import com.example.myapplication.view.MainActivity;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.contact.IMainContact;

public class MainPresenter extends BasePresenter<IMainContact.IMainView, MainModel> implements IMainContact.IMainPresenter {

    @Override
    public void changeModel(String toString) {
        String s = getM().handleModel(toString);
        getV().showData(s);
    }
}
