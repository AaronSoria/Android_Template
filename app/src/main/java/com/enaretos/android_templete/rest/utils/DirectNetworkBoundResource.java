package com.enaretos.android_templete.rest.utils;

import android.text.TextUtils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.enaretos.android_templete.rest.responses.ApiResponse;
import com.enaretos.android_templete.singletons.AppExecutors;
import com.enaretos.android_templete.view.vo.Resource;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class DirectNetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public DirectNetworkBoundResource() {
        this.appExecutors = AppExecutors.getInstance();
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.setValue(Resource.loading(null));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            if (response.isSuccessful()) {
                appExecutors.networkIO().execute(() -> {
                    result.postValue(Resource.success(processResponse(response)));
                });
            } else {
                String errorMsg = "General Error.";
                if(response.getError() != null && !TextUtils.isEmpty(response.getError().getMessage())){
                    errorMsg = response.getError().getMessage();
                }
                result.postValue(Resource.error(errorMsg, null,response.getStatusCode()));
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected abstract ResultType processResponse(ApiResponse<RequestType> response);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
