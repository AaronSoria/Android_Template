package com.enaretos.android_templete.utils;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdapters {

    @BindingAdapter("data")
    public static <T> void setRecyclerViewProperties(RecyclerView recyclerView, T data ){
        if (recyclerView.getAdapter() != null){
            BindableAdapter adapter = (BindableAdapter)recyclerView.getAdapter();
            adapter.setData(data);
        }

    }
}