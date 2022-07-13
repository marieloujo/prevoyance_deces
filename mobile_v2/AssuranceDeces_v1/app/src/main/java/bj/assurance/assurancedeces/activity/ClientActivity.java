package bj.assurance.assurancedeces.activity;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.Notifications;
import bj.assurance.assurancedeces.fragment.client.Accueil;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.BottombarClickListener;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity {




    private BubbleTabBar bubbleTabBar;
    private ImageView alertIcon;


    private ShowDialog showDialog;


    @SuppressLint("StaticFieldLeak")
    private static TextView frameTitle;
    private static Client client;
    private static Utilisateur utilisateur;
    private static View isRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        init();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu_client, menu);
        return true;

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        new OptionMenuClickListener(ClientActivity.this)
                .clientOptionMenuItemClickListener(
                        item.getItemId()
                );

        return true;

    }








    private void init() {


        try {

            findView();
            initValue();
            setClicklistener();

        } catch (Exception e) {

            e.printStackTrace();

        }


    }




    private void findView() {


        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        frameTitle  =  findViewById(R.id.frame_title);
        alertIcon   =  findViewById(R.id.alertIcon);


        isRead = findViewById(R.id.isRead);

    }





    @SuppressLint("SetTextI18n")
    private void initValue() {


        try {

            Gson gson = new Gson();

            Userable userable =  gson.fromJson(getIntent().getExtras()
                    .getString("client", null), Userable.class);


            client = gson.fromJson(
                    gson.toJson(userable.getObject()), Client.class
            );

            utilisateur = gson.fromJson(
                    gson.toJson(userable.getUtilisateur()), Utilisateur.class
            );



        } catch (Exception e) {

            e.printStackTrace();

        }



        frameTitle.setText("Salut " + utilisateur.getPrenom());


        showDialog = new ShowDialog(ClientActivity.this);



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();



        unReadNotifications(utilisateur.getId());


    }






    private void setClicklistener() {


        alertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new Notifications(utilisateur.getId()));

            }
        });



        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                new BottombarClickListener(fragmentManager).clientBottombarItemClicked(i);
            }
        });


    }











    private void unReadNotifications(final Long id) {


        new UtilisateurServiceImplementation(ClientActivity.this)

                .unReadNotifications(id,

                        TokenManager.getInstance(
                                (ClientActivity.this).getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            try {


                                Type listType = new TypeToken<List<Notification>>() {}.getType();

                                List<Notification> notifications = new Gson().fromJson(response.body(), listType);

                                isRead.setVisibility(View.VISIBLE);

                            } catch (Exception e) {}


                        } else {

                            System.out.println("code" + response.code());

                            if (response.code() == 404) {

                                try {

                                    isRead.setVisibility(View.INVISIBLE);

                                } catch (Exception e) {}

                            } else {


                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());


                                    try {

                                        //showDialog.getAlertDialog().dismiss();

                                    } catch (Exception ignored) {
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    String string = new CatchApiError(response.code()).catchError(ClientActivity.this);


                                    System.out.println("stringno" + string);

                                }
                            }

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {


                        try {

                            showDialog.showNoInternetDialog()
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                            unReadNotifications(id);
                                        }
                                    })

                                    .setNegativeListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })

                                    .setNegativeText("Annuler")
                                    .setNegativeButtonColor(Color.parseColor("#e57373"))
                                    .setNegativeTextColor(Color.parseColor("#ffffffff"))

                                    .build()
                                    .show();

                        } catch (Exception ignored) {}


                    }


                });


    }












    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = (ClientActivity.this).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        frameTitle.setText("Notifications");

    }






    public static TextView getFrameTitle() {
        return frameTitle;
    }

    public static void setFrameTitle(TextView frameTitle) {
        ClientActivity.frameTitle = frameTitle;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        ClientActivity.client = client;
    }


    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        ClientActivity.utilisateur = utilisateur;
    }


    public static View getIsRead() {
        return isRead;
    }
}
