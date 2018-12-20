package com.stackflow.app.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInterest {

    @PrimaryKey
    @NonNull
    private String userInterest;

    @NonNull
    private String interestTags;

    @NonNull
    public String getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(@NonNull String userInterest) {
        this.userInterest = userInterest;
    }

    @NonNull
    public String getInterestTags() {
        return interestTags;
    }

    public void setInterestTags(@NonNull String interestTags) {
        this.interestTags = interestTags;
    }
}
