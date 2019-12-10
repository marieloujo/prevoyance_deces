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

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<Float> commisions = new ArrayList<>();
    private TextView soldeCommisssions;
    private Spinner annee, semestre;
    private List<String> listAnnee = new ArrayList<>();
    private List<String> listSemestre = new ArrayList<>();

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
        getValue();
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
        lineView.setColorArray(new int[]{Color.parseColor("#424345"),Color.parseColor("#2dad58")});

        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        lineView.setBottomTextList(test);

        soldeCommisssions.setText(SuperMarchandMainActivity.getSuperMarchand().getCommission() + " fcfa");
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
                    System.out.println(response.body());
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
                getContext(),R.layout.item_spinner,getResources().getStringArray(R.array.semestre)){
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

    }

    private void getValue() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        int dateDebut, dateActu;
        dateDebut = (SuperMarchandMainActivity.getUtilisateur().getDateCreation().getYear()) + 1900;
        dateActu = new Date().getYear() + 1900;

        while (dateDebut + 1 <= dateActu) {
            listAnnee.add(String.valueOf(dateDebut));
        }
        listAnnee.add(String.valueOf(dateActu));

       // Date date = simpleDateFormat.parse(SuperMarchandMainActivity.getSuperMarchand().getUtilisateur().getDateCreation());


    }

}


