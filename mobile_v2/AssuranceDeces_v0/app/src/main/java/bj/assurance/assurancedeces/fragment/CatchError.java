package bj.assurance.assurancedeces.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import bj.assurance.assurancedeces.R;




public class CatchError extends Fragment {



    private TextView errorText;
    private Button button;

    private String text;



    public CatchError() {
        // Required empty public constructor
    }




    @SuppressLint("ValidFragment")
    public CatchError(String errorText) {

        text = errorText;

    }






    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catch_error, container, false);


        init(view);


        return view;
    }




    private void init(View view) {

        findView(view);
        initValue();

    }




    private void findView(View view) {

        errorText = view.findViewById(R.id.error_text);
        button = view.findViewById(R.id.retry);

    }




    private void initValue() {

        errorText.setText(text);

    }



}
