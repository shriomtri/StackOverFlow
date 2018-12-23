package com.stackflow.app.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stackflow.app.R;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.view.adapters.QuestionTitleAdapter;
import com.stackflow.app.view.adapters.TagAdapter;
import com.stackflow.app.viewmodel.HomeViewModel;
import com.stackflow.app.databinding.ActivityHomeBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity implements TagAdapter.TagClickListener {

    ActivityHomeBinding binding;

    private HomeViewModel viewModel;
    private QuestionTitleAdapter titleAdapter;
    private TagAdapter tagAdapter;
    private TextView interestTv;

    private String currentInterest = null;
    private String currentTag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        setupActionBar();
        setupHomeView();
        setupNavigation();


    }

    private void setupActionBar() {

        setSupportActionBar(binding.homeView.toolbar);
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

        viewModel.getUserInterest().observe(this, userInterests -> {

            currentInterest = userInterests.get(0).getUserInterest();
            currentTag = userInterests.get(0).getUserInterest();

            binding.contentNav.interestOne.setText(userInterests.get(0).getUserInterest());
            binding.contentNav.interestTwo.setText(userInterests.get(1).getUserInterest());
            binding.contentNav.interestThree.setText(userInterests.get(2).getUserInterest());
            binding.contentNav.interestFour.setText(userInterests.get(3).getUserInterest());

            getRelatedTags(currentInterest);
        });

        binding.contentNav.interestTagList.setLayoutManager(new LinearLayoutManager(this));
        tagAdapter = new TagAdapter(this);
        binding.contentNav.interestTagList.setAdapter(tagAdapter);

    }


    public void interestClicked(View view) {

        if(view instanceof TextView) {
            interestTv = (TextView) view;
            currentInterest = interestTv.getText().toString().toLowerCase();
            getRelatedTags(currentInterest);
        }
    }

    private void getRelatedTags(String interest) {

        Map<String, String> map = new HashMap<>();
        map.put(Constants.QueryParam.PAGE, "1");
        map.put(Constants.QueryParam.PAGE_SIZE, "10");
        map.put(Constants.QueryParam.ORDER, "desc");
        map.put(Constants.QueryParam.SORT, "popular");
        map.put(Constants.QueryParam.INNAME, interest);
        map.put(Constants.QueryParam.SITE, "stackoverflow");
        map.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.getPopularTag(map).observe(this, popularTagResponseList -> {
            List<PopularTag> popularTags = popularTagResponseList.getItems();
            tagAdapter.swapData(popularTags);
            getTrendingQuestions(popularTags.get(0).getName());
        });

    }

    private void getTrendingQuestions(String tag) {

        //TODO whenever user change the workspace, show shimmer effect instead progressDialog
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
        closeDrawers();
    }

    @Override
    public void tagClicked(String tag) {
        getTrendingQuestions(tag);
        closeDrawers();
    }
}
