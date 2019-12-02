package bj.assurance.prevoyancedeces.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;
import com.kinda.alert.KAlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.fragment.Boutique;
import bj.assurance.prevoyancedeces.fragment.client.Accueil;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonProfile;
import bj.assurance.prevoyancedeces.fragment.client.Notification;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.UserService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";


    BubbleTabBar bubbleTabBar;
    FragmentTransaction fragmentTransaction;
    @SuppressLint("StaticFieldLeak")
    static TextView title,backTitle;
    ImageView alert;
    static Client  client ;
    static Utilisateur utilisateur ;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
        setOnclickListner();

    }

    @SuppressLint("WrongConstant")
    public void init() {

        title = findViewById(R.id.frame_title);
        backTitle = findViewById(R.id.back_frame);
        alert = findViewById(R.id.alertIcon);
        bubbleTabBar = findViewById(R.id.bubbleTabBar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        try {

            utilisateur =  gson.fromJson(getIntent().getExtras().getString("client", null), Utilisateur.class);
            client = gson.fromJson(gson.toJson(utilisateur.getObject()), Client.class);

        } catch (Exception e) {

        }

        setView();

        title.setText("Bonjour "+ utilisateur.getPrenom());

    }

    public void setView() {
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();
        bubbleTabBar.setVisibility(View.VISIBLE);
    }

    public void setOnclickListner() {
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationItemClicked(i);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Notification(), getResources().getString(R.string.notifications));
            }
        });
    }

    public void findbyId(AccessToken accessToken) {

        Call<Client> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.findbyId(utilisateur.getUsereableId());
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    client = response.body();

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(Main2Activity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    public void buttomNavigationItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_carnet:
                replaceFragment(new MonProfile(), getResources().getString(R.string.mon_profil));
                break;

            case R.id.bottom_nav_marchands:
                replaceFragment(new Marchands(), getResources().getString(R.string.marchands));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new Accueil(), getResources().getString(R.string.bonjour_joan));

            case R.id.bottom_nav_boutique:
                replaceFragment(new Boutique(), getResources().getString(R.string.boutique_virtuelle));

        }
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        title.setText(titre);

    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        String url = "https://play.google.com/store/apps/details";

        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public static TextView getTextTitle() {
        return title;
    }

    public static void setTitle(TextView title) {
        Main2Activity.title = title;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Main2Activity.client = client;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        Main2Activity.utilisateur = utilisateur;
    }
}
