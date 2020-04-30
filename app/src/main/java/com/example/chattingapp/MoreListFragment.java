package com.example.chattingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreListFragment extends Fragment {
    private ViewGroup viewGroup;
    private Button btnProfile;
    public MoreListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_more_list, container, false);

        btnProfile = (Button) viewGroup.findViewById(R.id.BtnMoveProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), UserProfileActivity.class);
                startActivity(in);
            }
        });

        return viewGroup;
    }

}
