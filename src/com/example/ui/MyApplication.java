package com.example.ui;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication instance;
    private static int i;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}