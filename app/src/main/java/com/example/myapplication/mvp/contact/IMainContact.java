package com.example.myapplication.mvp.contact;

public interface IMainContact {

     interface IMainPresenter {
        void changeModel(String toString);
    }

     interface IMainView {
        void showData(String s1);
    }

     interface IMainModel{
        String handleModel(String toString);
    }
}