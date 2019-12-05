package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.kinda.alert.KAlertDialog;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.model.Departement;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Marchands extends Fragment {

    private ExpandingList mExpandingList;
    private ProgressBar progressBar;

    public Marchands() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_marchands, container, false);

        init(view);

        try {

            getDepartementofUser(TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)).getToken());
        } catch (Exception e) {}

        return view;
    }

    public void init(View view) {

        mExpandingList = view.findViewById(R.id.expanding_list_main);
        progressBar = view.findViewById(R.id.scroll_progress);

    }

    @SuppressLint("SetTextI18n")
    private void addItem(String title, List<Utilisateur> subItems) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.size());

            for (int i = 0; i < subItems.size(); i++) {
                View subItemZero = item.getSubItemView(i);
                ((TextView) subItemZero.findViewById(R.id.nom_prenom_marchand)).setText(subItems.get(i).getNom()+ " " +
                                subItems.get(i).getPrenom());
                ((TextView) subItemZero.findViewById(R.id.numero_marchand)).setText(subItems.get(i).getTelephone());

                int finalI = i;
                subItemZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragment(new DetailMarchand(subItems.get(finalI)), "Marchand "+subItems.get(finalI).getPrenom());
                    }
                });

            }

        }

    }

    public void getDepartementofUser(AccessToken accessToken) {

        Call<Departement> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.getMarchandsinClient();
        call.enqueue(new Callback<Departement>() {
            @Override
            public void onResponse(Call<Departement> call, Response<Departement> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    for (int i = 0; i < response.body().getCommunes().size(); i++) {
                        if (!(response.body().getCommunes().get(i).getUtilisateurs().size() == 0)) {
                            addItem(response.body().getCommunes().get(i).getNom(), response.body().getCommunes().get(i).getUtilisateurs());

                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    if (response.code() == 422) {
                        System.out.println(response.errorBody().source());
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(getContext(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Departement> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

                /*new KAlertDialog(getContext(), KAlertDialog.WARNING_TYPE)
                        .setTitleText("Connexion impossibe au serveur")
                        .setContentText("Oups!!! quelque chose s'est mal passé vérifier votre connexion internet et réessayer")
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();*/
            }
        });
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);

    }

    private void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        Main2Activity.getTextTitle().setText(titre);

    }





}
