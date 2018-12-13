package com.stackflow.app.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stackflow.app.R;
import com.stackflow.app.databinding.ActivityProfileBinding;
import com.stackflow.app.service.model.ResponseList;
import com.stackflow.app.service.model.User;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;
import com.stackflow.app.viewmodel.ProfileViewModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;
    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        if(SharedPrefUtil.instance().getSerializable(SharedPrefUtil.USER_PROFILE,User.class) != null){
            setupProfile();
        }

        showProgressDialog(this);
        getUserData();
    }

    private void getUserData() {

        Map<String,String> queryMap = new HashMap<>();
        queryMap.put(Constants.QueryParam.ORDER,"desc");
        queryMap.put(Constants.QueryParam.SORT,"name");
        queryMap.put(Constants.QueryParam.FILTER,"default");
        queryMap.put(Constants.QueryParam.SITE,"stackoverflow");
        queryMap.put(Constants.QueryParam.ACCESS_TOKEN, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_TOKEN));
        queryMap.put(Constants.QueryParam.KEY, SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_KEY));

        profileViewModel.getUserInfo(queryMap).observe(this, userResponseList -> {
            cancelProgressDialog();
            if(userResponseList != null  && userResponseList.getItems() != null ){
                User user = userResponseList.getItems().get(0);
                SharedPrefUtil.instance().set(SharedPrefUtil.USER_PROFILE, user);
                setupProfile();
            }
        });

    }

    private void setupProfile() {
        User user = (User) SharedPrefUtil.instance().getSerializable(SharedPrefUtil.USER_PROFILE,User.class);
        binding.userNameTextView.setText(user.getDisplayName());
        Glide.with(this).load(user.getProfileImage()).into(binding.profileImageView);
    }

    public void submit(View view) {
        //TODO update the user info accordingliy if he/she updates in the app.
        startActivity(new Intent(this,HomeActivity.class));
        finish();
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
