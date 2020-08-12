package com.enaretos.android_templete.rest.resources;

import androidx.lifecycle.LiveData;

import com.enaretos.android_templete.models.ContactModel;
import com.enaretos.android_templete.rest.responses.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface ContactResources {

    @GET("contacts.json")
    LiveData<ApiResponse<List<ContactModel>>> getContacts();
}
