package com.stackflow.stackoverflow.view.activities;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stackflow.stackoverflow.R;
import com.stackflow.stackoverflow.databinding.ActivityHomeBinding;
import com.stackflow.stackoverflow.service.model.Question;
import com.stackflow.stackoverflow.view.adapters.QuestionTitleAdapter;
import com.stackflow.stackoverflow.viewmodel.HomeViewModel;

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

    private void getTrendingQuestions() {

        showProgressDialog(this);

        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pagesize", "10");
        map.put("order", "desc");
        map.put("sort", "activity");
        map.put("tagged", "android");
        map.put("site", "stackoverflow");

        viewModel.trendingQuestions(map).observe(this, questionResponseList -> {

            cancelProgressDialog();

            if (questionResponseList != null && questionResponseList.getItems() != null) {

                List<Question> questionList = questionResponseList.getItems();
                titleAdapter.swapData(questionList);

            }
        });

    }
}
