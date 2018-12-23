package com.stackflow.app.viewmodel;

import com.stackflow.app.service.database.LocalDataRepository;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.model.UserInterest;
import com.stackflow.app.service.net.DataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterestViewModel extends ViewModel {

    private DataRepository dataRepository;
    private LocalDataRepository localDataRepository;

    public InterestViewModel() {
        dataRepository = DataRepository.instance();
        localDataRepository = LocalDataRepository.instance();
    }

    public LiveData<ResponseList<PopularTag>> getPopularTag(Map<String, String> map) {
        MutableLiveData<ResponseList<PopularTag>> data = new MutableLiveData<>();
        dataRepository.getPopularTag(map).observeForever(data::setValue);
        return data;
    }

    public void setUserInterest(List<String> interest) {

        List<UserInterest> userInterestList = new ArrayList<>(4);
        for (int i = 0, size = interest.size(); i < size; i++) {
            userInterestList.add(new UserInterest(interest.get(i), ""));
        }

        localDataRepository.setUserInterests(userInterestList);
    }

}
