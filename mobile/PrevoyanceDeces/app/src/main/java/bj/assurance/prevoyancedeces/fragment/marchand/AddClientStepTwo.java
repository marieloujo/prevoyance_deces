package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.msa.dateedittext.DateEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Assurer;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import br.com.sapereaude.maskedEditText.MaskedEditText;

public class AddClientStepTwo extends Fragment {

    private Button cancel, next;
    private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance, tvProfession, tvEmployeur, tvCommune, tvTelephone;
    private EditText etNom, etPrenoms, etAdresse, etProfession, etEmployeur;
    private DateEditText etDateNaissance;
    private FormEditText etEmail;
    private Spinner etSituationMatrimoniale, etSexe, etCommune;
    private MaskedEditText etTelephone;
    SimpleDateFormat dtYYYY = new SimpleDateFormat("YYYY");

    public AddClientStepTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_client_step_two, container, false);

        init(view);
        setClickListener();

        return view;
    }


    public void init(View view) {
        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        etNom = view.findViewById(R.id.etNomClient);
        etPrenoms = view.findViewById(R.id.etPrenomClient);
        etEmail = view.findViewById(R.id.et_email);
        etAdresse = view.findViewById(R.id.etAdresseClient);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeClient);
        etSexe = view.findViewById(R.id.etSexeClient);
        etDateNaissance = view.findViewById(R.id.dateEditText);
        etEmployeur = view.findViewById(R.id.etEmployeur);
        etProfession = view.findViewById(R.id.etProfession);
        etTelephone = view.findViewById(R.id.etTelephoneClient);
        etCommune = view.findViewById(R.id.etCommuneClient);

        etDateNaissance.listen();

        int minYear = new Date().getYear() - 74;
        int maxYear = new Date().getYear() - 18;

        etDateNaissance.setMinDate(new Date(minYear,12,31));
        etDateNaissance.setMaxDate(new Date(maxYear, 12,31));



        tvNom = view.findViewById(R.id.tvNomClient);
        tvPrenoms = view.findViewById(R.id.tvPrenomClient);
        tvEmail = view.findViewById(R.id.tvEmailClient);
        tvAdresse = view.findViewById(R.id.tvAdresseDomicileClient);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeClient);
        tvSexe = view.findViewById(R.id.tvetSexeClient);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceClient);
        tvCommune = view.findViewById(R.id.tvCommuneClient);
        tvProfession = view.findViewById(R.id.tvProfessionClient);
        tvEmployeur = view.findViewById(R.id.tvEmployeurClient);
        tvTelephone = view.findViewById(R.id.tvTelephoneClient);

        makeSpinnerList();
        //autoCompleCommune();

    }

    /*public void autoCompleCommune() {
        String communeName[] = {};
        for (int i = 0; i < communes.size(); i++) {
            communeName[i] = communes.get(i).getNom();
        }

        System.out.println(communeName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, communeName);
        etCommune.setAdapter(adapter);

    }*/

    private void setClickListener() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFraglent(new ListeClients());
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                if (verifeData()) {

                    System.out.println(MarchandMainActivity.getContrat().toString());

                    Commune commune = new Commune();


                    try {
                        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
                            if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                                commune = MarchandMainActivity.getCommunes().get(i);
                            }
                        }
                    } catch (Exception e) {

                    }

                    Assurer assurer = new Assurer(etProfession.getText().toString(), etEmployeur.getText().toString(),new Utilisateur(
                            etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                            etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                            etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                            false, commune), false
                    );

                    MarchandMainActivity.getContrat().setAssurer(assurer);

                    replaceFraglent(new AddClientStepThree());

                }

                // if (verifeData()) {

                    /*
                    Commune commune = new Commune();


                    for (int i = 0; i<MarchandMainActivity.getCommunes().size(); i++) {
                        if (MarchandMainActivity.getCommunes().get(i).getNom().equals(etCommune.getSelectedItem().toString())) {
                            commune = MarchandMainActivity.getCommunes().get(i);
                        }
                    }

                    Client client = new Client(etProfession.getText().toString(), etEmployeur.getText().toString(),new Utilisateur(
                            etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                            etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                            etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                            false, commune)
                    );

                    MarchandMainActivity.getContrat().setClient(client);
                    replaceFraglent(new AddClientStepTwo());

                    Commune commune = new Commune("Abomey-calavi", new Departement("Atlantique"));

                    Utilisateur utilisateur = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 00", "aziz@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur1 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 01", "aziz1@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur2 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 02", "aziz2@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);

                    Utilisateur utilisateur3 = new Utilisateur("FLOUKOUNBE", "Aziz", "00 00 00 02", "aziz2@gmail.com", "masculin",
                            simpleDateFormat.format(new Date()), "celibaire sans enfant", "calavi", false, false,  commune);


                    Client client = new Client("electicien", "VISOUSSI carine", utilisateur);

                    Marchand marchand = new Marchand("GYY176767878", "435678", "35467", new SuperMarchand(), utilisateur1);

                    Benefice benefice = new Benefice("epouse", "30", new Beneficiaire(utilisateur3));

                    List<Benefice> benefices = new ArrayList<>();
                    benefices.add(benefice);

                    Assurer assurer = new Assurer("Vitrie", utilisateur2, false);

                    Contrat contrat = new Contrat("1 000 000", "1 000", "1", simpleDateFormat.format(new Date()),
                            simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), simpleDateFormat.format(new Date()), client,
                            marchand, benefices, assurer);


                System.out.println(contrat.toString());

                    senContrat(TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken(),
                            contrat);

                    //System.out.println(contrat.toString());*/

            }
            //replaceFraglent(new AddClientStepTwo());
            //}
        });
    }

    private void replaceFraglent(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

    }

    private boolean verifeData() {

        boolean isValid = true;

        if (etNom.getText().toString().isEmpty()) {
            tvNom.setVisibility(View.VISIBLE);

            isValid = false;

        } else {
            tvNom.setVisibility(View.INVISIBLE);
        }

        if (etPrenoms.getText().toString().isEmpty()) {

            tvPrenoms.setVisibility(View.VISIBLE);
            isValid = false;

        } else tvPrenoms.setVisibility(View.INVISIBLE);

        if (etEmail.getText().toString().isEmpty()) {
            isValid = false;
            tvEmail.setVisibility(View.VISIBLE);
        } else tvEmail.setVisibility(View.INVISIBLE);

        if (etAdresse.getText().toString().isEmpty()) {
            isValid = false;
            tvAdresse.setVisibility(View.VISIBLE);
        } else tvAdresse.setVisibility(View.INVISIBLE);

        if (etSituationMatrimoniale.getSelectedItem().toString().equals("Situation matrimoniale")) {
            isValid = false;
            tvSituationMatrimoniale.setVisibility(View.VISIBLE);
        } else tvSituationMatrimoniale.setVisibility(View.INVISIBLE);

        if (etSexe.getSelectedItem().toString().equals("Sexe")) {
            isValid = false;
            tvSexe.setVisibility(View.VISIBLE);
        } else tvSexe.setVisibility(View.INVISIBLE);

        if (etDateNaissance.getText().toString().equals("")) {
            isValid = false;
            tvDateNaissance.setVisibility(View.VISIBLE);
        } else tvDateNaissance.setVisibility(View.INVISIBLE);

       /* if (etCommune.getSelectedItem().toString().equals("Commune")) {
            isValid = false;
            tvCommune.setVisibility(View.VISIBLE);
        } else tvCommune.setVisibility(View.INVISIBLE);*/

        if (etProfession.getText().toString().isEmpty()) {
            isValid = false;
            tvProfession.setVisibility(View.VISIBLE);
        } else tvProfession.setVisibility(View.INVISIBLE);

        if (etEmployeur.getText().toString().isEmpty()) {
            isValid = false;
            tvEmployeur.setVisibility(View.VISIBLE);
        } else tvEmployeur.setVisibility(View.INVISIBLE);

        if (etTelephone.getRawText().isEmpty()) {
            isValid = false;
            etTelephone.setVisibility(View.VISIBLE);
        } else etTelephone.setVisibility(View.INVISIBLE);


        boolean allValid = etEmail.testValidity() ;

        return isValid && allValid;

    }


    private void makeSpinnerList() {

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.situation_matrimoniale)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        final ArrayAdapter<String> sexeArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.sexe)){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        String[] communess = new String[MarchandMainActivity.getCommunes().size()+1];
        communess[0] = "Commune";
        for (int i = 0; i < MarchandMainActivity.getCommunes().size(); i++) {
            communess[i+1] = MarchandMainActivity.getCommunes().get(i).getNom();
            System.out.println(communess);
        }

        final ArrayAdapter<String> communeArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,communess){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        communeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        etSituationMatrimoniale.setAdapter(spinnerArrayAdapter);
        etSexe.setAdapter(sexeArrayAdapter);
        etCommune.setAdapter(communeArrayAdapter);

    }



}
