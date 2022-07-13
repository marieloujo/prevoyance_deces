package bj.assurance.assurancedeces.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.Notifications;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.utils.BottombarClickListener;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;

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

public class ClientActivity extends AppCompatActivity {




    private BubbleTabBar bubbleTabBar;
    private ImageView alertIcon;



    @SuppressLint("StaticFieldLeak")
    private static TextView frameTitle;
    private static Client client;


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

        findView();
        initValue();
        setClicklistener();


    }




    private void findView() {


        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        frameTitle  =  findViewById(R.id.frame_title);
        alertIcon   =  findViewById(R.id.alertIcon);


    }





    @SuppressLint("SetTextI18n")
    private void initValue() {


        try {

            client =  new Gson().fromJson(getIntent().getExtras()
                    .getString("client", null), Client.class);


            System.out.println(client);

        } catch (Exception e) {

            e.printStackTrace();

        }



        frameTitle.setText("Salut " + client.getUtilisateur().getPrenom());




        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_main,new bj.assurance.assurancedeces.fragment.client.Accueil());
        fragmentTransaction.commit();




    }






    private void setClicklistener() {


        alertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new Notifications());

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
}
