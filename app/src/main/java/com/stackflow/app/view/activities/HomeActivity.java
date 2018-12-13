package com.stackflow.app.view.activities;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stackflow.app.R;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.view.adapters.QuestionTitleAdapter;
import com.stackflow.app.viewmodel.HomeViewModel;
import com.stackflow.app.databinding.ActivityHomeBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private QuestionTitleAdapter titleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        binding.titleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        titleAdapter = new QuestionTitleAdapter(this);
        binding.titleRecyclerView.setAdapter(titleAdapter);

        getTrendingQuestions();

    }

    private void getTrendingQuestions() {

        showProgressDialog(this);

        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, "1");
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "activity");
        map.put(Constants.QueryParam.TAGGED, "android");
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY,SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.trendingQuestions(map).observe(this, questionResponseList -> {

            cancelProgressDialog();

            if (questionResponseList != null && questionResponseList.getItems() != null) {
                List<Question> questionList = questionResponseList.getItems();
                titleAdapter.swapData(questionList);
            }
        });
    }


    @Override
    public boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    public boolean isToolbarRequired() {
        return false;
    }

    @Override
    public void onBackPressed() {

    }

}
