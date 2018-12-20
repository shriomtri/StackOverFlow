package com.stackflow.app.service.net;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private DataService dataService;
    private static DataRepository dataRepository;

    private DataRepository() {
        dataService = RetrofitBuilder.getInstance().create(DataService.class);
    }

    public static DataRepository instance() {
        if (dataRepository == null) {
            dataRepository = new DataRepository();
        }
        return dataRepository;
    }

    public LiveData<ResponseList<Question>> trendingQuestions(Map<String,String> map) {

        MutableLiveData<ResponseList<Question>> data = new MutableLiveData<>();
        Call<ResponseList<Question>> call = dataService.trendingQuestion(map);
        call.enqueue(new Callback<ResponseList<Question>>() {
            @Override
            public void onResponse(Call<ResponseList<Question>> call, Response<ResponseList<Question>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseList<Question>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<ResponseList<User>> getUserInfo(Map<String,String> map) {

        MutableLiveData<ResponseList<User>> data = new MutableLiveData<>();
        Call<ResponseList<User>> call = dataService.getUserInfo(map);
        call.enqueue(new Callback<ResponseList<User>>() {
            @Override
            public void onResponse(Call<ResponseList<User>> call, Response<ResponseList<User>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseList<User>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<ResponseList<PopularTag>> getPopularTag(Map<String, String> map){

        MutableLiveData<ResponseList<PopularTag>> data = new MutableLiveData<>();
        Call<ResponseList<PopularTag>> call = dataService.getPopularTag(map);
        call.enqueue(new Callback<ResponseList<PopularTag>>() {
            @Override
            public void onResponse(Call<ResponseList<PopularTag>> call, Response<ResponseList<PopularTag>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseList<PopularTag>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
