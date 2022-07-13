package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bj.assurance.prevoyancedeces.R;


public class Prospects extends Fragment {

    public Prospects() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prospects, container, false);

        TextView textView = view.findViewById(R.id.no_data);
        textView.setText("Vous n'avez encore enregistrer aucun prospects");



        return view;
    }


}
