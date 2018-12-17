package com.stackflow.app.viewmodel;

import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.net.DataRepository;

import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterestViewModel extends ViewModel {

    private DataRepository dataRepository;

    public InterestViewModel(){ dataRepository = DataRepository.instance(); }

    public LiveData<ResponseList<PopularTag>> getPopularTag(Map<String,String> map) {
        MutableLiveData<ResponseList<PopularTag>> data = new MutableLiveData<>();
        dataRepository.getPopularTag(map).observeForever(data::setValue);
        return data;
    }

}
