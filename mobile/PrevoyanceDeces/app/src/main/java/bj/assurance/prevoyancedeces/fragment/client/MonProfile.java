package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import bj.assurance.prevoyancedeces.utils.ApiError;
import bj.assurance.prevoyancedeces.utils.Utils;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.adapter.ContratClientAdapter;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class MonProfile extends Fragment {

    private TextView nomPrenom, adresse, email;
    private Button mesDiscussions;
    private CardView cardView;

    private RecyclerView recyclerView;
    private ContratClientAdapter contratClientAdapter;

    private ProgressBar progressBar;

    public MonProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mon_profile, container, false);

        try {

            init(view);
            bindingData();
        } catch (Exception e) {

        }
        return view;

    }


    @SuppressLint("WrongConstant")
    public void init(View view) {
        nomPrenom = view.findViewById(R.id.nom_prenom_clent);
        adresse = view.findViewById(R.id.adresse_client);
        email = view.findViewById(R.id.adresse_mail_client);
        cardView = view.findViewById(R.id.contrat_item);
        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.scroll_progress);

        mesDiscussions = view.findViewById(R.id.mes_messages);
        mesDiscussions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Discussion(), getResources().getString(R.string.discussion));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        200));
            }
        });*/

    }

    @SuppressLint("SetTextI18n")
    public void bindingData() {
        nomPrenom.setText(Main2Activity.getUtilisateur().getNom() + " " + Main2Activity.getUtilisateur().getPrenom());
        adresse.setText(Main2Activity.getUtilisateur().getAdresse());
        email.setText(Main2Activity.getUtilisateur().getEmail());

        getContratsForUser(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken());
    }

    private void replaceFragment(Fragment fragment, String titre){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        Main2Activity.getTextTitle().setText(titre);

    }

    private void getContratsForUser(AccessToken accessToken) {

        try {
            Call<Client> call;
            ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
            call = service.getClientbyId(Main2Activity.getClient().getId());
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        if (response.body().getContrats().isEmpty()) {

                        } else {
                            System.out.println(response.body());
                            contratClientAdapter = new ContratClientAdapter(getContext(), response.body().getContrats());
                            recyclerView.setAdapter(contratClientAdapter);

                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response.code() == 422) {
                            System.out.println(response.errorBody().source());
                            handleErrors(response.errorBody());
                        }

                        if (response.code() == 401) {
                            ApiError apiError = Utils.converErrors(response.errorBody());
                        }
                    }

                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        } catch (Exception e) {
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
        Toast.makeText(getActivity(),apiError.getErrors() + " " + apiError.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(apiError.getErrors() + " " + apiError.getMessage());

    }

}
