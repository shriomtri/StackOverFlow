package com.stackflow.app.service.database;

import android.os.Handler;
import android.os.HandlerThread;

import com.stackflow.app.service.model.UserInterest;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocalDataRepository {

    private static LocalDataRepository localDataRepository;
    private AppDatabase appDatabase;

    private LocalDataRepository(){
        appDatabase = DatabaseCreator.getInstance().getDatabase();
    }

    public static LocalDataRepository instance(){

        if(localDataRepository == null){
            localDataRepository = new LocalDataRepository();
        }

        return localDataRepository;
    }

    public void insertUserInterests(List<UserInterest> userInterests){

        final HandlerThread mHandlerThread = new HandlerThread("Handler");
        mHandlerThread.start();
        final Handler handler = new Handler(mHandlerThread.getLooper());
        final Runnable runnable = () -> {
            appDatabase.getUserInterestDao().insertUserInterests(userInterests);
        };
        handler.post(runnable);

    }

    public LiveData<List<UserInterest>> getUserInterest(){
        return appDatabase.getUserInterestDao().getUserInterest();
    }

}
