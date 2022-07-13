package bj.assurance.assurancedeces.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.marchand.AddContratStepthree;
import bj.assurance.assurancedeces.model.Benefice;
import bj.assurance.assurancedeces.model.Beneficiaire;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.ValidationEror;
import bj.assurance.assurancedeces.serviceImplementation.ContratServiceImplementation;
import bj.assurance.assurancedeces.utils.CatchApiError;
import bj.assurance.assurancedeces.utils.OptionMenuClickListener;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContentAddBeneficier extends AppCompatActivity {



    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    int numero = 1;


    private Button prev, next;
    private ShowDialog showDialog;


    private List<Benefice> benefices = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add_beneficier);



        init();



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu_add_beneficiaire, menu);
        return true;

    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.add_beneficiaire:

                numero++;
                adapter.addFragment(new AddContratStepthree(), "Béneficiaire n°" + numero);
                adapter.notifyDataSetChanged();

                break;


            case R.id.supp_beneficiaire:
                break;


        }

        return true;

    }






    private void init() {

        try {

            findView();
            initValue();
            clickListner();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }




    private void initValue() {


        showDialog = new ShowDialog(ContentAddBeneficier.this);


    }





    private void findView() {

        prev = findViewById(R.id.annuler);
        next = findViewById(R.id.suivant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewPager viewPager = findViewById(R.id.pager);
        setViewPager(viewPager);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


    }





    private void clickListner() {


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmationtoCancel();

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextClicked();

            }
        });




    }










    private void nextClicked() {


        benefices.clear();

        System.out.println("size fragment " + adapter.mFragmentList.size());

        for (int i = 0; i < adapter.mFragmentList.size(); i++)  {

            AddContratStepthree addContratStepthree = (AddContratStepthree) adapter.mFragmentList.get(i);

            if (addContratStepthree.isIsGetedbyPhone()) {

                onResponseBeneficaire(addContratStepthree);

            } else {

                validation(addContratStepthree, i);

            }


        }

    }










    private void ifBeneicaireisGetbyPhone(AddContratStepthree addContratStepthree) {

        Beneficiaire beneficiaire = new Beneficiaire();

        Utilisateur utilisateur1 = addContratStepthree.getUtilisateurFromForm();
        utilisateur1.setId(addContratStepthree.getUtilisateur().getId());

        utilisateur1.setTelephone(
                (addContratStepthree.getPhoneIdentity().getSelectedItem().toString()).substring(1) +
                addContratStepthree.getEtTelephone().getRawText()
        );


        if (addContratStepthree.getBeneficiaire1() != null) {

            try {

                Userable userable = addContratStepthree.getUtilisateur().getUserables().get(0);
                userable.setUtilisateur(utilisateur1);
                userable.setObject(null);

                beneficiaire = new Beneficiaire(addContratStepthree.getBeneficiaire1().getId(), userable);

            } catch (Exception e) {

                e.printStackTrace();

                beneficiaire = new Beneficiaire(addContratStepthree.getBeneficiaire1().getId(), new Userable());

            }

        } else {

            beneficiaire = new Beneficiaire(0L, utilisateur1);
            beneficiaire.getUserable().getUtilisateur().setId(addContratStepthree.getUtilisateur().getId());

        }

        benefices.add(new Benefice(addContratStepthree.getQualification(),
                addContratStepthree.getEtTaux().getText().toString(), beneficiaire));

    }








    private void validation(final AddContratStepthree addContratStepthree, final int position) {


        final LottieAlertDialog lottieAlertDialog =
                new LottieAlertDialog.Builder(ContentAddBeneficier.this,DialogTypes.TYPE_LOADING)
                .setTitle("Vérification des données")
                .setDescription("Veuillez patientez")
                .build();
        lottieAlertDialog.setCancelable(false);



        new ContratServiceImplementation(TokenManager.getInstance(ContentAddBeneficier.this
                .getSharedPreferences("prefs", MODE_PRIVATE))
                .getToken())
                .validation(addContratStepthree.getUtilisateurFromForm())
                .enqueue(new Callback<JsonObject>() {



                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {



                        if (response.isSuccessful()) {

                            try {

                                lottieAlertDialog.dismiss();

                            } catch (Exception e) {}

                            onResponseBeneficaire(addContratStepthree);

                        } else {


                            try {

                                lottieAlertDialog.dismiss();

                            } catch (Exception e) {}

                            onResponseFailure(response, addContratStepthree);


                            //tvTelephone.setText("Ce numéro n'est pas attribué pour le code " + phoneIdentity.getSelectedItem().toString());
                            //tvTelephone.setVisibility(View.VISIBLE);

                        }

                    }



                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        try {

                            lottieAlertDialog.dismiss();

                        } catch (Exception e) {}

                        try {

                            showDialog.showNoInternetDialog().setPositiveListener(new ClickListener() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                    lottieAlertDialog.dismiss();
                                    validation(addContratStepthree, position);
                                }
                            })
                                    .build()
                                    .show();

                        } catch (Exception ignored) {}




                    }


                });



    }










    @SuppressLint("SetTextI18n")
    private void onResponseBeneficaire(AddContratStepthree addContratStepthree) {


        addContratStepthree.resetFormError();


        if (addContratStepthree.getEtTaux().getText().toString().equals("")) {

            addContratStepthree.makeTauxError("Le taux est obligatoire");

        } else  {

            if (addContratStepthree.getQualification().equals("")) {

                addContratStepthree.resetTauxError();

                addContratStepthree.makeQualifError();

            } else {

                addContratStepthree.resetTauxError();

                ifTauxisValid(addContratStepthree);

            }

        }


    }






    private void ifTauxisValid(AddContratStepthree addContratStepthree) {

            Integer taux = 0;
            System.out.println("taux " + taux);
            for (int i = 0; i < adapter.mFragmentList.size(); i++) {

                taux += Integer.valueOf(
                        ((AddContratStepthree) adapter.mFragmentList.get(i))
                                .getEtTaux().getText().toString());
            }

            System.out.println("taux1 value " + taux);

            boolean isValidTaux = false;
            if (taux < 100 || taux > 100) {

                System.out.println("taux value " + taux);

                new  ShowDialog(ContentAddBeneficier.this).showWarningDialog("Valeur incorrect",
                        "Veuillez vérifier la valeur des taux, le taux total doit être égale à 100");

            } else {

                isValidTaux = true;

            }


            if (isValidTaux) {

                setListBenefice(addContratStepthree);


            }



    }






    private void setListBenefice(AddContratStepthree addContratStepthree) {


        addContratStepthree.resetTauxError();

        if (addContratStepthree.isIsGetedbyPhone()) {

            ifBeneicaireisGetbyPhone(addContratStepthree);

        } else {


            Beneficiaire beneficiaire = new Beneficiaire(0L, addContratStepthree.getUtilisateurFromForm());
            beneficiaire.getUserable().setId(0L);
            beneficiaire.getUserable().getUtilisateur().setId(0L);
            beneficiaire.getUserable().getUtilisateur().setTelephone(
                    (addContratStepthree.getPhoneIdentity().getSelectedItem().toString()).substring(1) +
                            addContratStepthree.getEtTelephone().getRawText()
            );


            benefices.add(new Benefice(addContratStepthree.getQualification(),
                    addContratStepthree.getEtTaux().getText().toString(),
                    beneficiaire));

        }


        if (adapter.mFragmentList.size() == benefices.size()) {


            MarchandActivity.getContrat().setBenefices(benefices);

            System.out.println(" beneficiaire " + new Gson().toJson(MarchandActivity.getContrat()));

            Intent intent = new Intent(ContentAddBeneficier.this, FragmentActivity.class)
                    .putExtra("key", "enregistrementStep4");

            ContentAddBeneficier.this.startActivity(intent);

        }

    }







    private void onResponseFailure(Response<JsonObject> response, AddContratStepthree addContratStepthree) {


        try {

            JSONObject jObjError = new JSONObject(response.errorBody().string());
            Type listType = new TypeToken<List<ValidationEror>>() {}.getType();

            List<ValidationEror> validationEror = new Gson().fromJson (
                    jObjError.getJSONObject("errors").getString("message"),
                    listType
            );

            addContratStepthree.makeEditError(validationEror);

            TextView customTab1 = (TextView) LayoutInflater.from(ContentAddBeneficier.this)
                    .inflate(R.layout.tab_error, null);

            /*customTab1.setText(tabLayout.getTabAt(position).getText());
            ((TextView) tabLayout.getTabAt(position).getCustomView()).setText("qsdsss");*/
            //.setTextColor(getResources().getColor(R.color.red_active));
            /*.setCustomView(customTab1)*/

            //tabLayout.setSelectedTabIndicatorColor((getResources().getColor(R.color.red_active)));

            try {

                showDialog.getAlertDialog().dismiss();

            } catch (Exception e) {e.printStackTrace();}


        } catch (Exception e) {

            e.printStackTrace();

            String string = new CatchApiError(response.code()).catchError(ContentAddBeneficier.this);

        }



    }








    private void setViewPager(ViewPager viewPager) {
        adapter =  new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddContratStepthree(), "Béneficiaire n°" + numero);
        viewPager.setAdapter(adapter);
    }









    private void confirmationtoCancel() {


            showDialog.showQuestionDialog("Confirmaion", "Voulez vous vraiment annuler ??" +
                    " vous perdriez toutes les données entrées.")

                    .setPositiveListener(new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();
                            finish();

                        }
                    })


                    .setNegativeListener(new ClickListener() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                            lottieAlertDialog.dismiss();
                        }
                    })

                    .build()
                    .show();



    }













    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();




        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }




        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }



        @Override
        public int getCount() {
            return mFragmentList.size();
        }



        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }











}
