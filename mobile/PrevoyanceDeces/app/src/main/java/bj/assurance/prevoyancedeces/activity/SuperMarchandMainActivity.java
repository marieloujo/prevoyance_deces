package bj.assurance.prevoyancedeces.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.Notification;
import bj.assurance.prevoyancedeces.fragment.supermachand.Accueil;

public class SuperMarchandMainActivity extends AppCompatActivity {

    private BubbleTabBar bubbleTabBar;
    private TextView title;
    private ImageView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supermarchand_main_activity);

        init();
        setView();
        setClickListener();

    }

    public void init() {

        bubbleTabBar = findViewById(R.id.bubbleTabBarSm);
        title = findViewById(R.id.frame_title);
        alert = findViewById(R.id.alertIcon);
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
                replaceFragment(new Notification(), getResources().getString(R.string.notifications));
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void buttomNavigationSmItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_marchands:
                replaceFragment(new Marchands(), getResources().getString(R.string.mes_marchands));
                break;

            case R.id.bottom_nav_profil_supermarchand:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Historique(), getResources().getString(R.string.mon_profil));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.supermachand.Accueil(), getResources().getString(R.string.bonjour_joan));

        }
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        title.setText(titre);

    }

}
