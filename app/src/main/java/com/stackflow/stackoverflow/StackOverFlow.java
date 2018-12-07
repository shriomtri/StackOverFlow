package com.stackflow.stackoverflow;

import android.app.Application;

import com.stackflow.stackoverflow.service.net.DataRepository;

public class StackOverFlow extends Application {

    private static StackOverFlow app;

    public static StackOverFlow instance(){ return app;}

    @Override
    public void onCreate() {
        super.onCreate();

        DataRepository.instance();

    }
}
