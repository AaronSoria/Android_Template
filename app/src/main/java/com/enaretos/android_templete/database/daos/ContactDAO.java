package com.enaretos.android_templete.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

import com.enaretos.android_templete.models.ContactModel;

@Dao
public interface ContactDAO {

    @Query("SELECT * FROM ContactModel")
    LiveData<List<ContactModel>> getAll();

    @Query("SELECT * FROM ContactModel c WHERE c.isFavorite = 1 ORDER BY c.name")
    LiveData<List<ContactModel>> getFavoriteContacts();

    @Query("SELECT * FROM ContactModel c WHERE c.isFavorite = 0 ORDER BY c.name")
    LiveData<List<ContactModel>> getOtherContacts();

    @Insert(onConflict = REPLACE)
    void insertAll(List<ContactModel> unitList);

    @Query("DELETE FROM ContactModel")
    void clear();

    @Query("SELECT * FROM ContactModel c WHERE c.id = :contactId LIMIT 1")
    LiveData<ContactModel> getContact(int contactId);
}
