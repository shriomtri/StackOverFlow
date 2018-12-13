package com.stackflow.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.stackflow.app.service.model.Question;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.net.DataRepository;

import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final DataRepository dataRepository;

    public HomeViewModel() {
        dataRepository = DataRepository.instance();
    }

    public LiveData<ResponseList<Question>> trendingQuestions(Map<String,String> map) {
        MutableLiveData<ResponseList<Question>> data = new MutableLiveData<>();
        dataRepository.trendingQuestions(map).observeForever(data::setValue);
        return data;
    }


}
