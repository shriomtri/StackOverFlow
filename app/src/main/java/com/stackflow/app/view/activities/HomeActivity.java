package com.stackflow.app.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

        setupActionBar();
        setupHomeView();
        setupNavigation();

        getTrendingQuestions("android");

    }

    private void setupActionBar() {

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }

    private void setupHomeView() {
        binding.homeView.titleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        titleAdapter = new QuestionTitleAdapter(this);
        binding.homeView.titleRecyclerView.setAdapter(titleAdapter);

    }

    private void setupNavigation() {

    }

    private void getTrendingQuestions(String tag) {

        showProgressDialog(this);

        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, "1");
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "activity");
        map.put(Constants.QueryParam.TAGGED, tag);
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.trendingQuestions(map).observe(this, questionResponseList -> {

            cancelProgressDialog();

            if (questionResponseList != null && questionResponseList.getItems() != null) {
                List<Question> questionList = questionResponseList.getItems();
                titleAdapter.swapData(questionList);
            }
        });

        //testing purpose
        viewModel.getUserInterest().observe(this, interests -> {
            Log.d("mark1", interests.get(0).getUserInterest() + " " + interests.get(1).getUserInterest());
            Log.d("mark1", "Size " + String.valueOf(interests.size()));
        });

    }


    public void interestClicked(View view) {
        switch (view.getId()) {
            case R.id.interest_one:
                closeDrawers();
                break;
            case R.id.interest_two:
                closeDrawers();
                break;
            case R.id.interest_three:
                closeDrawers();
                break;
            case R.id.interest_four:
                closeDrawers();
                break;

        }
    }

    private void closeDrawers() {
        if(binding.drawerLayout.isDrawerOpen(binding.navView)){
            binding.drawerLayout.closeDrawers();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    public boolean isToolbarRequired() {
        return false;
    }

    @Override
    public void onBackPressed() {

    }
}
