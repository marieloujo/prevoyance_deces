package bj.assurance.assurancedeces.fragment;



import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bj.assurance.assurancedeces.R;





public class NoDataFound extends Fragment {



    private int image;
    private String texte;

    private ImageView imageView;
    private TextView textView;




    @SuppressLint("ValidFragment")
    public NoDataFound(int image, String texte) {
        this.image = image;
        this.texte = texte;
    }




    public NoDataFound() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_no_data_found, container, false);


        init(view);


        return view;
    }








    private void init(View view) {


        findView(view);
        initValue();


    }





    private void findView(View view) {

        imageView = view.findViewById(R.id.no_data_icon);
        textView = view.findViewById(R.id.no_data_text);

    }




    private void initValue() {

        //imageView.setImageDrawable(getM);
        textView.setText(texte);

    }





}
