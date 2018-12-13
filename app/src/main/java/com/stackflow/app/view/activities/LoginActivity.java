package com.stackflow.app.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.stackflow.app.BuildConfig;
import com.stackflow.app.R;
import com.stackflow.app.databinding.ActivityLoginBinding;
import com.stackflow.app.util.Constants;
import com.stackflow.app.util.SharedPrefUtil;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //check of uri data coming from redirect uri
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().contains("access_token")) {

            String redirect_uri = uri.toString();
            String access_token = redirect_uri.substring(redirect_uri.indexOf("=") + 1);
            SharedPrefUtil.instance().set(SharedPrefUtil.ACCESS_TOKEN, access_token);
            gotoProfileActivity();
            cancelProgressDialog();

        } else if (!TextUtils.isEmpty(SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_TOKEN))) {
            gotoHomeActivity();
        }


        binding.loginBtn.setOnClickListener(v -> {

            showProgressDialog(this);

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority("stackoverflow.com").appendPath("oauth").appendPath("dialog").appendQueryParameter(Constants.QueryParam.CLIENT_ID, BuildConfig.CLIENT_ID).appendQueryParameter(Constants.QueryParam.SCOPE, "no_expiry").appendQueryParameter(Constants.QueryParam.REDIRECT_URI, "https://stackexchange.com/oauth/login_success");

            String authUrl = builder.build().toString();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
            startActivity(intent);

        });

    }

    private void gotoProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

    private void gotoHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
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
        finish();
    }
}
