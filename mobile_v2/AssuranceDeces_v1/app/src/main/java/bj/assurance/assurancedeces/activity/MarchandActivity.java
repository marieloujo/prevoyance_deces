package bj.assurance.assurancedeces.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.BottomSheet.ActionBottomDialogFragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.Notifications;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.MesClients;
import bj.assurance.assurancedeces.fragment.marchand.MonProfil;
import bj.assurance.assurancedeces.fragment.marchand.Transactions;
import bj.assurance.assurancedeces.model.Benefice;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.BottombarClickListener;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.NotificationReader;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




@SuppressLint("Registered")
public class MarchandActivity extends AppCompatActivity {


    private BubbleTabBar bubbleTabBar;
    private ImageView alertIcon;


    private NotificationReader notificationReader;


    private static View isRead;


    @SuppressLint("StaticFieldLeak")
    private static TextView frameTitle;

    private static Contrat contrat;
    private static Marchand marchand;
    private static Utilisateur utilisateur;
    private static Userable userable;
    private static Menu menus;






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

            Gson gson = new Gson();

            userable =  gson.fromJson(Objects.requireNonNull(getIntent().getExtras())
                    .getString("marchand", null), Userable.class);


            System.out.println(userable);


            marchand = gson.fromJson(
                    gson.toJson(userable.getObject()), Marchand.class
            );

            marchand.setUserable(userable);

            utilisateur = gson.fromJson(
                    gson.toJson(userable.getUtilisateur()), Utilisateur.class
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

        notificationReader = new NotificationReader(this);



        frameTitle.setText("Salut " + utilisateur.getPrenom());



        contrat = new Contrat();
        contrat.setMarchand(marchand);
        contrat.setId(0L);


        List<Benefice> benefices = new ArrayList<>();
        contrat.setBenefices(benefices);


        System.out.println(new Gson().toJson(contrat));


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();


        notificationReader.unReadNotifications(utilisateur.getId());


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

                replaceFragment(new Notifications(utilisateur.getId()), "Notifications");

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









/*

*/



    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment, String title) {


        FragmentManager fragmentManager = (MarchandActivity.this).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        frameTitle.setText(title);

    }
















    @Override
    public void onBackPressed() {
        tellFragments();
    }





    private void tellFragments(){


        List<Fragment> fragments = getSupportFragmentManager().getFragments();


        for(Fragment f : fragments){


            if(f != null) {

                 /*if ( f instanceof MesClients) {

                     ((MesClients) f).doBack();
                     return;

                 }
*/

                if ( f instanceof Accueil) {

                    ((Accueil) f).doBack();
                    return;

                }


                replaceFragment(new Accueil(), "Salut " + MarchandActivity.getUtilisateur().getPrenom());

                /*if ( f instanceof MonProfil) {

                    ((MonProfil) f).doBack();
                    return;

                }


                if ( f instanceof Transactions) {

                    ((Transactions) f).doBack();
                    return;

                }*/


            }

        }


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




    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {

        MarchandActivity.utilisateur = utilisateur;
    }


    public static Userable getUserable() {
        return userable;
    }

    public static void setUserable(Userable userable) {
        MarchandActivity.userable = userable;
    }


    public static Menu getMenu() {
        return menus;
    }




}
