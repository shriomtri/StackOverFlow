package com.stackflow.app.service.database;

import com.stackflow.app.service.database.TypeConverter.StringArrayConvertor;
import com.stackflow.app.service.model.UserInterest;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserInterest.class},
          version = AppDatabase.DATABASE_VERSION,
          exportSchema = false)
@TypeConverters({StringArrayConvertor.class})
public abstract class AppDatabase extends RoomDatabase {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "stackflow.db";

    public abstract UserInterestDao getUserInterestDao();


    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //no schema updated
        }
    };

}
