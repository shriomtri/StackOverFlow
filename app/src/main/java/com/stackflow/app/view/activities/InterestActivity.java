package com.stackflow.app.view.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.stackflow.app.R;
import com.stackflow.app.service.model.Question;
import com.stackflow.app.service.model.SelectedInterest;
import com.stackflow.app.util.AutofitRecyclerView;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.view.adapters.InterestAdapter;
import com.stackflow.app.view.adapters.QuestionTitleAdapter;
import com.stackflow.app.view.adapters.SelectedInterestAdapter;
import com.stackflow.app.viewmodel.InterestViewModel;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;


import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InterestActivity extends BaseActivity implements InterestAdapter.InterestClickListener, SelectedInterestAdapter.RemoveClickListener {

    private InterestViewModel viewModel = null;
    private RecyclerView seletedInterestList;
    private AutofitRecyclerView interestList;

    private InterestAdapter interestAdapter = null;
    private SelectedInterestAdapter selectedAdapter = null;

    private int interestCount = 0;
    private Typeface custom_font;

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        viewModel = ViewModelProviders.of(this).get(InterestViewModel.class);
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/JosefinSansSemiBold.ttf");
        setUpList();
        textView = findViewById(R.id.interest_gudieTV);
        textView.setTypeface(custom_font);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPopularInterest();
        //clearAllSelection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //set observer for adding and removal of user interest

        viewModel.currentSelectedInterest().observe(this, selectedInterest -> {
            interestAdapter.remove(selectedInterest);
            selectedAdapter.swapData(viewModel.getSelectedInterest());
        });

        viewModel.currentRemovedInterest().observe(this, removedInterest -> {
            interestAdapter.insert(removedInterest);
            selectedAdapter.swapData(viewModel.getSelectedInterest());
        });
    }

    private void setUpList() {

        interestAdapter = new InterestAdapter(this);
        interestList = findViewById(R.id.interest_list);
        interestList.setAdapter(interestAdapter);

        seletedInterestList = findViewById(R.id.selected_interest_list);
        seletedInterestList.setLayoutManager(new GridLayoutManager(this, 3));
        selectedAdapter = new SelectedInterestAdapter(this);
        seletedInterestList.setAdapter(selectedAdapter);

    }

    private void getPopularInterest() {
        showProgressDialog(this);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(Constants.QueryParam.PAGE, "1");
        queryMap.put(Constants.QueryParam.PAGE_SIZE, "30");
        queryMap.put(Constants.QueryParam.ORDER, "desc");
        queryMap.put(Constants.QueryParam.SORT, "popular");
        queryMap.put(Constants.QueryParam.SITE, "stackoverflow");
        queryMap.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        viewModel.getPopularTag(queryMap).observe(this, popularTagResponseList -> {
            cancelProgressDialog();
            if (popularTagResponseList != null && popularTagResponseList.getItems().size() > 0) {
                interestAdapter.swapData(popularTagResponseList.getItems());
            } else {
                //TODO handle the error
            }
        });
    }

    private void clearAllSelection() {
        viewModel.clearAllSelection();
    }

    public void submit(View view) {
        if (viewModel.getCount() < 4) {
            showToastMessage("Select at least 4 interest");
        } else {
            viewModel.setUserInterest();
            showProgressDialog(this);
            new Handler().postDelayed(() -> {
                startActivity(new Intent(InterestActivity.this, HomeActivity.class));
                cancelProgressDialog();
                finish();
            }, 3000);
        }
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

    @Override
    public void tagSelected(String tag, Integer position) {
        boolean isAdded = viewModel.setSelected(tag, position);
        if (!isAdded) {
            showToastMessage("Only 4 selection allowed");
        }
    }

    @Override
    public void tagRemoved(SelectedInterest removedInterest, int position) {
        viewModel.removeSelected(removedInterest, position);
    }
}
