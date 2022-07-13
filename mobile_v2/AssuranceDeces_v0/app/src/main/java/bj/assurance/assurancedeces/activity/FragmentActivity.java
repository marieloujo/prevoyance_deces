package bj.assurance.assurancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.BreakIterator;

public class FragmentActivity extends AppCompatActivity {



    private TextView frameTitle;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        init();
    }




    private void init() {

        findView();
        initValue();
        setClickListener();

    }



    private void findView() {

        frameTitle = findViewById(R.id.title_frame);
        imageView = findViewById(R.id.backFrame);

    }



    private void initValue() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentFragment,new Accueil());
        fragmentTransaction.commit();


        System.out.println(getIntent().getExtras().getString("key"));
        checkFragmentStart(getIntent().getExtras().getString("key"));

    }



    private void setClickListener() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FragmentActivity.this, MarchandActivity.class));
            }
        });

    }




    private void checkFragmentStart(String string) {


        switch (string) {


            case "enregistrement":

                replaceFragment(new AddContratStepone(), "Enregistrement de contrat");

                break;


        }


    }





    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = (FragmentActivity.this).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        frameTitle.setText(title);

    }




}
