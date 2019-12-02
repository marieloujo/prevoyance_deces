package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.Boutique;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.visiteur.Accueil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

public class MainVisiteurActivity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar;
    FragmentTransaction fragmentTransaction;
    @SuppressLint("StaticFieldLeak")
    static TextView title;

    ImageView icon, drawer;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_visiteur);

        init();
    }

    public void init() {

        title = findViewById(R.id.frame_title);
        bubbleTabBar = findViewById(R.id.bubbleTabBarV);
        icon = findViewById(R.id.alertIcon);
        drawer = findViewById(R.id.drawer_layout);
        view = findViewById(R.id.info);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        setView();
        setOnclickListner();

        title.setText("Bonjour");

    }

    public void setView() {
        fragmentTransaction.add(R.id.content_main,new Accueil());
        fragmentTransaction.commit();

        icon.setVisibility(View.INVISIBLE);
        drawer.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
    }

    public void setOnclickListner() {
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationItemClicked(i);
            }
        });

    }

    public void buttomNavigationItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_marchands_visiteur:
                replaceFragment(new Marchands(), getResources().getString(R.string.marchands));
                break;

            case R.id.bottom_nav_accueil_visiteur:
                replaceFragment(new Accueil(), getResources().getString(R.string.bonjour_joan));
                break;

            case R.id.bottom_nav_boutique_visiteur:
                replaceFragment(new Boutique(), getResources().getString(R.string.boutique_virtuelle));
                break;

        }
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        title.setText(titre);

    }
}
