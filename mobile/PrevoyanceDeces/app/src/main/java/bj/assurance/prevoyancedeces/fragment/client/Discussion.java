package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;

public class Discussion extends Fragment {

    public Discussion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //MarchandMainActivity.getFloatingActionButton().setVisibility(View.INVISIBLE);

        View view = inflater.inflate(R.layout.fragment_discussion, container, false);

        //init();

        return view;
    }

    public void init() {
        Main2Activity.getTextTitle().setVisibility(View.VISIBLE);
        Main2Activity.getBackTitle().setVisibility(View.INVISIBLE);

        Main2Activity.getBackTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }

}
