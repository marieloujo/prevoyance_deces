package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.BottomSheet.ListeTranfertBottomSheet;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.Boutique;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonProfile;
import bj.assurance.prevoyancedeces.fragment.marchand.AddClientStepOne;
import bj.assurance.prevoyancedeces.fragment.marchand.AddClientStepThree;
import bj.assurance.prevoyancedeces.fragment.marchand.AddProspect;
import bj.assurance.prevoyancedeces.fragment.marchand.Historique;
import bj.assurance.prevoyancedeces.fragment.marchand.ProfileMarchand;
import bj.assurance.prevoyancedeces.model.Departement;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitClientInstance;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.PhoneList;
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
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static bj.assurance.prevoyancedeces.R.id.marchand_option_nav_add_contrat;


public class MarchandMainActivity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    static TextView title;
    private ImageView alert;
    private static Contrat contrat = new Contrat();

    TextView nomPrenom, matricule, soldeActuelCreditVirtuel, soldeInitialeCreditVirtuel;

    static Marchand marchand ;
    static Utilisateur utilisateur;

    androidx.appcompat.widget.Toolbar toolbar;

    private static List<PhoneList> phoneLists;


    private static List<Commune> communes = new ArrayList<>();
    private static List<Departement> departements = new ArrayList<>();

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

            getPhoneList(
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    public void init() {
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
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
            marchand.setUtilisateur(utilisateur);
            contrat.setMarchand(marchand);

        } catch (Exception ignored) { }

            getCommunes(
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

            getDepartements(
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken());

            setView();

            title.setText("Salut "+ utilisateur.getPrenom());

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
                    replaceFragment(new Notification(MarchandMainActivity.getUtilisateur().getId()), getResources().getString(R.string.notifications), "NOTIFICATIONS");
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
                    replaceFragment(new ProfileMarchand(), getResources().getString(R.string.mon_profil), "DISCUSSIONS");
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
                    replaceFragment(new Accueil(), "Salut "  + utilisateur.getPrenom(), "ACCUEIL");
                }
        }
    }


    public void replaceFragment(Fragment fragment, String titre, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment, tag).commit();

        title.setText(titre);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_marchand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.marchand_option_nav_clients:
                if (!(getFragmentManager().findFragmentByTag("CLIENTS") != null
                        && getFragmentManager().findFragmentByTag("CLIENTS").isVisible())) {
                    replaceFragment(new ListeClients(), getResources().getString(R.string.mes_clients), "CLIENTS");
                }
                break;

            case R.id.marchand_option_nav_transactions:
                if (!(getFragmentManager().findFragmentByTag("TRANSACTIONS") != null
                        && getFragmentManager().findFragmentByTag("TRANSACTIONS").isVisible())) {
                    replaceFragment(new Transactions(), getResources().getString(R.string.mon_profil), "TRANSACTIONS");
                }
                break;

            case R.id.marchand_option_nav_accueil:
                if (!(getFragmentManager().findFragmentByTag("ACCUEIL") != null
                        && getFragmentManager().findFragmentByTag("ACCUEIL").isVisible())) {
                    replaceFragment(new Accueil(), "Salut "  + utilisateur.getPrenom(), "ACCUEIL");
                }
                break;

            case marchand_option_nav_add_contrat:
                if (!(getFragmentManager().findFragmentByTag("ADD_CONTRATS") != null
                        && getFragmentManager().findFragmentByTag("ADD_CONTRATS").isVisible())) {
                    replaceFragment(new AddClientStepOne(), "Enregistrement", "ADD_CONTRATS");
                }
                break;

            case R.id.marchand_option_nav_contrat_en_attente:
                if (!(getFragmentManager().findFragmentByTag("CONTRAT_ATTENTE") != null
                        && getFragmentManager().findFragmentByTag("CONTRAT_ATTENTE").isVisible())) {
                    //replaceFragment(new ListeClients(), getResources().getString(R.string.mes_clients), "CONTRAT_ATTENTE");
                }
                break;

            case R.id.marchand_option_nav_historique:
                if (!(getFragmentManager().findFragmentByTag("HISTORIQUE") != null
                        && getFragmentManager().findFragmentByTag("HISTORIQUE").isVisible())) {
                    replaceFragment(new Historique(), getResources().getString(R.string.transactions), "HISTORIQUE");
                }
                break;

            case R.id.marchand_option_nav_profil:
                if (!(getFragmentManager().findFragmentByTag("PROFIL") != null
                        && getFragmentManager().findFragmentByTag("PROFIL").isVisible())) {
                    replaceFragment(new ProfileMarchand(), getResources().getString(R.string.mon_profil), "PROFIL");
                }
                break;

            case R.id.marchand_option_nav_transfert:
                if (!(getFragmentManager().findFragmentByTag("DISCUSSIONS") != null
                        && getFragmentManager().findFragmentByTag("DISCUSSIONS").isVisible())) {

                    BottomSheet bottomSheet = new ListeTranfertBottomSheet(MarchandMainActivity.this);
                    bottomSheet.show();

                }
                break;

            case R.id.option_nav_logout:
               lougout(
                       TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
               );
                break;

            case R.id.marchand_option_nav_add_prospect:
                if (!(getFragmentManager().findFragmentByTag("ADD_PROSPECT") != null
                        && getFragmentManager().findFragmentByTag("ADD_PROSPECT").isVisible())) {
                    replaceFragment(new AddProspect(), "Ajouter un prospect", "ADD_PROSPECT");
                }

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void lougout(AccessToken accessToken) {

        KAlertDialog pDialog = new KAlertDialog(MarchandMainActivity.this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#2d8df8"));
        pDialog.setTitleText("Déconnexion");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.logout();
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                pDialog.dismiss();
                Log.w(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).deleteToken();
                    Intent intent = new Intent(MarchandMainActivity.this, Connexion.class);
                    startActivity(intent);
                } else {
                    Log.w(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }




    private void getPhoneList(AccessToken accessToken) {

        Call<List<PhoneList>> call;
        UserService userService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);

        call = userService.getListValidPhone();

        call.enqueue(new Callback<List<PhoneList>>() {

            @Override
            public void onResponse(Call<List<PhoneList>> call, Response<List<PhoneList>> response) {


                if (response.isSuccessful()) {


                    phoneLists = response.body();
                    System.out.println(phoneLists);

                }  else {

                }

            }

            @Override
            public void onFailure(Call<List<PhoneList>> call, Throwable t) {

            }


        });


    }






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

    public static List<Departement> getDepartements() {
        return departements;
    }

    public static void setDepartements(List<Departement> departements) {
        MarchandMainActivity.departements = departements;
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

    private void getCommunes(AccessToken accessToken) {
        Call<JsonObject> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.getCommunes(MarchandMainActivity.getUtilisateur().getCommune().getDepartement().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    getResponseCommunes(response.body());

                } else {
                    Log.w(ContentValues.TAG, "onFailure: " + response.code() + "idDepartement: " +
                            Main2Activity.getUtilisateur().getCommune().getDepartement().getId());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(ContentValues.TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getResponseCommunes(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        } catch (Exception ignored) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Commune>>() {}.getType();

            assert outputPaginate != null;
            string = gson.toJson(outputPaginate.getObjects());
            communes = gson.fromJson(string, listType);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDepartements(AccessToken accessToken) {
        Call<JsonObject> call;
        UserService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(UserService.class);
        call = service.getDepartement();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    responseDepartement(response.body());
                } else {
                    Log.w(TAG, "onError: " + response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void responseDepartement(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        } catch (Exception ignored) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Departement>>() {}.getType();

            assert outputPaginate != null;
            departements = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            System.out.println(departements.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<PhoneList> getPhoneLists() {
        return phoneLists;
    }

    public static void setPhoneLists(List<PhoneList> phoneLists) {
        MarchandMainActivity.phoneLists = phoneLists;
    }
}
