package com.enaretos.android_templete.singletons;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import androidx.room.Room;

import com.enaretos.android_templete.database.AppDatabase;
import com.enaretos.android_templete.repositories.Repository;
import com.enaretos.android_templete.rest.utils.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APPSINGLETON extends Application {
    public static final String TOKEN = "Token";
    public static final String REFRESHTOKEN = "RefreshToken";
    public static final String USERID = "UserId";
    public static final String USERNAME = "UserName";

    private final String CONTACTS_ENDPOINT = "https://s3.amazonaws.com/technical-challenge/v3/";
    public static APPSINGLETON app;
    private AppDatabase db;
    private Retrofit apiConfig;
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = this.configDB();
        //Call retrofit app
        apiConfig = this.configAPI();
        repository = Repository.getInstance();
    }

    private AppDatabase configDB() {
        return Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "CHALLENGE_DB")
                .fallbackToDestructiveMigration()
                .build();
    }

    private Retrofit configAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS)
                .readTimeout(240, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder();

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String token = pref.getString(TOKEN, null);


                    if (token != null) {
                        builder.header(
                                "Authorization",
                                "Bearer" + " " + token
                        );
                    }

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(logging)
                .cache(null);



        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(CONTACTS_ENDPOINT)
                .client(okHttpClient.build())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //********GETS y SETS***********
    public AppDatabase getDb() {
        return db;
    }

    public Retrofit getApi() {
        return apiConfig;
    }

    public Repository getRepository() {
        return repository;
    }

}


