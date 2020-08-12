package com.enaretos.android_templete.rest.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.enaretos.android_templete.models.ErrorModel;
import com.enaretos.android_templete.rest.responses.ApiResponse;


import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        MutableLiveData<ApiResponse<R>> observable = new MutableLiveData<>();

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                ApiResponse<R> data;
                data = new ApiResponse<>(response);
                observable.postValue(data);
            }

            @Override
            public void onFailure(Call<R> call, Throwable throwable) {
                throwable.printStackTrace();
                ErrorModel error = new ErrorModel(throwable.getMessage());
                observable.postValue(new ApiResponse<>(ApiResponse.ERROR_STATUS, error));
            }
        });

        return observable;
    }
}
