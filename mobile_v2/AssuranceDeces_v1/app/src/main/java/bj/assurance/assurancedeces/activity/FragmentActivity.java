package bj.assurance.assurancedeces.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepfive;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepfour;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepone;
import bj.assurance.assurancedeces.fragment.marchand.AddContratSteptwo;
import bj.assurance.assurancedeces.fragment.marchand.AddProspect;
import bj.assurance.assurancedeces.fragment.marchand.Historique;
import bj.assurance.assurancedeces.fragment.marchand.MesClients;
import bj.assurance.assurancedeces.fragment.marchand.Transactions;
import bj.assurance.assurancedeces.fragment.supermarchand.AddMarchand;
import bj.assurance.assurancedeces.fragment.supermarchand.ClientsOfMarchands;
import bj.assurance.assurancedeces.model.Marchand;


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


        /*Fragment fragmentA = Fragment.instantiate(this, AddContratStepone.class.getName());
        Fragment fragmentB = Fragment.instantiate(this, AddContratSteptwo.class.getName());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.con, fragmentA)
                .add(R.id.container, fragmentB)
                .show(fragmentA)
                .hide(fragmentB)
                .commit();*/

        checkFragmentStart(getIntent().getExtras().getString("key"));

    }



    private void setClickListener() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentActivity.this.finish();

            }
        });

    }




    private void checkFragmentStart(String string) {

        switch (string) {

            case "enregistrement":

                replaceFragment(new AddContratStepone(), "Enregistrement de contrat");
                break;


            case "enregistrementStep4":

                replaceFragment(new AddContratStepfour(), "Enregistrement de contrat");
                break;



            case "HistoriqueMarchand":


                try {

                    String string1 = getIntent().getExtras().getString("marchand", null);

                    if (string1 == null)
                        replaceFragment(new Historique(), "Historique");

                    else {

                        replaceFragment(new Historique(
                                new Gson().fromJson(string1, Marchand.class), false
                        ), "Historique");

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }


                break;


            case "TransactionsMarchand":

                replaceFragment(new Transactions(), "Transactions");

                break;


            case "Prospects":

                replaceFragment(new AddProspect(), "Ajout de prospect");
                break;



            case "Clients":

                Long id = getIntent().getExtras().getLong("id");
                replaceFragment(new ClientsOfMarchands(id), "Liste client");

                break;


            case "AddMarchand":

                replaceFragment(new AddMarchand(), "Enregistrement de marchand");
                break;

        }

    }





    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment, String title) {

        FragmentManager fragmentManager = FragmentActivity.this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        frameTitle.setText(title);

    }






    @Override
    public void onBackPressed() {
        tellFragments();
    }

    private void tellFragments(){

        List<Fragment> fragments = getSupportFragmentManager().getFragments();


        for(Fragment f : fragments){

            try {

                if(f != null ) {

                    (getFragmentManager()).popBackStack();

                    if (f instanceof AddContratStepone) {

                        ((AddContratStepone) f).doBack();
                        return;
                    }

                    if (f instanceof AddContratSteptwo) {

                        ((AddContratSteptwo) f).doBack();
                        return;
                    }


                    if (f instanceof AddContratStepfive) {

                        ((AddContratStepfive) f).doBack();
                        return;
                    }

                    super.onBackPressed();

                }

            } catch (Exception e) {

                e.printStackTrace();

            }
        }


    }








}
