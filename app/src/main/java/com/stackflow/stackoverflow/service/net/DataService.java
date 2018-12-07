package com.stackflow.stackoverflow.service.net;

import com.stackflow.stackoverflow.service.model.Question;
import com.stackflow.stackoverflow.service.model.ResponseList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DataService {
    @GET("questions")
    Call<ResponseList<Question>> trendingQuestion(@QueryMap Map<String,String> options);

}
