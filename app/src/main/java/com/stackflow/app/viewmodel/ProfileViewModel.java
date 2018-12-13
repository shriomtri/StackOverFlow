package com.stackflow.app.viewmodel;

import com.stackflow.app.service.model.Question;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.model.User;
import com.stackflow.app.service.net.DataRepository;

import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {


    private final DataRepository dataRepository;

    public ProfileViewModel() {
        this.dataRepository = DataRepository.instance();
    }


    public LiveData<ResponseList<User>> getUserInfo(Map<String,String> map) {
        MutableLiveData<ResponseList<User>> data = new MutableLiveData<>();
        dataRepository.getUserInfo(map).observeForever(data::setValue);
        return data;
    }

}
