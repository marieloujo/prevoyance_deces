package bj.assurance.assurancedeces.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.utils.OnBackPressedListener;


public class CatchError extends Fragment implements OnBackPressedListener {



    private TextView errorText;
    private Button button;

    private String text, buttonTexte;

    private boolean isVisisble;
    private View view;


    public CatchError() {
        // Required empty public constructor
    }




    @SuppressLint("ValidFragment")
    public CatchError(String errorText, String buttonTexte, boolean isVisible) {

        text = errorText;

        this.buttonTexte = buttonTexte;
        this.isVisisble = isVisible;

    }






    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_catch_error, container, false);


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
        button.setText(buttonTexte);


        if (isVisisble)
            button.setVisibility(View.VISIBLE);


        else button.setVisibility(View.INVISIBLE);



    }


    @Override
    public void doBack() {



    }
}
