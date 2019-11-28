package bj.assurance.prevoyancedeces.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.client.Accueil;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonProfile;
import bj.assurance.prevoyancedeces.fragment.client.Notification;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    BubbleTabBar bubbleTabBar, bubbleTabBarV;
    FragmentTransaction fragmentTransaction;
    @SuppressLint("StaticFieldLeak")
    static TextView title,backTitle;
    ImageView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
        setView();
        setOnclickListner();

    }

    public void init() {
        title = findViewById(R.id.frame_title);
        backTitle = findViewById(R.id.back_frame);
        alert = findViewById(R.id.alertIcon);
        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        bubbleTabBarV = findViewById(R.id.bubbleTabBarV);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    public void setView() {
        if (Welcome.visiteur) {
            fragmentTransaction.add(R.id.content_main,new bj.assurance.prevoyancedeces.fragment.visiteur.Accueil());
            fragmentTransaction.commit();
            bubbleTabBarV.setVisibility(View.VISIBLE);
            bubbleTabBar.setVisibility(View.INVISIBLE);

        } else {
            fragmentTransaction.add(R.id.content_main,new Accueil());
            fragmentTransaction.commit();
            bubbleTabBarV.setVisibility(View.INVISIBLE);
            bubbleTabBar.setVisibility(View.VISIBLE);
        }
    }

    public void setOnclickListner() {
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationItemClicked(i);
            }
        });

        bubbleTabBarV.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationVItemClicked(i);
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Notification(), getResources().getString(R.string.notifications));
            }
        });
    }

    public void buttomNavigationItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_nav_carnet:
                Intent intent = new Intent(Main2Activity.this, MonProfile.class);
                startActivity(intent);
                break;

            case R.id.bottom_nav_marchands:
                replaceFragment(new Marchands(), getResources().getString(R.string.marchands));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new Accueil(), getResources().getString(R.string.bonjour_joan));

        }
    }

    public void buttomNavigationVItemClicked(int id) {

        switch (id) {

            case R.id.bottom_nav_boutique:
                replaceFragment(new Accueil(), getResources().getString(R.string.mon_carnet));
                break;

            case R.id.bottom_nav_accueil:
                replaceFragment(new bj.assurance.prevoyancedeces.fragment.visiteur.Accueil(), getResources().getString(R.string.bonjour_joan));

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

    public static TextView getBackTitle() {
        return backTitle;
    }

    public static void setBackTitle(TextView backTitle) {
        Main2Activity.backTitle = backTitle;
    }
}
