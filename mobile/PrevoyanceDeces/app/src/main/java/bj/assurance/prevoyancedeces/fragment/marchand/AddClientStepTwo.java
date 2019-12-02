package bj.assurance.prevoyancedeces.fragment.marchand;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;

import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.model.Assurer;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import br.com.sapereaude.maskedEditText.MaskedEditText;

public class AddClientStepTwo extends Fragment {

    private Button cancel, next;
    //private TextView tvNom, tvPrenoms, tvEmail, tvAdresse, tvSituationMatrimoniale, tvSexe, tvDateNaissance;
    private EditText etNom, etPrenoms, etAdresse, etDateNaissance, etProfession, etEmployeur;
    private FormEditText etEmail;
    private Spinner etSituationMatrimoniale, etSexe;
    private MaskedEditText etTelephone;

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

        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

        System.out.println(MarchandMainActivity.getContrat());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFraglent(new ListeClients());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Assurer assurer = new Assurer(etProfession.getText().toString(),new Utilisateur(
                        etNom.getText().toString(), etPrenoms.getText().toString(), etTelephone.getRawText(),
                        etEmail.getText().toString(), etSexe.getSelectedItem().toString(), etDateNaissance.getText().toString(),
                        etSituationMatrimoniale.getSelectedItem().toString(), etAdresse.getText().toString(), false,
                        false, "WETSETT", "DEA24356", new Commune()),
                        false);

                MarchandMainActivity.getContrat().setAssurer(assurer);

                replaceFraglent(new AddClientStepThree());
            }
        });

        return view;
    }


    public void init(View view) {
        etNom = view.findViewById(R.id.et_nomAssure);
        etPrenoms = view.findViewById(R.id.etPrenomAssure);
        etEmail = view.findViewById(R.id.etEmailAssure);
        etAdresse = view.findViewById(R.id.etAdresseAssure);
        etSituationMatrimoniale = view.findViewById(R.id.etSituationMatrimonialeAssure);
        etSexe = view.findViewById(R.id.etSexeAssure);
        etDateNaissance = view.findViewById(R.id.etDateNaissanceAssure);
        etEmployeur = view.findViewById(R.id.etEmployeurAssure);
        etProfession = view.findViewById(R.id.etProfessionAssure);
        etTelephone = view.findViewById(R.id.etTelephoneAssure);

        /*
        tvNom = view.findViewById(R.id.tvNomClient);
        tvPrenoms = view.findViewById(R.id.tvPrenomClient);
        tvEmail = view.findViewById(R.id.tvEmailClient);
        tvAdresse = view.findViewById(R.id.tvAdresseClient);
        tvSituationMatrimoniale = view.findViewById(R.id.tvSituationMatrimonialeClient);
        tvSexe = view.findViewById(R.id.tvetSexeClient);
        tvDateNaissance = view.findViewById(R.id.tvDateNaissanceClient);*/

    }

    public void replaceFraglent(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

    }

}
