package bj.assurance.prevoyancedeces.activity;

import android.content.Intent;
import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.BoutiqueVirtuelle;
import bj.assurance.prevoyancedeces.fragment.Discussion;
import bj.assurance.prevoyancedeces.fragment.Marchand.Accueil;
import bj.assurance.prevoyancedeces.fragment.Marchand.InscriptionClient;
import bj.assurance.prevoyancedeces.fragment.Marchand.Statistique;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.fragment.client.MonCarnet;
import bj.assurance.prevoyancedeces.fragment.Notification;
import bj.assurance.prevoyancedeces.fragment.ProduitsServices;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BubbleTabBar bubbleTabBar, bubbleTabBarM;
    FragmentTransaction fragmentTransaction;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        title = findViewById(R.id.toolbarTextView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        bubbleTabBarM = findViewById(R.id.bubbleTabBar_marchand);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Connexion.users.equals("client")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.client_drawer_menu);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main,new MonCarnet());
            fragmentTransaction.commit();
            title.setText(getResources().getString(R.string.mon_carnet));

            bubbleTabBar.setVisibility(View.VISIBLE);

        } else if (Connexion.users.equals("marchands")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.marchand_drawer_menu);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_main,new Accueil());
            fragmentTransaction.commit();
            title.setText(getResources().getString(R.string.mon_carnet));

            bubbleTabBarM.setVisibility(View.VISIBLE);

        } else if (Connexion.users.equals("super marchants")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.super_marchand_drawer_menu);
        }


        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationItemClicked(i);
            }
        });

        bubbleTabBarM.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                buttomNavigationItemClicked(i);
            }
        });
    }

    public void buttomNavigationItemClicked(int id) {

        switch (id) {

            case R.id.bottom_discussion:
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
                break;

            case R.id.bottom_carnet:
                replaceFragment(new MonCarnet(), getResources().getString(R.string.mon_carnet));
                break;

            case R.id.bottom_marchands:
                replaceFragment(new Marchands(), getResources().getString(R.string.marchands));
                break;

            case R.id.bottom_marchand_acceuil:
                replaceFragment(new Accueil(), getResources().getString(R.string.acceuil));
                break;

            case R.id.bottom_statistic:
                replaceFragment(new Statistique(), getResources().getString(R.string.mes_statistiques));
                break;
        }
    }

    public void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        title.setText(titre);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_arlert) {
            replaceFragment(new Notification(), getResources().getString(R.string.notifications));
        } else if (id == R.id.action_chat) {
            replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
        } else if (id == R.id.action_share) {
            share();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_marchands) {
            replaceFragment(new Marchands(), getResources().getString(R.string.marchands));
        } else if (id == R.id.nav_discussion) {
            replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
        } else if (id == R.id.nav_carnet) {
            replaceFragment(new MonCarnet(), getResources().getString(R.string.mon_carnet));
        } else if (id == R.id.nav_produits_services) {
            replaceFragment(new ProduitsServices(), getResources().getString(R.string.produits_et_services));
        } else if (id == R.id.nav_boutique_virtuelle) {
            replaceFragment(new BoutiqueVirtuelle(),  getResources().getString(R.string.boutique_virtuelle));
        } else if (id == R.id.nav_partager) {
            share();
        } else if (id == R.id.nav_accueil_marchand) {
            replaceFragment(new Accueil(),  getResources().getString(R.string.acceuil));
        } else if (id == R.id.nav_inscrire_client) {
            replaceFragment(new InscriptionClient(),  getResources().getString(R.string.inscrire_un_client));
        } else if (id == R.id.nav_statistic) {
            replaceFragment(new Statistique(),  getResources().getString(R.string.mes_statistiques));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        String url = "https://play.google.com/store/apps/details";

        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
