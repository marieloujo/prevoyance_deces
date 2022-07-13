package bj.assurance.assurancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

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
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
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



    private ShowDialog showDialog;
    private GetResponseObject getResponseObject;


    private List<Compte> compte = new ArrayList<>();

    private Marchand marchand = null;
    private boolean creditVirtuelEvolution = true;




    public Historique() {
        // Required empty public constructor
    }




    @SuppressLint("ValidFragment")
    public Historique(Marchand marchand, boolean creditVirtuelEvolution) {
        this.marchand = marchand;
        this.creditVirtuelEvolution = creditVirtuelEvolution;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_historique, container, false);


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

        lineView = view.findViewById(R.id.line_view);

    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initValue() throws ParseException {


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

        if (marchand == null) {

            historiqueCompteofMarchnd(
                    MarchandActivity.getMarchand().getId(),
                    new Date()
            );

        } else {

            historiqueCompteofMarchnd(
                    marchand.getId(),
                    new Date()
            );

        }


    }








    @RequiresApi(api = Build.VERSION_CODES.M)
    private void makeSpinnerList() throws ParseException {

        if (marchand == null) {

            new ContratFormUtils(getContext(), MarchandActivity.getUtilisateur().getCommune().getDepartement().getId())
                    .makeAnneeSpinnerList(annee, semestre, MarchandActivity.getMarchand().getDateCreation());

        } else {

            new ContratFormUtils(getContext(), null)
                    .makeAnneeSpinnerList(annee, semestre, marchand.getDateCreation());

        }

    }






    private void historiqueCompteofMarchnd(final Long id, final Date date) {


        new MarchandServiceImplementation(


                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).historiqueCompteOfMarchandByDate(id, date)

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

        List<Float> creditVirtuel = new ArrayList<>();
        List<Float> commission = new ArrayList<>();

        for (int i = 0; i<comptes.size(); i++) {

            if (comptes.get(i).getCompte().equals("credit_virtuel")) {


                creditVirtuel.add(Float.valueOf(comptes.get(i).getMontant()));


            } else if (comptes.get(i).getCompte().equals("commission")) {

                commission.add(Float.valueOf(comptes.get(i).getMontant()));

            }
        }

        ArrayList<ArrayList<Float>> graphes = new ArrayList<>();
        graphes.add((ArrayList<Float>) commission);

        if (creditVirtuelEvolution)
            graphes.add((ArrayList<Float>) creditVirtuel);
        else graphes.add(new ArrayList<Float>());

        lineView.setFloatDataList(graphes);

    }




}
