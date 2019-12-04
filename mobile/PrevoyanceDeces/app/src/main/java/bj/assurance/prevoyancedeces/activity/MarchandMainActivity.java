package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Notification;
import bj.assurance.prevoyancedeces.fragment.marchand.Accueil;
import bj.assurance.prevoyancedeces.fragment.marchand.ListeClients;
import bj.assurance.prevoyancedeces.fragment.marchand.Transactions;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;



public class MarchandMainActivity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    static TextView title;
    private ImageView alert;
    private static Contrat contrat = new Contrat();

    TextView nomPrenom, matricule, soldeActuelCreditVirtuel, soldeInitialeCreditVirtuel;

    static Marchand marchand ;
    static Utilisateur utilisateur;


    private static List<Commune> communes = new ArrayList<>();

    Gson gson = new Gson();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchand_main);

        try {
            init();
            setView();
            setClickListener();
        } catch (Exception e) {
        }

    }

    public void init() {
        title = findViewById(R.id.frame_title);
        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        alert = findViewById(R.id.alertIcon);
        nomPrenom = findViewById(R.id.nom_prenom_marchand);
        matricule = findViewById(R.id.matricule_marchand);
        soldeActuelCreditVirtuel = findViewById(R.id.credit_virtuelle_actuel);
        soldeInitialeCreditVirtuel = findViewById(R.id.credit_virtuel_depart);

        try {

            utilisateur =  gson.fromJson(getIntent().getExtras().getString("marchand", null), Utilisateur.class);
            marchand = gson.fromJson(gson.toJson(utilisateur.getObject()), Marchand.class);

        } catch (Exception ignored) { }

        /*findbyId(
                TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
        );
        contrat.setMarchand(marchand);*/

        try {
            getCommunesbyDepartement(
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
            );

            setView();

            title.setText("Salut "+ utilisateur.getPrenom());

        } catch (Exception e) {}

    }

    @SuppressLint("SetTextI18n")
    public void setValue() {
        nomPrenom.setText(utilisateur.getNom()+ " " + utilisateur.getPrenom());
        matricule.setText(marchand.getMatricule());
        soldeActuelCreditVirtuel.setText(marchand.getCreditVirtuel());


    }

    public void setClickListener(){
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationMItemClicked(i);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getFragmentManager().findFragmentByTag("NOTIFICATIONS") != null &&
                        getFragmentManager().findFragmentByTag("NOTIFICATIONS").isVisible())) {
                    replaceFragment(new Notification(), getResources().getString(R.string.notifications), "NOTIFICATIONS");
                }
            }
        });
    }

    public void setView() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main_marchand,new Accueil());
        fragmentTransaction.commit();
    }

    @SuppressLint("RestrictedApi")
    public void buttomNavigationMItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                if (!(getFragmentManager().findFragmentByTag("DISCUSSIONS") != null
                        && getFragmentManager().findFragmentByTag("DISCUSSIONS").isVisible())) {
                    replaceFragment(new Discussion(), getResources().getString(R.string.discussion), "DISCUSSIONS");
                }
                break;

            case R.id.bottom_nav_clients:
                if (!(getFragmentManager().findFragmentByTag("CLIENTS") != null
                        && getFragmentManager().findFragmentByTag("CLIENTS").isVisible())) {
                    replaceFragment(new ListeClients(), getResources().getString(R.string.mes_clients), "CLIENTS");
                }
                break;

            case R.id.bottom_nav_prospects:
                if (!(getFragmentManager().findFragmentByTag("TRANSACTIONS") != null
                        && getFragmentManager().findFragmentByTag("TRANSACTIONS").isVisible())) {
                    replaceFragment(new Transactions(), getResources().getString(R.string.transactions), "TRANSACTIONS");
                }
                break;

            case R.id.bottom_nav_accueil:
                if (!(getFragmentManager().findFragmentByTag("ACCUEIL") != null
                        && getFragmentManager().findFragmentByTag("ACCUEIL").isVisible())) {
                    replaceFragment(new Accueil(), "Salut " /* + utilisateur.getPrenom()*/, "ACCUEIL");
                }

        }
    }



    public void replaceFragment(Fragment fragment, String titre, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment, tag).commit();

        title.setText(titre);

    }


    /*
    public void floatingButtonaddProspect() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }*/

    public static Marchand getMarchand() {
        return marchand;
    }

    public static void setMarchand(Marchand marchand) {
        MarchandMainActivity.marchand = marchand;
    }


    public static TextView getTitleFrame() {
        return title;
    }

    public static void setTitle(TextView title) {
        MarchandMainActivity.title = title;
    }

    public static Contrat getContrat() {
        return contrat;
    }

    public static void setContrat(Contrat contrat) {
        MarchandMainActivity.contrat = contrat;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        MarchandMainActivity.utilisateur = utilisateur;
    }

    public static List<Commune> getCommunes() {
        return communes;
    }

    public static void setCommunes(List<Commune> communes) {
        MarchandMainActivity.communes = communes;
    }

    /***** chargement des ressources necessaires ******/

    // recuperer les informations du marchand connecté
    public void findbyId(AccessToken accessToken) {

        Call<Marchand> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.findById();
        call.enqueue(new Callback<Marchand>() {
            @Override
            public void onResponse(Call<Marchand> call, Response<Marchand> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    marchand = response.body();

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(MarchandMainActivity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Marchand> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(MarchandMainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void getCommunesbyDepartement(AccessToken accessToken) {

        Call<List<Commune>> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getCommunebyDepartement(MarchandMainActivity.getUtilisateur().getCommune().getDepartement().getId());
        call.enqueue(new Callback<List<Commune>>() {
            @Override
            public void onResponse(Call<List<Commune>> call, Response<List<Commune>> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    communes = response.body();

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        //Toast.makeText(Main2Activity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Commune>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}
