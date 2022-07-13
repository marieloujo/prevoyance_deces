package bj.assurance.prevoyancedeces.fragment.supermachand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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
import com.tsongkha.spinnerdatepicker.DateUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.activity.SuperMarchandMainActivity;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.SuperMarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import im.dacer.androidcharts.LineView;
import ir.farshid_roohi.linegraph.ChartEntity;
import ir.farshid_roohi.linegraph.LineChart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class Historique extends Fragment {

    int randomint = 9;

    LineView lineView;

    private List<Compte> commisions = new ArrayList<>();
    private TextView soldeCommisssions;
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

        View view = inflater.inflate(R.layout.fragment_historique_supermarchand, container, false);

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

    @SuppressLint("SetTextI18n")
    public void init(View view) {
        annee = view.findViewById(R.id.etAnnee);
        semestre = view.findViewById(R.id.etSemestre);
        soldeCommisssions = view.findViewById(R.id.solde_commisssions);

        lineView = (LineView)view.findViewById(R.id.line_view);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setColorArray(new int[]{Color.parseColor("#424345")});

        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);

        soldeCommisssions.setText(SuperMarchandMainActivity.getSuperMarchand().getCommission() + " coins");
    }

    private void getListCompte(AccessToken accessToken) {
        Call<JsonObject> call;
        SuperMarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(SuperMarchandService.class);
        call = service.getEvolutionsCommission(SuperMarchandMainActivity.getSuperMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    getResponseTransaction(response.body());
                    //setValue(response.body());

                } else {
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getListComptebyDate(AccessToken accessToken, Date date) {
        Call<JsonObject> call;
        SuperMarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(SuperMarchandService.class);
        call = service.getEvolutionsCommissionbyDate(SuperMarchandMainActivity.getSuperMarchand().getId(), date);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    getResponseTransaction(response.body());
                    //setValue(response.body());
                } else {
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getResponseTransaction(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

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
            commisions = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);

            System.out.println("commissions: " + commisions.toString() + "\n" );

            makeGraphe();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeGraphe() {
        ArrayList<ArrayList<Float>> graphes = new ArrayList<>();
        ArrayList<Float> commissionFloat = new ArrayList<>();

        for (Compte compte : commisions) {
            commissionFloat.add(Float.valueOf(compte.getMontant()));
        }

        graphes.add(commissionFloat);

        lineView.setFloatDataList(graphes);
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

                    getListComptebyDate(
                            TokenManager.getInstance(getActivity().
                                    getSharedPreferences("prefs", MODE_PRIVATE)).
                                    getToken(), new Date()
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


