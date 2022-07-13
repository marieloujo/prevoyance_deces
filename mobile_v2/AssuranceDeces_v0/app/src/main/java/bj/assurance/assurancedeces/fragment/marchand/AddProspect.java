package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.ShowDialog;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProspect extends Fragment {



    private EditText nom,prenom, email;
    private Spinner sexe, commune, phoneIdentity;
    private MaskedEditText telephone;

    private TextView tvEmail, tvTelephone, tvCommune, tvPrenoms, tvNom;

    private Button next, cancel;

    private ContratFormUtils contratFormUtils;
    private ShowDialog showDialog;



    public AddProspect() {



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_prospect, container, false);


        init(view);


        return view;
    }




    private void init(View view) {

        findView(view);
        initValue();
        onClickListener();

    }


    private void findView(View view) {

        nom = view.findViewById(R.id.etNomProspect);
        prenom = view.findViewById(R.id.etPrenomProspect);
        sexe = view.findViewById(R.id.etSexeProspect);
        commune = view.findViewById(R.id.etCommuneProspect);
        email = view.findViewById(R.id.et_emailProspect);
        telephone = view.findViewById(R.id.etTelephoneProspect);
        phoneIdentity = view.findViewById(R.id.phoneIdentity);

        tvNom = view.findViewById(R.id.tvNomProspect);
        tvPrenoms = view.findViewById(R.id.tvPrenomProspect);
        tvEmail = view.findViewById(R.id.tvEmailProspect);
        tvCommune = view.findViewById(R.id.tvCommuneProspect);
        tvTelephone = view.findViewById(R.id.tvTelephoneProspect);

        cancel = view.findViewById(R.id.annuler);
        next = view.findViewById(R.id.suivant);

    }


    private void initValue() {

        contratFormUtils = new ContratFormUtils(getContext());

        showDialog = new ShowDialog(getContext());

        makeSpinnerList();

    }



    private void makeSpinnerList() {

        contratFormUtils.makeCommuneList(commune);
        contratFormUtils.makeSexeSpinnerList(sexe);
        contratFormUtils.makePhoneIdentityList(phoneIdentity);

    }




    private void onClickListener() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verifData()) {

                    createProspect(
                            getUtilisateurFromForm()
                    );


                }

            }
        });

    }




    @SuppressLint("SetTextI18n")
    private boolean verifData() {

        boolean isValid = true;

        if (nom.getText().toString().isEmpty()) {

            tvNom.setText("Le nom est oblicatoire");
            tvNom.setVisibility(View.VISIBLE);

            isValid = false;

        } else {

            tvNom.setText("");
            tvNom.setVisibility(View.INVISIBLE);
        }

        if (prenom.getText().toString().isEmpty()) {

            tvPrenoms.setText("Le prenom est oblicatoire");
            tvPrenoms.setVisibility(View.VISIBLE);
            isValid = false;

        } else {

            tvPrenoms.setText("");
            tvPrenoms.setVisibility(View.INVISIBLE);

        }

        if (email.getText().toString().isEmpty() || telephone.getRawText().isEmpty()) {

            isValid = false;

            if (email.getText().toString().isEmpty()) {

                tvEmail.setText("Veuillez renseinger l'email");
                tvEmail.setVisibility(View.VISIBLE);

            } else {

                tvTelephone.setVisibility(View.INVISIBLE);
                tvTelephone.setText("");
            }

        } else {

            tvEmail.setText("");
            tvEmail.setVisibility(View.INVISIBLE);

        }

        if (commune.getSelectedItem().toString().equals("Commune")) {

            isValid = false;
            tvCommune.setText("La commune est obligatoire");
            tvCommune.setVisibility(View.VISIBLE);

        } else

            tvCommune.setVisibility(View.INVISIBLE);

        return isValid;
    }



    private Utilisateur getUtilisateurFromForm() {

        return new
            Utilisateur(
                nom.getText().toString(),
                prenom.getText().toString(),
                telephone.getText().toString(),
                email.getText().toString(),
                sexe.getSelectedItem().toString(),
                null,
                null,
                null,
                true,
                false,
                contratFormUtils.getSelectedCommune(commune)
            );
    }




    private void createProspect(final Utilisateur utilisateur) {


        new UtilisateurServiceImplementation(getContext())
            .createPospect(utilisateur)
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            try {
                                showDialog.getAlertDialog().dismiss();
                            } catch (Exception e) {}

                            showDialog.showSuccessDialog("Enregistrement réussi",
                                    "Le prospect à été bien enregistré")

                                    .setPositiveListener(new ClickListener() {

                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                            resetForm();
                                        }
                                    }).build().show();


                        } else {

                            String string = new CatchApiError(response.code()).catchError(getContext());

                            showDialog.showErrorDialog("error", "","")
                                    .build().show();


                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            showDialog.getAlertDialog().dismiss();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                            @Override
                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                lottieAlertDialog.dismiss();
                                createProspect(utilisateur);
                            }
                        })
                        .build()
                        .show();

                    }


                });


    }



    private void resetForm() {

        nom.setText("");
        prenom.setText("");
        telephone.setText("");
        email.setText("");

    }



}
