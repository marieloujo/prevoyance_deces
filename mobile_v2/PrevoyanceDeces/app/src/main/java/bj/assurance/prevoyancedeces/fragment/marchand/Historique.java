package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import im.dacer.androidcharts.LineView;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class Historique extends Fragment {

    int randomint = 9;

    LineView lineView;

    private List<Float> creditVirtules = new ArrayList<>();
    private List<Float> commisions = new ArrayList<>();

    private Spinner annee, semestre;

    private List<String> listAnnee = new ArrayList<>();
    private List<String> listSemestre = new ArrayList<>();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM yyyy", Locale.FRANCE);
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM", Locale.FRANCE);

    int date;

    public Historique() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historique, container, false);

        init(view);
        getListCompte(
            TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken()
        );

        try {
            getValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        makeSpinnerList();

        return view;
    }

    public void init(View view) {

        annee = view.findViewById(R.id.etAnnee);
        semestre = view.findViewById(R.id.etSemestre);

        lineView = (LineView)view.findViewById(R.id.line_view);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setColorArray(new int[]{Color.parseColor("#424345"),Color.parseColor("#2dad58")});

        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);
    }

    private void getListCompte(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getEvolution(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseTransaction(response.body());
                    //setValue(response.body());

                } else {

                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Connexion impossibe au serveur")
                        .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    public void setValue(List<Compte> comptes) {

        for (int i = 0; i<comptes.size(); i++) {
            if (comptes.get(i).getCompte().equals("credit_virtuel")) {
                creditVirtules.add(Float.valueOf(comptes.get(i).getMontant()));
            } else if (comptes.get(i).getCompte().equals("commission")) {
                commisions.add(Float.valueOf(comptes.get(i).getMontant()));
            }
        }

        ArrayList<ArrayList<Float>> graphes = new ArrayList<>();
        graphes.add((ArrayList<Float>) commisions);
        graphes.add((ArrayList<Float>) creditVirtules);

        lineView.setFloatDataList(graphes);

    }

    private void getResponseTransaction(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        List<Compte> comptes = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Compte>>() {}.getType();

            assert outputPaginate != null;
            comptes = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);

            setValue(comptes);

            System.out.println("comptes: " + comptes.toString() + "\n" +
                    "commissions: " + commisions.toString() + "\n" +
                    "credit_virtuelle" + creditVirtules + "\n");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void makeSpinnerList() {

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner, listAnnee) {
            @Override
            public boolean isEnabled(int position){
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };

        final ArrayAdapter<String> sexeArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.item_spinner,listSemestre){
            @Override
            public boolean isEnabled(int position){
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        sexeArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        annee.setAdapter(spinnerArrayAdapter);
        semestre.setAdapter(sexeArrayAdapter);

        annee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position < parent.getCount() && position > 0){
                    // Notify the selected item text
                    String[] strings =  (selectedItemText.split("–"));

                    getListCompte(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken()
                    );

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void getValue() throws ParseException {

        String dateDebut = "2009-12-31 23:59:59";

        int dateTemp, dateActu;
        dateTemp = simpleDateFormat.parse(dateDebut).getYear() + 1900;
        dateActu = (new Date().getYear()) + 1900;
        date = simpleDateFormat.parse(dateDebut).getDay();
        String intervalle;

        //System.out.println((new Date().getYear()) + 1900);

        while (dateTemp + 1 <= dateActu) {
            Date date1 = dateFormat.parse(String.valueOf(dateTemp) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());
            Date date2 = dateFormat.parse(String.valueOf(dateTemp + 1) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay());

            System.out.println(date1 + " " + date2);

            intervalle = format.format(date1) + "  –   " + format.format(date2);
            listAnnee.add(intervalle);
            dateTemp = dateTemp + 1;
        }

        Date lasteDayofIntervalle = dateFormat.parse(String.valueOf(dateTemp) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                simpleDateFormat.parse(dateDebut).getDay());
        Date newDate = dateFormat.parse(dateFormat.format(new Date()));

        if (lasteDayofIntervalle.compareTo(newDate) < 0) {
            listAnnee.add(format.format(lasteDayofIntervalle) + "  –  " + format.format( dateFormat.parse(String.valueOf(dateTemp + 1) + "-" + simpleDateFormat.parse(dateDebut).getMonth() + "-" +
                    simpleDateFormat.parse(dateDebut).getDay())));
        }

        listSemestre.add(formatDate.format(lasteDayofIntervalle) + "  –  " + formatDate.format(addMonth(lasteDayofIntervalle, 6)));
        listSemestre.add(formatDate.format(addMonth(lasteDayofIntervalle, 6)) + "  –  " + formatDate.format(addMonth(lasteDayofIntervalle, 12)));


        Collections.reverse(listAnnee);

    }

    private static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }



}
