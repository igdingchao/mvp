package com.example.myapplication.model;

import com.example.myapplication.contact.IMainContact;

public class MainModel implements IMainContact.IMainModel {

    @Override
    public String handleModel(String toString) {
        return toString;
    }

}
