package com.stackflow.app.view.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.stackflow.app.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void showProgressDialog(Activity activity) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            if (dialog == null) {
                dialog = new ProgressDialog(activity);
            }

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            if (!activity.isFinishing()) dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelProgressDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public abstract boolean isHomeAsUpEnabled();

    public abstract boolean isToolbarRequired();

    public abstract void onBackPressed();

    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
