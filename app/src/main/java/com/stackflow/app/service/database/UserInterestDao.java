package com.stackflow.app.service.database;

import com.stackflow.app.service.model.UserInterest;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserInterestDao {

    @Query("SELECT userInterest FROM UserInterest")
    LiveData<List<String>> getUserInterest();

    @Query("SELECT interestTags FROM UserInterest WHERE userInterest = :userInterest")
    LiveData<List<String>> getInterestTag(String userInterest);

    @Insert
    void insertUserInterests(UserInterest interests);

    @Update
    void updateUserInterest(UserInterest userInterest);

}
