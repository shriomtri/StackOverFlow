package com.stackflow.stackoverflow;

import android.app.Application;

import com.stackflow.stackoverflow.service.net.DataRepository;
import com.stackflow.stackoverflow.util.SharedPrefUtil;

public class StackOverFlow extends Application {

    private static StackOverFlow app;

    public static StackOverFlow instance(){ return app;}

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefUtil.set(this);
        SharedPrefUtil.instance().set(SharedPrefUtil.ACCESS_KEY,BuildConfig.KEY);
        DataRepository.instance();

    }
}
