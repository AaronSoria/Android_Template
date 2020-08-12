package com.enaretos.android_templete.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.enaretos.android_templete.database.daos.ContactDAO;
import com.enaretos.android_templete.models.ContactModel;

@Database(entities = {
        ContactModel.class,
},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDAO contactDAO();


}