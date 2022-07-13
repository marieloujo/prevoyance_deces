package bj.assurance.assurancedeces.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.HistoriqueSearch;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.ListeClientAdapter;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.CustomJsonObject;
import bj.assurance.assurancedeces.utils.GetResponseObject;
import bj.assurance.assurancedeces.utils.onConnexionListener;
import bj.assurance.assurancedeces.utils.sqliteDbHelper.DBHelper;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{



    private static SearchView searchView;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;

    private DBHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        init();
    }




    private void init() {

        findView();
        initValue();
    }



    private void findView() {

        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler);

        linearLayout = findViewById(R.id.load_search);
    }




    @SuppressLint("WrongConstant")
    private void initValue() {

        dbHelper = new DBHelper(SearchActivity.this);

        searchView.setQueryHint("Rechercher");
        searchView.setOnQueryTextListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        if (!dbHelper.getHistoriqueSearch().isEmpty()) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new HistoriqueSearch()).commit();

        }



    }





    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }




    @SuppressLint("SetTextI18n")
    @Override
    public boolean onQueryTextChange(String newText) {


        linearLayout.setVisibility(View.VISIBLE);
        ( linearLayout.findViewById(R.id.loader)).setVisibility(View.VISIBLE);
        ((TextView) linearLayout.findViewById(R.id.enties_texte)).setText("Recherche de " + newText);


        findUserByNom(newText);


        return false;
    }




    private void findUserByNom(final String name) {


        new UtilisateurServiceImplementation(SearchActivity.this)

            .findUserbyName(name,
                    TokenManager.getInstance(SearchActivity.this.
                            getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken()
            )

                .enqueue(new Callback<JsonObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            System.out.println(response.body());

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Client>>() {}.getType();


                            OutputPaginate outputPaginate = new Gson().fromJson(
                                    response.body(), OutputPaginate.class);


                            List<Client> clients = gson.fromJson(

                                    gson.toJson(outputPaginate.getObjects()),

                                    listType

                            );
                            linearLayout.setVisibility(View.INVISIBLE);

                            ListeClientAdapter listeClientAdapter = new ListeClientAdapter(clients, SearchActivity.this);
                            recyclerView.setAdapter(listeClientAdapter);

                            dbHelper.insertSearch(name);


                        } else {

                            if (response.code() == 404)

                                ((TextView) linearLayout.findViewById(R.id.enties_texte)).setText("Aucun résultat");
                                ( linearLayout.findViewById(R.id.loader)).setVisibility(View.INVISIBLE);


                        }


                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        System.out.println(t.getMessage());

                        if (t.getMessage().equals("timeout")) {

                            ((TextView) linearLayout.findViewById(R.id.enties_texte)).setText("Temps écroulé, veuillez réessayer");
                            ( linearLayout.findViewById(R.id.loader)).setVisibility(View.INVISIBLE);

                        } else {

                            if (new onConnexionListener(SearchActivity.this).checkConnexion()) {

                                ((TextView) linearLayout.findViewById(R.id.enties_texte)).setText("Echec de connexion au serveur");
                                (linearLayout.findViewById(R.id.loader)).setVisibility(View.INVISIBLE);

                            } else {

                                ((TextView) linearLayout.findViewById(R.id.enties_texte)).setText("Connexion perdue, vérifier votre connexion internet");
                                ( linearLayout.findViewById(R.id.loader)).setVisibility(View.INVISIBLE);

                            }

                        }

                    }
                });



    }




    public void deleteAll(View view) {

        dbHelper.deleteHistorique();


    }


    public static SearchView getSearchView() {
        return searchView;
    }
}
