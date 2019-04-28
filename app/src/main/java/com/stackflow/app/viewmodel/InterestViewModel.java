package com.stackflow.app.viewmodel;

import com.stackflow.app.service.database.LocalDataRepository;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.model.SelectedInterest;
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

    private MutableLiveData<SelectedInterest> currentSelected = new MutableLiveData<>();
    private MutableLiveData<SelectedInterest> currentRemoved = new MutableLiveData<>();
    private List<SelectedInterest> selectedInterestList = new ArrayList<>();

    private int count = 0;

    public InterestViewModel() {
        dataRepository = DataRepository.instance();
        localDataRepository = LocalDataRepository.instance();
    }

    public LiveData<ResponseList<PopularTag>> getPopularTag(Map<String, String> map) {
        MutableLiveData<ResponseList<PopularTag>> data = new MutableLiveData<>();
        dataRepository.getPopularTag(map).observeForever(data::setValue);
        return data;
    }

    public void setUserInterest() {

        List<UserInterest> userInterestList = new ArrayList<>(4);
        for (int i = 0, size = selectedInterestList.size(); i < size; i++) {
            userInterestList.add(new UserInterest(selectedInterestList.get(i).getTag().toLowerCase(), ""));
        }

        localDataRepository.setUserInterests(userInterestList);
    }

    public boolean setSelected(String tag, Integer position) {

        for(SelectedInterest selectedInterest : selectedInterestList){
            if(selectedInterest.getTag().equals(tag)){
                return false;
            }
        }

        if (count != 4) {
            count++;

            SelectedInterest selectedInterest = new SelectedInterest(tag, position);
            selectedInterestList.add(selectedInterest);
            currentSelected.setValue(selectedInterest);

            return true;
        } else { return false; }
    }

    public LiveData<SelectedInterest> currentSelectedInterest(){
        return currentSelected;
    }

    public List<SelectedInterest> getSelectedInterest(){
        return selectedInterestList;
    }

    public void removeSelected(SelectedInterest removedInterest, int position) {
        count--;
        selectedInterestList.remove(position);
        currentRemoved.setValue(removedInterest);
    }

    public LiveData<SelectedInterest> currentRemovedInterest(){return currentRemoved;}

    public int getCount(){
        return count;
    }

    public void clearAllSelection() {
        if(selectedInterestList.size()>0){
            for(SelectedInterest selectedInterest: selectedInterestList){
                removeSelected(selectedInterest, selectedInterest.getPosition());
            }
        }
    }
}
