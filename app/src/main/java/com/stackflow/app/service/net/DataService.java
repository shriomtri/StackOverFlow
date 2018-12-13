package com.stackflow.app.service.net;

import com.stackflow.app.service.model.Question;
import com.stackflow.app.service.model.ResponseList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface DataService {
    @GET("questions")
    Call<ResponseList<Question>> trendingQuestion(@QueryMap Map<String,String> options);

}
