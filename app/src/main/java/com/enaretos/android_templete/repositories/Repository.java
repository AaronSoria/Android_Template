package com.enaretos.android_templete.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.enaretos.android_templete.database.AppDatabase;
import com.enaretos.android_templete.rest.models.ListOfValuesResponseModel;
import com.enaretos.android_templete.rest.resources.ContactResources;
import com.enaretos.android_templete.rest.responses.ApiResponse;
import com.enaretos.android_templete.rest.utils.DirectNetworkBoundResource;
import com.enaretos.android_templete.singletons.APPSINGLETON;
import com.enaretos.android_templete.view.vo.Resource;

public class Repository {
    private static Repository instance;
    AppDatabase db;
    com.enaretos.android_templete.rest.resources.ContactResources contactResource;

    private Repository() {
        contactResource = APPSINGLETON.app.getApi().create(ContactResources.class);
        db = APPSINGLETON.app.getDb();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<Resource<ListOfValuesResponseModel>> getLOVs() {
        return new DirectNetworkBoundResource<ListOfValuesResponseModel, ListOfValuesResponseModel>() {
            @Override
            protected ListOfValuesResponseModel processResponse(ApiResponse<ListOfValuesResponseModel> response){

                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ListOfValuesResponseModel>> createCall() {
                return null;
            }


        }.asLiveData();
    }
}
