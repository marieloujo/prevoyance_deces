package bj.assurance.assurancedeces.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.Notifications;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.Historique;
import bj.assurance.assurancedeces.model.Benefice;
import bj.assurance.assurancedeces.model.Commune;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Departement;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.PhoneList;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.model.pagination.OutputPaginate;
import bj.assurance.assurancedeces.recyclerViewAdapter.NotificationAdapter;
import bj.assurance.assurancedeces.serviceImplementation.DepartementServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.PhoneServiveImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.BottombarClickListener;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MarchandActivity extends AppCompatActivity {


    private BubbleTabBar bubbleTabBar;
    private ImageView alertIcon;


    private static View isRead;


    @SuppressLint("StaticFieldLeak")
    private static TextView frameTitle;

    private static Contrat contrat;
    private static Marchand marchand;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchand);


        init();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu_marchand, menu);
        return true;

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        new OptionMenuClickListener(MarchandActivity.this)
                .marchandOptionMenuItemClickListener(
                        item.getItemId()
                );

        return true;

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        findView();
        initValue();
        setClicklistener();

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void initValue() {

        try {

            marchand =  new Gson().fromJson(Objects.requireNonNull(getIntent().getExtras())
                    .getString("marchand", null), Marchand.class);


        } catch (Exception e) {

            e.printStackTrace();

        }



        frameTitle.setText("Salut " + marchand.getUtilisateur().getPrenom());


        contrat = new Contrat();
        contrat.setMarchand(marchand);
        contrat.setId(0L);

        List<Benefice> benefices = new ArrayList<>();
        contrat.setBenefices(benefices);



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();



        unReadNotifications(marchand.getUtilisateur().getId());


        /*getCommuneofMarchand(marchand.getUtilisateur().getCommune().getDepartement().getId());


        getPhoneNumberPrefixList();


        getListDepartement();*/


    }



    private void findView() {


        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        frameTitle  =  findViewById(R.id.frame_title);
        alertIcon   =  findViewById(R.id.alertIcon);


        isRead = findViewById(R.id.isRead);


    }



    private void setClicklistener() {


        alertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new Notifications(), "Notifications");

            }
        });



        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                new BottombarClickListener(fragmentManager).marchanBottombarItemClicked(i);

            }
        });


    }





    private void unReadNotifications(Long id) {


        new UtilisateurServiceImplementation(MarchandActivity.this)

            .unReadNotifications(id,

                    TokenManager.getInstance(
                            ((FragmentActivity)MarchandActivity.this).getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken()

            )

                .enqueue(new Callback<JsonArray>() {


                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                        if (response.isSuccessful()) {

                            Type listType = new TypeToken<List<Notification>>() {}.getType();

                            List<Notification> notifications = new Gson().fromJson(response.body(), listType);

                            isRead.setVisibility(View.VISIBLE);


                            System.out.println("notifications "+notifications);


                        } else {

                            System.out.println("code" + response.code());

                            if (response.code() == 404) {

                                isRead.setVisibility(View.INVISIBLE);

                            } else {


                                try {

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());


                                    try {

                                        //showDialog.getAlertDialog().dismiss();

                                    } catch (Exception ignored) {
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();

                                    String string = new CatchApiError(response.code()).catchError(MarchandActivity.this);


                                    System.out.println("stringno" + string);

                                }
                            }

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {


                        try {

                            /*showDialog.showNoInternetDialog()
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
                                    .show();*/

                        } catch (Exception ignored) {}


                    }


                });


    }



/*

*/



    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = (MarchandActivity.this).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        frameTitle.setText(title);

    }














    public static View getIsRead() {
        return isRead;
    }

    public static void setIsRead(View isRead) {
        MarchandActivity.isRead = isRead;
    }

    public static TextView getFrameTitle() {
        return frameTitle;
    }



    public static void setFrameTitle(TextView frameTitle) {
        MarchandActivity.frameTitle = frameTitle;
    }



    public static Marchand getMarchand() {
        return marchand;
    }



    public static void setMarchand(Marchand marchand) {
        MarchandActivity.marchand = marchand;
    }




    public static Contrat getContrat() {
        return contrat;
    }



    public static void setContrat(Contrat contrat) {
        MarchandActivity.contrat = contrat;
    }



}
