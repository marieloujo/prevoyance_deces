package bj.assurance.assurancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Utilisateur;


public class MonProfil extends Fragment {



    private TextView nomPrenomClient, adresseClient, adresseMailClient;

    private Client client;


    public MonProfil() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mon_profil_client, container, false);


        init(view);


        return view;
    }







    private void init(View view) {


        findView(view);
        initValue();



    }




    private void findView(View view) {


        nomPrenomClient = view.findViewById(R.id.nom_prenom_clent);
        adresseClient = view.findViewById(R.id.adresse_client);
        adresseMailClient = view.findViewById(R.id.adresse_mail_client);

    }





    @SuppressLint("SetTextI18n")
    private void initValue() {

        Utilisateur utilisateur = ClientActivity.getUtilisateur();


        nomPrenomClient.setText(

                utilisateur.getNom()
                    + " " +
                utilisateur.getPrenom()
        );


        adresseMailClient.setText(utilisateur.getEmail());


        adresseClient.setText(utilisateur.getAdresse());


    }



}
