package com.stackflow.stackoverflow.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.annotation.Nullable;

import com.stackflow.stackoverflow.service.model.Question;
import com.stackflow.stackoverflow.service.model.ResponseList;
import com.stackflow.stackoverflow.service.net.DataRepository;

import java.util.Map;

public class HomeViewModel extends ViewModel {

    private DataRepository dataRepository;

    public HomeViewModel() {
        dataRepository = DataRepository.instance();
    }

    public LiveData<ResponseList<Question>> trendingQuestions(Map<String,String> map) {
        MutableLiveData<ResponseList<Question>> data = new MutableLiveData<>();
        dataRepository.trendingQuestions(map).observeForever(data::setValue);
        return data;
    }


}
