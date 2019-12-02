package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Notification;
import bj.assurance.prevoyancedeces.fragment.marchand.Accueil;
import bj.assurance.prevoyancedeces.fragment.marchand.ListeClients;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
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

import static androidx.constraintlayout.widget.Constraints.TAG;



public class MarchandMainActivity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    static TextView title;
    private ImageView alert;
    private static Contrat contrat = new Contrat();

    TextView nomPrenom, matricule, soldeActuelCreditVirtuel, soldeInitialeCreditVirtuel;

    static Marchand marchand ;
    Utilisateur utilisateur;

    Gson gson = new Gson();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchand_main);

        init();
        setView();
        setClickListener();

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


        setView();

        title.setText("Bonjour "+ utilisateur.getPrenom());
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
                replaceFragment(new Notification(), getResources().getString(R.string.notifications));
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
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_clients:
                replaceFragment(new ListeClients(), getResources().getString(R.string.mes_clients));
                break;

            case R.id.bottom_nav_prospects:
                replaceFragment(new ListeClients(), getResources().getString(R.string.mes_prospects));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new Accueil(), getResources().getString(R.string.bonjour_joan));

        }
    }



    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

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

    public void findbyId(AccessToken accessToken) {

        Call<Marchand> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.findById(utilisateur.getUsereableId());
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

}
