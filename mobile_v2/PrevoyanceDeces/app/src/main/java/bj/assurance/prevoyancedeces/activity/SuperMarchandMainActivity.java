package bj.assurance.prevoyancedeces.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.Boutique;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonProfile;
import bj.assurance.prevoyancedeces.fragment.client.Notification;
import bj.assurance.prevoyancedeces.fragment.supermachand.Accueil;
import bj.assurance.prevoyancedeces.fragment.supermachand.Historique;
import bj.assurance.prevoyancedeces.fragment.supermachand.MesMarchand;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.SuperMarchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperMarchandMainActivity extends AppCompatActivity {

    private BubbleTabBar bubbleTabBar;
    private static TextView title;
    private ImageView alert;


    private static   Utilisateur utilisateur;
    private static SuperMarchand superMarchand;
    private Gson gson = new Gson();
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarchand_main_activity);

        init();
        setView();
        setClickListener();
        binding();

    }

    public void init() {

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        bubbleTabBar = findViewById(R.id.bubbleTabBarSm);
        title = findViewById(R.id.frame_title);
        alert = findViewById(R.id.alertIcon);



        try {

            utilisateur =  gson.fromJson(getIntent().getExtras().getString("supermarchand", null),
                    Utilisateur.class);
            superMarchand = gson.fromJson(gson.toJson(utilisateur.getObject()), SuperMarchand.class);
            superMarchand.setUtilisateur(utilisateur);

            System.out.println("\n user: \n "+ superMarchand.toString() + "\n supermarchand" + superMarchand.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void binding() {
        title.setText("Salut " + utilisateur.getPrenom());
    }

    public void setView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();
    }

    public void setClickListener(){
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationSmItemClicked(i);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Notification(SuperMarchandMainActivity.getUtilisateur().getId()), getResources().getString(R.string.notifications));
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void buttomNavigationSmItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(getSuperMarchand().getId()), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_marchands:
                replaceFragment(new MesMarchand(), getResources().getString(R.string.mes_marchands));
                break;

            case R.id.bottom_nav_profil_supermarchand:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Historique(), getResources().getString(R.string.mon_profil));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Accueil(), "Salut " + utilisateur.getPrenom());

        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_supermarchand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.super_marchand_option_nav_discussion:
                replaceFragment(new Discussion(utilisateur.getId()), getResources().getString(R.string.discussion));
                return true;

            case R.id.super_marchand_option_nav_profil:
                replaceFragment(new Historique(), getResources().getString(R.string.mon_profil));
                return true;

            case R.id.super_marchand_option_nav_marchands:
                replaceFragment(new MesMarchand(), getResources().getString(R.string.mes_marchands));
                return true;

            case R.id.super_marchand_option_nav_accueil:
                replaceFragment(new Accueil(), "Salut " + utilisateur.getPrenom());
                return true;

            case R.id.super_marchand_option_nav_logpout:
                lougout(
                        TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
                );
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void lougout(AccessToken accessToken) {

        KAlertDialog pDialog = new KAlertDialog(SuperMarchandMainActivity.this, KAlertDialog.PROGRESS_TYPE);
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
                //Log.w(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).deleteToken();
                    Intent intent = new Intent(SuperMarchandMainActivity.this, Connexion.class);
                    startActivity(intent);
                } else {
                    //Log.w(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }



    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        title.setText(titre);

    }

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public static SuperMarchand getSuperMarchand() {
        return superMarchand;
    }

    public void setSuperMarchand(SuperMarchand superMarchand) {
        this.superMarchand = superMarchand;
    }

    public static TextView getTitleFrame() {
        return title;
    }

    public static void setTitle(TextView title) {
        SuperMarchandMainActivity.title = title;
    }
}
