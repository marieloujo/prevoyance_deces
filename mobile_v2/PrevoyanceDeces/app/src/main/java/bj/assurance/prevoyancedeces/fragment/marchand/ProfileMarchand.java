package bj.assurance.prevoyancedeces.fragment.marchand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.Main2Activity;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.adapter.ProspectsAdapter;
import bj.assurance.prevoyancedeces.fragment.client.Discussion;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;


public class ProfileMarchand extends Fragment {

    private TextView nomPrenom, adresse, email;
    private Button openDiscussions;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public ProfileMarchand() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_marchand, container, false);

        init(view);
        bindingData();
        setlickListener();
        getListProspect(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );

        return view;
    }

    @SuppressLint("WrongConstant")
    private void init(View view) {
        nomPrenom = view.findViewById(R.id.nom_prenom_marchand);
        adresse = view.findViewById(R.id.adresse_marchand);
        email = view.findViewById(R.id.adresse_mail_marchand);
        openDiscussions = view.findViewById(R.id.mes_messages);
        progressBar = view.findViewById(R.id.scroll_progress);
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("SetTextI18n")
    private void bindingData() {
        nomPrenom.setText(MarchandMainActivity.getMarchand().getUtilisateur().getNom() + " " +
                MarchandMainActivity.getMarchand().getUtilisateur().getPrenom());

        adresse.setText(MarchandMainActivity.getMarchand().getUtilisateur().getAdresse());
        email.setText(MarchandMainActivity.getMarchand().getUtilisateur().getEmail());
    }

    private void setlickListener() {
        openDiscussions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main_marchand, new Discussion(MarchandMainActivity.getUtilisateur().getId())).commit();
                MarchandMainActivity.getTitleFrame().setText(getContext().getResources().getString(R.string.discussion));
            }
        });
    }

    private void getListProspect(AccessToken accessToken) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getProspects(MarchandMainActivity.getMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseContrat(response.body());
                    progressBar.setVisibility(View.INVISIBLE);
                    //progressBarMain.setVisibility(View.INVISIBLE);*/
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    /*progressBarMain.setVisibility(View.INVISIBLE);
                    contentError.setVisibility(View.VISIBLE);*/
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);

                /*progressBarMain.setVisibility(View.INVISIBLE);
                layoutConnexionLose.setVisibility(View.INVISIBLE);
                layoutNodata.setVisibility(View.INVISIBLE);
                contentError.setVisibility(View.VISIBLE);
                contentMain.setVisibility(View.INVISIBLE);*/
            }
        });
    }

    private void getResponseContrat(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;
        List<Utilisateur> utilisateurs = null;

        /*TextView errorText = contentError.findViewById(R.id.error_text);
        TextView nodata = layoutNodata.findViewById(R.id.no_data);*/

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            progressBar.setVisibility(View.INVISIBLE);
            //errorText.setText(messageError);
            /*layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.INVISIBLE);
            contentError.setVisibility(View.VISIBLE);
            contentMain.setVisibility(View.INVISIBLE);*/
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            progressBar.setVisibility(View.INVISIBLE);
            //nodata.setText(message);
            /*layoutConnexionLose.setVisibility(View.INVISIBLE);
            layoutNodata.setVisibility(View.VISIBLE);
            contentError.setVisibility(View.INVISIBLE);
            contentMain.setVisibility(View.INVISIBLE);*/
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Utilisateur>>() {}.getType();

            assert outputPaginate != null;
            string = gson.toJson(outputPaginate.getObjects());
            utilisateurs = gson.fromJson(string, listType);
            ProspectsAdapter prospectsAdapter = new ProspectsAdapter(getContext(), utilisateurs);
            recyclerView.setAdapter(prospectsAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }catch (Exception e) {
            e.printStackTrace();
            progressBar.setVisibility(View.INVISIBLE);
        }


    }


}
