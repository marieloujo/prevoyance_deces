package bj.assurance.prevoyancedeces.fragment.marchand;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurivanets.bottomsheets.BottomSheet;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kinda.alert.KAlertDialog;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.SimpleCustomBottomSheet;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class Accueil extends Fragment {


    private SeekBar seekBar;
    private TextView textView, matricule, nomPrenom, creditVirtuelleActuelle, creditVirtuelleRecharger, gotoTransaction;
    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;
    private TransactionAdater transactionAdater;

    private ExpandingList mExpandingList;


    public Accueil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accueil_marchand, container, false);


        init(view);
        setClickListener();

        bindData(MarchandMainActivity.getMarchand());

        /*
        getContratsForUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());*/

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        seekBar = view.findViewById(R.id.simpleSeekBar);
        textView = view.findViewById(R.id.view_historique);
        floatingActionButton = view.findViewById(R.id.floatingAdd);
        matricule = view.findViewById(R.id.matricule_marchand);
        nomPrenom = view.findViewById(R.id.nom_prenom_marchand);
        creditVirtuelleActuelle = view.findViewById(R.id.credit_virtuelle_actuel);
        creditVirtuelleRecharger = view.findViewById(R.id.credit_virtuel_depart);
        recyclerView = view.findViewById(R.id.recycler);
        gotoTransaction = view.findViewById(R.id.view_all_transaction);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mExpandingList = view.findViewById(R.id.expanding_list_main);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setClickListener() {
        seekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new Historique()).commit();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new SimpleCustomBottomSheet(getActivity());
                bottomSheet.show();
            }
        });

        gotoTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new Transactions()).commit();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void bindData(Marchand marchand) {

        nomPrenom.setText(MarchandMainActivity.getUtilisateur().getNom()+ " " + MarchandMainActivity.getUtilisateur().getPrenom());
        matricule.setText(marchand.getMatricule());
        creditVirtuelleRecharger.setText(marchand.getCreditVirtuel() + " fcfa");

        getCreditVirtuelle(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );

    }

    @SuppressLint("SetTextI18n")
    private void addItem(Contrat subItems) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout_transaction);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item


            try {
                ((TextView) item.findViewById(R.id.nom_prenom_clent)).setText(subItems.getClient().getUtilisateur().getNom() + " " +
                        subItems.getClient().getUtilisateur().getPrenom());

                ((TextView) item.findViewById(R.id.numero_police)).setText(subItems.getNumeroPolice());
            } catch (Exception e) {
                System.out.println(e.getCause());
            }

            //We can create items in batch.
            item.createSubItems(subItems.getTransactions().size());

            for (int i = 0; i < subItems.getTransactions().size(); i++) {
                View subItemZero = item.getSubItemView(i);

                try {
                    ((TextView) subItemZero.findViewById(R.id.date_paiement)).setText(subItems.getTransactions().get(i).getCreatedAt());

                    ((TextView) subItemZero.findViewById(R.id.montant_paye)).setText(subItems.getTransactions().get(i).getMontant()+ " fcfa");
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }

            }

        }

    }

    private void getContratsForUser(AccessToken accessToken) {

        Call<Marchand> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getMarchanbyId(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<Marchand>() {
            @Override
            public void onResponse(Call<Marchand> call, Response<Marchand> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    //System.out.println(response.body());
                    bindData(response.body());

                    /*
                    for (int i = 0; i < response.body().getContrats().size(); i++) {
                        addItem(response.body().getContrats().get(i));
                    }*/

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Marchand> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Connexion impossibe au serveur")
                        .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }


    private void getCreditVirtuelle(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getCompte();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    JsonElement credit = response.body().get("credit_virtuel");
                    creditVirtuelleActuelle.setText(credit.getAsString() + " fcfa");

                    Integer pourcentage = (
                            Integer.valueOf(MarchandMainActivity.getMarchand().getCreditVirtuel()) / Integer.valueOf(credit.getAsString())
                        ) * 100 ;

                    seekBar.setProgress(pourcentage);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTansactions(AccessToken accessToken) {
       /* Call<List<Portefeuille>> call;

        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);

        call = service.g(MarchandMainActivity.getUtilisateur().getId());
        call.enqueue(new Callback<List<Portefeuille>>() {
            @Override
            public void onResponse(Call<List<Portefeuille>> call, Response<List<Portefeuille>> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    transactionAdater = new TransactionAdater(getContext(), response.body());
                    recyclerView.setAdapter(transactionAdater);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Portefeuille>> call, Throwable t) {

            }
        });*/
    }

}
