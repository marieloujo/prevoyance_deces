package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.SimpleCustomBottomSheet;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonProfile;
import bj.assurance.prevoyancedeces.fragment.marchand.Accueil;
import bj.assurance.prevoyancedeces.fragment.marchand.AddClient;
import bj.assurance.prevoyancedeces.fragment.marchand.Historique;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MarchandMainActivity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar,bubbleTabBarSm;
    TextView title;
    private static FloatingActionButton floatingActionButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchand_main);

        init();
        setView();
        floatingButtonaddTransaction();
        setClickListener();

    }

    public void init() {
        title = findViewById(R.id.frame_title);
        floatingActionButton = findViewById(R.id.floatingAdd);
        bubbleTabBarSm = findViewById(R.id.bubbleTabBarSm);
        bubbleTabBar = findViewById(R.id.bubbleTabBar);
    }

    public void setClickListener(){
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationMItemClicked(i);
            }
        });

        bubbleTabBarSm.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationSmItemClicked(i);
            }
        });
    }

    public void setView() {
        if (Connexion.users.equals("super marchants")) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main_marchand,new bj.assurance.prevoyancedeces.fragment.supermachand.Accueil());
            fragmentTransaction.commit();
            bubbleTabBarSm.setVisibility(View.VISIBLE);
            bubbleTabBar.setVisibility(View.INVISIBLE);
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main_marchand,new Accueil());
            fragmentTransaction.commit();
            bubbleTabBarSm.setVisibility(View.INVISIBLE);
            bubbleTabBar.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("RestrictedApi")
    public void buttomNavigationMItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_clients:
                replaceFragment(new Marchands(), getResources().getString(R.string.mes_clients));
                floatingButtonaddClient();
                break;

            case R.id.bottom_nav_prospects:
                replaceFragment(new Marchands(), getResources().getString(R.string.mes_prospects));
                floatingButtonaddProspect();
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new Accueil(), getResources().getString(R.string.bonjour_joan));
                floatingButtonaddTransaction();

        }
    }

    @SuppressLint("RestrictedApi")
    public void buttomNavigationSmItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                floatingActionButton.setVisibility(View.INVISIBLE);
                break;

            case R.id.bottom_nav_marchands:
                replaceFragment(new Marchands(), getResources().getString(R.string.mes_marchands));
                floatingButtonaddClient();
                break;

            case R.id.bottom_nav_profil_supermarchand:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Historique(), getResources().getString(R.string.mon_profil));
                floatingButtonaddProspect();
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Accueil(), getResources().getString(R.string.bonjour_joan));
                floatingButtonaddTransaction();

        }
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main_marchand, fragment).commit();

        title.setText(titre);

    }

    public void floatingButtonaddTransaction() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new SimpleCustomBottomSheet(MarchandMainActivity.this);
                bottomSheet.show();
            }
        });
    }

    public void floatingButtonaddClient() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddClient(), "Ajouter un client");
            }
        });
    }

    public void floatingButtonaddProspect() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }

    public static FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public static void setFloatingActionButton(FloatingActionButton floatingActionButton) {
        MarchandMainActivity.floatingActionButton = floatingActionButton;
    }
}
