package com.stackflow.app;

import android.app.Application;

import com.stackflow.app.service.database.DatabaseCreator;
import com.stackflow.app.service.net.DataRepository;
import com.stackflow.app.util.SharedPrefUtil;

public class StackOverFlow extends Application {

    private static StackOverFlow app;

    public static StackOverFlow instance(){ return app;}

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefUtil.set(this);
        SharedPrefUtil.instance().set(SharedPrefUtil.ACCESS_KEY,BuildConfig.KEY);
        SharedPrefUtil.instance().set(SharedPrefUtil.CLIENT_ID,BuildConfig.CLIENT_ID);
        DataRepository.instance();
        DatabaseCreator.getInstance().createDb(this);

    }
}
