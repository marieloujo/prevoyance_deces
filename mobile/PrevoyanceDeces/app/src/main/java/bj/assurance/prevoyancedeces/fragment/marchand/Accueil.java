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
import com.kinda.alert.KAlertDialog;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.SimpleCustomBottomSheet;
import bj.assurance.prevoyancedeces.Utils.AccessToken;
import bj.assurance.prevoyancedeces.Utils.ApiError;
import bj.assurance.prevoyancedeces.Utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.adapter.TransactionAdater;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
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
    private TextView textView;
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


        getContratsForUser(TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken());

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View view) {
        seekBar = view.findViewById(R.id.simpleSeekBar);
        textView = view.findViewById(R.id.view_historique);
        floatingActionButton = view.findViewById(R.id.floatingAdd);

        mExpandingList = view.findViewById(R.id.expanding_list_main);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setClickListener() {
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

            ((TextView) item.findViewById(R.id.nom_prenom_clent)).setText(subItems.getClient().getUtilisateur().getNom() + " " +
                    subItems.getClient().getUtilisateur().getPrenom());

            ((TextView) item.findViewById(R.id.numero_police)).setText(subItems.getNumeroPolice());

            //We can create items in batch.
            item.createSubItems(subItems.getTransactions().size());

            for (int i = 0; i < subItems.getTransactions().size(); i++) {
                View subItemZero = item.getSubItemView(i);
                ((TextView) subItemZero.findViewById(R.id.nom_prenom_clent)).setText(subItems.getClient().getUtilisateur().getNom()+ " " +
                        subItems.getClient().getUtilisateur().getPrenom());


                Call<List<Portefeuille>> call;
                MarchandService service = new RetrofitBuildForGetRessource(
                        TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
                ).getRetrofit().create(MarchandService.class);
                call = service.getTransaction(subItems.getId());
                call.enqueue(new Callback<List<Portefeuille>>() {
                    @Override
                    public void onResponse(Call<List<Portefeuille>> call, Response<List<Portefeuille>> response) {

                        Log.w(TAG, "onResponse: " + response);

                        if (response.isSuccessful()) {
                            System.out.println(response.body());
                            ((TextView) subItemZero.findViewById(R.id.date_paiement)).setText(subItems.getTransactions().get(i).getCreatedAt());

                            ((TextView) subItemZero.findViewById(R.id.montant_paye)).setText(subItems.getTransactions().get(i).getMontant());

                        } else {
                            if (response.code() == 422) {
                                System.out.println(response.errorBody().source());
                                handleErrors(response.errorBody());
                            }
                            if (response.code() == 401) {
                                ApiError apiError = Utils.converErrors(response.errorBody());
                                //Toast.makeText(Main2Activity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Portefeuille>> call, Throwable t) {
                        Log.w(TAG, "onFailure: " + t.getMessage());
                        //Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }

        }

    }

    public void getContratsForUser(AccessToken accessToken) {

        Call<Marchand> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getMarchanbyId(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<Marchand>() {
            @Override
            public void onResponse(Call<Marchand> call, Response<Marchand> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    for (int i = 0; i < response.body().getContrats().size(); i++) {
                        addItem(response.body().getContrats().get(i));
                    }

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
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

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


}
