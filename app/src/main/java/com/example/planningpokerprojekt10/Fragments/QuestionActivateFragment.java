package com.example.planningpokerprojekt10.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planningpokerprojekt10.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionActivateFragment extends Fragment {

    private View mView;

    public QuestionActivateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.questionactivate_fragment,container,false);


        return mView;
    }

}
