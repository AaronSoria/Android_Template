package com.enaretos.android_templete.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.enaretos.android_templete.R;
import com.enaretos.android_templete.repositories.Repository;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Repository.getInstance().getLOVs().observe(this, listOfValuesResponseModelResource -> {
            System.out.println("ok");
        });

        setContentView(R.layout.activity_main);

    }


}
