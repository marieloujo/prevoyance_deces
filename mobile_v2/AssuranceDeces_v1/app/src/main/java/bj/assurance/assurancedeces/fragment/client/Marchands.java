package bj.assurance.assurancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.fragment.CatchError;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.Expandable;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListClientExpandableAdapter;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListProspetsAdapter;
import bj.assurance.assurancedeces.serviceImplementation.DepartementServiceImplementation;
import bj.assurance.assurancedeces.utils.json.ReadExternalJson;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Marchands extends Fragment {




    private Spinner etDepartement;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<Commune> communeList = new ArrayList<>();
    private List<ParentObject> parentObjects = new ArrayList<>();


    public Marchands() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_marchands, container, false);


        init(view);

        return view;
    }







    private void init(View view) {


        findView(view);
        initValue();


    }




    private void findView(View view) {


        etDepartement = view.findViewById(R.id.etDepartement);
        recyclerView = view.findViewById(R.id.recycler);


        progressBar = view.findViewById(R.id.scroll_progress);


    }





    @SuppressLint("WrongConstant")
    private void initValue() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        try {

            getListDepartement();

        } catch (Exception e) {

            e.printStackTrace();

        }


        /*try {
            getCommuneofDepartement(ClientActivity.getUtilisateur().getCommune().getDepartement().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }











    private void getListDepartement() throws JSONException {



        String string = new ReadExternalJson().getJSONData(
                getContext(), "departements.json");

        System.out.println( string);

        Type listType = new TypeToken<List<Departement>>() {}.getType();
        JSONObject jsonObject = new JSONObject(string);


        List<Departement> departements = new Gson().fromJson(
                jsonObject.getString("data"), listType);



        System.out.println(departements);




        final ArrayAdapter<Departement> spinnerArrayAdapter = new ArrayAdapter<Departement>(
                getContext(),R.layout.item_spinner, departements) {

            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent)

            {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };



        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        etDepartement.setAdapter(spinnerArrayAdapter);



        for (int i = 0; i < departements.size(); i++) {


            if (departements.get(i).getId().equals(ClientActivity.getUtilisateur().getCommune().getDepartement().getId())) {

                etDepartement.setSelection(i);

            }

        }




        etDepartement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {

                    Departement departement = (Departement) adapterView.getItemAtPosition(i);

                    System.out.println(departement);

                    List<ParentObject> parentObjects = new ArrayList<>();

                    ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), parentObjects);
                    recyclerView.setAdapter(listProspetsAdapter);


                    try {
                        getCommuneofDepartement(departement.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }









    private void getCommuneofDepartement(final Long id) throws JSONException {


        System.out.println(id);


        String string = new ReadExternalJson().getJSONData(
                getContext(), "communes.json");

        Type listType = new TypeToken<List<Commune>>() {}.getType();
        JSONObject jsonObject = new JSONObject(string);


        List<Commune> communes = new Gson().fromJson(
                jsonObject.getString("data"), listType);



        communeList = new ArrayList<>();
        for (int i = 0; i < communes.size(); i++) {

            if (communes.get(i).getDepartement().getId().equals(id)) {


                this.communeList.add(communes.get(i));
            }

        }


        System.out.println(communeList);


        getMarchandsofCommune();


    }









    private void getMarchandsofCommune() {



        if (communeList.size() > 0) {

            parentObjects.clear();

            getListeMarchandofCommune(0);

        }




    }






    private void getListeMarchandofCommune(final int position) {

        progressBar.setVisibility(View.VISIBLE);



        new DepartementServiceImplementation(

                TokenManager.getInstance(getContext().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).listMarchandByCommuneOfDepartement(communeList.get(position).getDepartement().getId(),
                communeList.get(position).getId())
                .enqueue(new Callback<JsonObject>() {


                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            try {

                                System.out.println(response.body());

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Marchand>>() {}.getType();


                                OutputPaginate outputPaginate = new Gson().fromJson(
                                        response.body(), OutputPaginate.class);


                                List<Marchand> marchands = gson.fromJson(

                                        gson.toJson(outputPaginate.getObjects()),

                                        listType
                                );


                                parentObjects.add( new Expandable(communeList.get(position).getNom(), getUsers(marchands)));


                                int j = position;
                                System.out.println(j);
                                if (j ++ < communeList.size()) {

                                    if (j == communeList.size()) {

                                        System.out.println("fin \n" + parentObjects);

                                        ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), parentObjects);
                                        recyclerView.setAdapter(listProspetsAdapter);

                                        progressBar.setVisibility(View.INVISIBLE);

                                    }


                                    getListeMarchandofCommune(j);

                                }


                                System.out.println(communeList);

                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }

                        } else {


                            try {


                                if (response.code() == 404) {

                                    parentObjects.add( new Expandable(communeList.get(position).getNom(), null));



                                    int j = position;
                                    System.out.println(j);
                                    if (j ++ < communeList.size()) {

                                        if (j == communeList.size()) {

                                            progressBar.setVisibility(View.INVISIBLE);

                                            ListProspetsAdapter listProspetsAdapter = new ListProspetsAdapter( getContext(), parentObjects);
                                            recyclerView.setAdapter(listProspetsAdapter);


                                        }


                                        getListeMarchandofCommune(j);

                                    }


                                }

                            } catch (Exception e) {

                                progressBar.setVisibility(View.INVISIBLE);

                                e.printStackTrace();

                            }


                        }

                    }





                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            progressBar.setVisibility(View.INVISIBLE);

                            if (new onConnexionListener(getContext()).checkConnexion()) {

                                replaceFragment(new CatchError("Serveur en maintenance veuillez patatientez ..",
                                        "Reporter", false));

                            } else

                                replaceFragment(new CatchError("Aucune connexion internet." +
                                        " Vous naviguez en mode non connecté, vérifier votre connexion internet et réessayer",
                                        "Reéssayer", true));


                        } catch (Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();

                        }

                    }


                });






    }








    private List<Object> getUsers(List<Marchand> marchands) {

        List<Object> utilisateurs = new ArrayList<>();


        for (int i = 0; i < marchands.size(); i ++ ) {


            utilisateurs.add(marchands.get(i).getUserable().getUtilisateur());


        }

        return utilisateurs;


    }











    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


    }







}
