package com.example.myapplication.mvp.model;

import com.example.myapplication.mvp.contact.IMainContact;

public class MainModel implements IMainContact.IMainModel {

    @Override
    public String handleModel(String toString) {
        return toString;
    }

}
