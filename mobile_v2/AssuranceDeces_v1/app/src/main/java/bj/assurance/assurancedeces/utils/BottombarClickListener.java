package bj.assurance.assurancedeces.utils;



import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.fragment.client.Marchands;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.MesClients;
import bj.assurance.assurancedeces.fragment.marchand.MonProfil;
import bj.assurance.assurancedeces.fragment.marchand.Transactions;
import bj.assurance.assurancedeces.fragment.supermarchand.Historique;
import bj.assurance.assurancedeces.fragment.supermarchand.MesMarchands;


public class BottombarClickListener {


    private FragmentManager fragmentManager;




    public BottombarClickListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }





    @SuppressLint("SetTextI18n")
    public void marchanBottombarItemClicked(int id) {

        switch (id) {

            case R.id.marchand_bottom_nav_accueil:

                replaceFragment(new Accueil());

                MarchandActivity.getFrameTitle().setText(

                        "Salut " + MarchandActivity.getUtilisateur().getPrenom()
                );


                break;

            case R.id.marchand_bottom_nav_clients:

                replaceFragment(new MesClients());

                MarchandActivity.getFrameTitle().setText("Mes clients ");

                break;

            case R.id.marchand_bottom_nav_transactions:

                replaceFragment(new Transactions());

                MarchandActivity.getFrameTitle().setText("Transactions");

                break;

            case R.id.marchand_bottom_nav_profil:

                replaceFragment(new MonProfil());

                MarchandActivity.getFrameTitle().setText("Profil");

                break;

        }
    }





    @SuppressLint("SetTextI18n")
    public void supermarchanBottombarItemClicked(int id) {


        switch (id) {


            case R.id.supermarchand_bottom_nav_accueil:

                replaceFragment(new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());

                SuperMarchandActivity.getFrameTitle().setText(
                        "Salut " +
                        SuperMarchandActivity.getUtilisateur().getPrenom()
                );

                break;


            case R.id.supermarchand_bottom_nav_historique:

                replaceFragment(new Historique());

                SuperMarchandActivity.getFrameTitle().setText("Historique");

                break;


            case R.id.supermarchand_bottom_nav_marchands:

                replaceFragment(new MesMarchands() );

                SuperMarchandActivity.getFrameTitle().setText("Mes marchands");

                break;


            /*case R.id.supermarchand_bottom_nav_profil:


                replaceFragment(new MonProfil(), "Profil");
                                break;*/




        }


    }






    @SuppressLint("SetTextI18n")
    public void clientBottombarItemClicked(int id) {


        switch (id) {


            case R.id.client_bottom_nav_accueil:

                replaceFragment(new bj.assurance.assurancedeces.fragment.client.Accueil());

                ClientActivity.getFrameTitle().setText(
                        "Salut " +
                                ClientActivity.getUtilisateur().getPrenom()
                );

                break;


            case R.id.client_bottom_nav_carnet:

                replaceFragment(new bj.assurance.assurancedeces.fragment.client.MonProfil());

                ClientActivity.getFrameTitle().setText("Mon profil");

                break;


            case R.id.client_bottom_nav_discussion:

               /* replaceFragment(new MesMarchands() );

                SuperMarchandActivity.getFrameTitle().setText("MesMarchands");*/


                break;


            case R.id.client_bottom_nav_marchands:

                replaceFragment(new Marchands() );

                ClientActivity.getFrameTitle().setText("Les marchands");

                break;



        }


    }








    private void replaceFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();

    }






}
