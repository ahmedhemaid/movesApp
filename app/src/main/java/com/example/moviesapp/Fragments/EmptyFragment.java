package com.example.moviesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesapp.R;

public class EmptyFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.emptyfragment, container, false);
        return rootView;
    }

}


