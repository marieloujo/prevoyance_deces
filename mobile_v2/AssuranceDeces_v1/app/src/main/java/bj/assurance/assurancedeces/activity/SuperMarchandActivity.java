package bj.assurance.assurancedeces.activity;

import android.annotation.SuppressLint;
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
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.BottombarClickListener;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SuperMarchandActivity extends AppCompatActivity {



    private BubbleTabBar bubbleTabBar;
    private ImageView alertIcon;



    @SuppressLint("StaticFieldLeak")
    private static TextView frameTitle;
    private static SuperMarchand superMarchand;
    private static Utilisateur utilisateur;
    private static View isRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_marchand);


        init();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu_supermarchand, menu);
        return true;

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        new OptionMenuClickListener(


                SuperMarchandActivity.this

        )
                .supermarchandOptionMenuItemClickListener(
                        item.getItemId()
                );



        return true;

    }





    private void init() {


        findView();
        initValue();
        setClicklistener();

    }





    private void findView() {

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        bubbleTabBar = findViewById(R.id.bubbleTabBarSm);
        frameTitle  =  findViewById(R.id.frame_title);
        alertIcon   =  findViewById(R.id.alertIcon);


        isRead = findViewById(R.id.isRead);

    }




    @SuppressLint("SetTextI18n")
    private void initValue() {


        try {

            Gson gson = new Gson();

            Userable userable = gson.fromJson(getIntent().getExtras()
                    .getString("superMarchand", null), Userable.class);


            superMarchand = gson.fromJson(gson.toJson(userable.getObject()), SuperMarchand.class);

            utilisateur = gson.fromJson(gson.toJson(userable.getUtilisateur()), Utilisateur.class);


        } catch (Exception e) {

            e.printStackTrace();

        }



        frameTitle.setText("Salut " + utilisateur.getPrenom());



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());
        fragmentTransaction.commit();

    }





    private void setClicklistener() {


        alertIcon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                replaceFragment(new Notifications(utilisateur.getId()));

                frameTitle.setText("Notifications");

            }
        });



        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                new BottombarClickListener(fragmentManager).supermarchanBottombarItemClicked(i);

            }
        });


    }






    @SuppressLint("SetTextI18n")
    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = (SuperMarchandActivity.this).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        frameTitle.setText("Notifications");

    }










    public static TextView getFrameTitle() {
        return frameTitle;
    }

    public static void setFrameTitle(TextView frameTitle) {
        SuperMarchandActivity.frameTitle = frameTitle;
    }


    public static SuperMarchand getSuperMarchand() {
        return superMarchand;
    }

    public static void setSuperMarchand(SuperMarchand superMarchand) {
        SuperMarchandActivity.superMarchand = superMarchand;
    }


    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        SuperMarchandActivity.utilisateur = utilisateur;
    }


    public static View getIsRead() {
        return isRead;
    }
}
