package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
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

    private LinearLayout noDataLayout;
    private RelativeLayout contentEror;

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
        noDataLayout = view.findViewById(R.id.no_data_layout);
        contentEror = view.findViewById(R.id.content_error);

        mesDiscussions = view.findViewById(R.id.mes_messages);
        mesDiscussions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Discussion(Main2Activity.getUtilisateur().getId()), getResources().getString(R.string.discussion));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @SuppressLint("SetTextI18n")
    private void bindingData() {
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
            Call<JsonObject> call;
            ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
            call = service.getAllContrat(Main2Activity.getClient().getId());
            call.enqueue(new Callback<JsonObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        getResponseContrat(response.body());

                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        noDataLayout.setVisibility(View.INVISIBLE);
                        contentEror.setVisibility(View.VISIBLE);

                        ((TextView) contentEror.findViewById(R.id.error_text))
                                .setText("Auncun contrat");
                        ((Button) contentEror.findViewById(R.id.retry)).setVisibility(View.INVISIBLE);
                    }

                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    noDataLayout.setVisibility(View.INVISIBLE);
                    contentEror.setVisibility(View.VISIBLE);

                    ((TextView) contentEror.findViewById(R.id.error_text))
                            .setText("Auncun contrat");
                    ((Button) contentEror.findViewById(R.id.retry)).setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = Utils.converErrors(response);
        Toast.makeText(getActivity(),apiError.getErrors() + " " + apiError.getMessage(), Toast.LENGTH_LONG).show();
        System.out.println(apiError.getErrors() + " " + apiError.getMessage());

    }

    private void getResponseContrat(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        List<Contrat> contrats = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            ((TextView) contentEror.findViewById(R.id.error_text))
                    .setText(messageError);
            contentEror.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.INVISIBLE);

        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            ((TextView) noDataLayout.findViewById(R.id.no_data))
                    .setText(message);
            contentEror.setVisibility(View.INVISIBLE);
            noDataLayout.setVisibility(View.VISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Contrat>>() {}.getType();

            assert outputPaginate != null;
            contrats = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);

            contratClientAdapter = new ContratClientAdapter(getContext(), contrats);
            recyclerView.setAdapter(contratClientAdapter);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
