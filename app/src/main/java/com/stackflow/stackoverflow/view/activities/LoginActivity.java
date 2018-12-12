package com.stackflow.stackoverflow.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.stackflow.stackoverflow.BuildConfig;
import com.stackflow.stackoverflow.R;
import com.stackflow.stackoverflow.databinding.ActivityLoginBinding;
import com.stackflow.stackoverflow.util.SharedPrefUtil;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    //TODO remove constants to saperate java file

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //check of uri data coming from redirect uri
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().contains("access_token")) {
            String redirect_uri = uri.toString();
            String access_token = redirect_uri.substring(redirect_uri.indexOf("=")+1);
            SharedPrefUtil.instance().set(SharedPrefUtil.ACCESS_TOKEN, access_token);
            gotHomeActivity();
            cancelProgressDialog();
        }

        //check of ACCESS_TOKEN nullablity
        if (!TextUtils.isEmpty(SharedPrefUtil.instance().getString(SharedPrefUtil.ACCESS_TOKEN))) {
            gotHomeActivity();
        }

        binding.loginBtn.setOnClickListener(v -> {

            showProgressDialog(this);

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("stackoverflow.com")
                    .appendPath("oauth")
                    .appendPath("dialog")
                    .appendQueryParameter("client_id",BuildConfig.CLIENT_ID)
                    .appendQueryParameter("scope", "no_expiry")
                    .appendQueryParameter("redirect_uri","https://stackexchange.com/oauth/login_success");

            String authUrl = builder.build().toString();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
            startActivity(intent);

        });

    }

    private void gotHomeActivity() {
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
