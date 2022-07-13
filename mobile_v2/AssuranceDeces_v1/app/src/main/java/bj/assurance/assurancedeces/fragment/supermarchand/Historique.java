package bj.assurance.assurancedeces.fragment.supermarchand;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.model.Compte;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.SuperMarchandServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.ContratFormUtils;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import im.dacer.androidcharts.LineView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Historique extends Fragment {



    private Spinner annee, semestre;
    private LineView lineView;

    private TextView soldeCommission;


    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;


    private List<Compte> compte = new ArrayList<>();






    public Historique() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historique_supermarchand, container, false);


        try {

            init(view);

        } catch (ParseException e) {

            e.printStackTrace();

        }


        return view;
    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(View view) throws ParseException {

        findView(view);
        initValue();

    }





    private void findView(View view) {


        annee = view.findViewById(R.id.etAnnee);
        semestre = view.findViewById(R.id.etSemestre);

        soldeCommission = view.findViewById(R.id.solde_commisssions);

        lineView = view.findViewById(R.id.line_view);

    }





    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initValue() throws ParseException {



        soldeCommission.setText(SuperMarchandActivity.getSuperMarchand().getCommission() + " points");


        showDialog = new ShowDialog(getContext());


        getResponseObject = new GetResponseObject();


        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setColorArray(new int[]{Color.parseColor("#424345"),Color.parseColor("#2dad58")});

        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);


        makeSpinnerList();

        historiqueCompteofMarchnd(
                SuperMarchandActivity.getSuperMarchand().getId(),
                new Date()
        );


       /* getCommission(

                SuperMarchandActivity.getSuperMarchand().getId()

        );
*/

    }








    @RequiresApi(api = Build.VERSION_CODES.M)
    private void makeSpinnerList() throws ParseException {


        new ContratFormUtils(getContext(),
                SuperMarchandActivity.getUtilisateur().getCommune().getDepartement().getId()).makeAnneeSpinnerList(annee, semestre,
                SuperMarchandActivity.getSuperMarchand().getDateCreation());

    }






    private void historiqueCompteofMarchnd(final Long id, final Date date) {


        new SuperMarchandServiceImplementation(


                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).getHistoriqueCommissionofSupermarchandbyDate(id, date)

                .enqueue(new Callback<JsonObject>() {


                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {



                        if (response.isSuccessful()) {


                            try {

                                getResponseObject.setJsonObject(response.body());

                                CustomJsonObject jsonObject = getResponseObject.getResponse();


                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Compte>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        jsonObject.getJsonObject(), OutputPaginate.class);


                                compte = gson.fromJson (

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType

                                );

                                makeGraphe(compte);

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        } else {

                            String string = new CatchApiError(response.code()).catchError(getContext());


                        }



                    }




                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {



                        try {

                            showDialog.showNoInternetDialog()

                                    .setPositiveListener( new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();

                                            historiqueCompteofMarchnd(id, date);
                                        }
                                    })

                                    .setNegativeListener( new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })

                                    .setNegativeText("Annuler")
                                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                                    .build()
                                    .show();

                        } catch (Exception ignored) {}



                    }



                });

    }





    private void makeGraphe(List<Compte> comptes) {

        List<Float> commission = new ArrayList<>();

        for (int i = 0; i<comptes.size(); i++) {

            commission.add(Float.valueOf(comptes.get(i).getMontant()));

        }

        ArrayList<ArrayList<Float>> graphes = new ArrayList<>();
        graphes.add((ArrayList<Float>) commission);

        lineView.setFloatDataList(graphes);

    }





    private void getCommission(final Long id) {


        new SuperMarchandServiceImplementation(

                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).getCommissionofSupermarchand(id)

                .enqueue(new Callback<JsonObject>() {


                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {


                            Compte compte = new Gson().fromJson(

                                    response.body().getAsJsonObject("success").get("commission"),
                                    Compte.class
                            );


                            System.out.println(compte);

                            soldeCommission.setText(compte.getMontant() + " points");


                        }  else {


                            new CatchApiError(response.code()).catchError(getContext());

                            showDialog.showErrorDialog("Oups...",
                                    "Nous n'avons pas pu récupérer votre dernier recharge de crédit virtuelle",
                                    "Réesayer")
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(id);

                                        }
                                    })

                                    .setNegativeListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })

                                    .setNegativeText("Annuler")
                                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                                    .build()
                                    .show();

                        }


                    }




                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {

                                            lottieAlertDialog.dismiss();
                                            getCommission(id);

                                        }
                                    })

                                    .setNegativeListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })

                                    .setNegativeText("Annuler")
                                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                                    .build()
                                    .show();

                        } catch (Exception ignored) {}


                    }


                });


    }




}
