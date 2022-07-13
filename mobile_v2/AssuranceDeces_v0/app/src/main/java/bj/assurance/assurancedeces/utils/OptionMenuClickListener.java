package bj.assurance.assurancedeces.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.google.gson.JsonObject;

import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import bj.assurance.assurancedeces.BottomSheet.ChoiceTransfertBottomSheet;
import bj.assurance.assurancedeces.BottomSheet.DepotBottomSheet;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.activity.MarchandActivity;
import bj.assurance.assurancedeces.activity.SuperMarchandActivity;
import bj.assurance.assurancedeces.fragment.client.Marchands;
import bj.assurance.assurancedeces.fragment.marchand.Accueil;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepone;
import bj.assurance.assurancedeces.fragment.marchand.AddProspect;
import bj.assurance.assurancedeces.fragment.marchand.Historique;
import bj.assurance.assurancedeces.fragment.marchand.MesClients;
import bj.assurance.assurancedeces.fragment.supermarchand.MesMarchands;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.serviceImplementation.MarchandServiceImplementation;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class OptionMenuClickListener {


    private Context context;
    private ShowDialog showDialog;


    public OptionMenuClickListener(Context context) {
        this.context = context;
        showDialog = new ShowDialog(context);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    public void marchandOptionMenuItemClickListener(int id) {


        switch (id) {

            case R.id.marchand_option_nav_accueil:

                replaceFragment(new Accueil());

                MarchandActivity.getFrameTitle().setText("Salut " +
                        MarchandActivity.getMarchand().getUtilisateur().getPrenom());

                break;


            case R.id.marchand_option_nav_clients:

                replaceFragment(new MesClients());

                MarchandActivity.getFrameTitle().setText("Mes clients");

                break;


            case R.id.marchand_option_nav_add_contrat:

                replaceFragment(new AddContratStepone());

                MarchandActivity.getFrameTitle().setText("Enregistrement de contrat");

                break;


            case R.id.marchand_option_nav_add_prospect:

                replaceFragment(new AddProspect());

                MarchandActivity.getFrameTitle().setText("Ajout d'un prospect");

                break;


            case R.id.marchand_option_nav_contrat_en_attente:

                break;


            case R.id.marchand_option_nav_historique:

                replaceFragment(new Historique());

                MarchandActivity.getFrameTitle().setText("Historique");

                break;


            case R.id.marchand_option_nav_profil:

                break;


            case R.id.marchand_option_nav_transfert:

                getAuthMarchand(MarchandActivity.getMarchand().getId());

                break;


            case R.id.option_nav_logout:

                new UtilisateurServiceImplementation(context).logout(

                        TokenManager.getInstance(((FragmentActivity) context).
                                getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                );

                break;

        }

    }





    public void supermarchandOptionMenuItemClickListener(int id) {


        switch (id) {

            case R.id.supermarchand_option_nav_accueil:

                replaceFragment(new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );

                break;


            case R.id.supermarchand_option_nav_marchands:

                replaceFragment(new MesMarchands());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );

                break;



            case R.id.supermarchand_option_nav_discussion:

                /*replaceFragment(new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );*/

                break;



            case R.id.supermarchand_option_nav_profil:

                /*replaceFragment(new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );

                break;*/




            case R.id.supermarchand_option_nav_logpout:

                new UtilisateurServiceImplementation(context).logout(

                        TokenManager.getInstance(((FragmentActivity) context).
                                getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                );

                break;



        }


    }








    @SuppressLint("SetTextI18n")
    public void clientOptionMenuItemClickListener(int id) {


        switch (id) {

            case R.id.client_option_nav_accueil:

                replaceFragment(new bj.assurance.assurancedeces.fragment.client.Accueil());

                ClientActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );

                break;


            case R.id.client_option_nav_boutique:

                /*replaceFragment(new ());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );*/

                break;



            case R.id.client_option_nav_discussion:

                /*replaceFragment(new bj.assurance.assurancedeces.fragment.supermarchand.Accueil());

                SuperMarchandActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.historique)

                );*/

                break;



            case R.id.client_option_nav_marchands:

                replaceFragment(new Marchands());

                ClientActivity.getFrameTitle().setText(

                        context.getResources().getString(R.string.marchands)

                );

                break;




            case R.id.client_option_nav_partager:

                new UtilisateurServiceImplementation(context).logout(

                        TokenManager.getInstance(((FragmentActivity) context).
                                getSharedPreferences("prefs", MODE_PRIVATE)).
                                getToken()

                );

                break;



        }


    }








    private void getAuthMarchand(Long id) {


        showDialog.showLoaddingDialog("Chargement", "Veuillez patienter quelques instant");

        new MarchandServiceImplementation(

                TokenManager.getInstance(((FragmentActivity) context).
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()

        ).getCreditAndComissionOfMarchand(id)

                .enqueue(new Callback<JsonObject>() {

                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful()) {

                            JsonObject jsonObjectData = response.body()
                                    .getAsJsonObject("success")
                                    .getAsJsonObject("data");

                            Integer commission = Integer.valueOf(jsonObjectData.get("commission").getAsString());

                            if (commission >= 1000) {

                                showDialog.getAlertDialog().dismiss();

                                BottomSheet bottomSheet = new ChoiceTransfertBottomSheet(
                                        (Activity) Objects.requireNonNull(context)
                                );
                                bottomSheet.show();


                            } else {

                                showDialog.getAlertDialog().dismiss();

                                showDialog.showWarningDialog("Commission insuffissante",
                                        "Votre solde de commission est de " + commission +
                                                " Veuillez recharger votre compte pour effectuer l'opération");

                            }



                        }

                    }




                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {


                    }

                });

    }







    private void replaceFragment(Fragment fragment) {


        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();


    }

}
