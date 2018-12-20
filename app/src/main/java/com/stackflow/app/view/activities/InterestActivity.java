package com.stackflow.app.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.stackflow.app.R;
import com.stackflow.app.databinding.ActivityInterestBinding;
import com.stackflow.app.service.model.PopularTag;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.view.adapters.InterestAdapter;
import com.stackflow.app.viewmodel.InterestViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class InterestActivity extends BaseActivity implements InterestAdapter.InterestClickListener{

    private ActivityInterestBinding binding = null;
    private InterestViewModel viewModel = null;
    private InterestAdapter interestAdapter = null;
    private List<PopularTag> popularTags = new ArrayList<>();

    private int interestCount  = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_interest);
        viewModel = ViewModelProviders.of(this).get(InterestViewModel.class);
        binding.submitWrapper.setOnClickListener(v -> { submit();});
        setUpList();
        getPopularTags();
    }

    private void setUpList() {
        interestAdapter = new InterestAdapter(this, popularTags);
        binding.interestList.setAdapter(interestAdapter);
        binding.interestList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getPopularTags() {
        showProgressDialog(this);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(Constants.QueryParam.PAGE, "1");
        queryMap.put(Constants.QueryParam.PAGE_SIZE, "20");
        queryMap.put(Constants.QueryParam.ORDER, "desc");
        queryMap.put(Constants.QueryParam.SORT, "popular");
        queryMap.put(Constants.QueryParam.SITE, "stackoverflow");
        queryMap.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.getPopularTag(queryMap).observe(this, popularTagResponseList -> {
            cancelProgressDialog();
            if(popularTagResponseList != null && popularTagResponseList.getItems().size() > 0){
                interestAdapter.swapData(popularTagResponseList.getItems());
            }else {
                //TODO handle the error
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

        if(interestCount < 4){
            showToastMessage("Select atleast 4 interest");
        }else {
            showToastMessage("Creating workspace");
            submit();
        }

    }

    public void submit() {
        //Do submit work here
        if(interestCount == 4){
            List<String> interestList = interestAdapter.selected();
            SharedPrefUtil.instance().setInterest(SharedPrefUtil.USER_INTEREST,interestList);

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void tagSelected(boolean isChecked) {

        if(isChecked){
            interestCount++;
        }else{
            interestCount--;
        }

        if(interestCount == 4){
            binding.submitWrapper.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }else{
            binding.submitWrapper.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }

    }
}
