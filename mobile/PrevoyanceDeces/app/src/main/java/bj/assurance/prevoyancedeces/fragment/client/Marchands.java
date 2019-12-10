package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kinda.alert.KAlertDialog;

import java.lang.reflect.Type;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Commune;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
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
    private List<Commune> communes;
    private List<Utilisateur> utilisateurs;

    private LinearLayout noDataLayout;
    private RelativeLayout connexionLose, contentEror;

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

        getCommunes(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).getToken()
        );


        return view;
    }

    public void init(View view) {
        mExpandingList = view.findViewById(R.id.expanding_list_main);
        progressBar = view.findViewById(R.id.scroll_progress);
        noDataLayout = view.findViewById(R.id.no_data_layout);
        connexionLose = view.findViewById(R.id.no_connection);
        contentEror = view.findViewById(R.id.content_error);

        noDataLayout.setVisibility(View.INVISIBLE);
        connexionLose.setVisibility(View.INVISIBLE);
        contentEror.setVisibility(View.INVISIBLE);
    }

    private void createItems(List<Commune> communes) {
        for (int i = 0; i < communes.size(); i++) {
            addItem(communes.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    private void addItem(Commune commune) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_location_black);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            ((TextView) item.findViewById(R.id.title)).setText(commune.getNom());

            try {
                getDepartementofUser(TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken(), commune.getId(), item);
            } catch (Exception e) {

            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void addSubItem(List<Utilisateur> utilisateurs, ExpandingItem view) {
        //We can create items in batch.
        view.createSubItems(utilisateurs.size());

        for (int i = 0; i < utilisateurs.size(); i++) {
            View subItemZero = view.getSubItemView(i);

            ((TextView) subItemZero.findViewById(R.id.nom_prenom_marchand)).setText(utilisateurs.get(i).getNom()+ " " +
                    utilisateurs.get(i).getPrenom());
            ((TextView) subItemZero.findViewById(R.id.numero_marchand)).setText(utilisateurs.get(i).getTelephone());

            int finalI = i;
            subItemZero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragment(new DetailMarchand(utilisateurs.get(finalI)), "Marchand "+utilisateurs.get(finalI).getPrenom());
                }
            });

        }
    }

    private void getDepartementofUser(AccessToken accessToken, Long idCommune, ExpandingItem view) {
        Call<JsonObject> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.getMarchandsofDepartement(Main2Activity.getUtilisateur().getCommune().getDepartement().getId(),idCommune);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseMarchand(response.body(), view);
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    Log.w(TAG, "onFailure: " + response.code() + "idDepartement: " +
                            Main2Activity.getUtilisateur().getCommune().getDepartement().getId() + " idCommune " +
                            idCommune);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
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

    private void getCommunes(AccessToken accessToken) {
        Call<JsonObject> call;
        ClientService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(ClientService.class);
        call = service.getCommunes(Main2Activity.getUtilisateur().getCommune().getDepartement().getId());
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseCommunes(response.body());

                } else {
                    Log.w(TAG, "onFailure: " + response.code() + "idDepartement: " +
                            Main2Activity.getUtilisateur().getCommune().getDepartement().getId());
                    progressBar.setVisibility(View.INVISIBLE);
                    connexionLose.setVisibility(View.INVISIBLE);
                    noDataLayout.setVisibility(View.INVISIBLE);
                    contentEror.setVisibility(View.VISIBLE);
                    ((TextView) contentEror.findViewById(R.id.error_text)).setText("Une erreur s'est produite lors dela récupération des communes," +
                            "veuillez réesayer");
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                connexionLose.setVisibility(View.INVISIBLE);
                noDataLayout.setVisibility(View.INVISIBLE);
                contentEror.setVisibility(View.VISIBLE);
                ((TextView) contentEror.findViewById(R.id.error_text)).setText("Une erreur s'est produite lors dela récupération des communes," +
                        "veuillez réesayer");
            }
        });
    }

    private void getResponseMarchand(JsonObject jsonObject, ExpandingItem view) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
        } catch (Exception ignored) {}

        try {
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Utilisateur>>() {}.getType();

            assert outputPaginate != null;
            String string = gson.toJson(outputPaginate.getObjects());
            utilisateurs = gson.fromJson(string, listType);
            addSubItem(utilisateurs, view);
        }catch (Exception e) {
            e.printStackTrace(); }

        System.out.println("error: " + error + ",\n" +
                "error_message: " + messageError + "\n" +
                "sucess: " + sucess + "\n" +
                "message_sucess " + message + "\n" +
                "data: " + outputPaginate + "\n" +
                "communes " + utilisateurs);

    }

    private void getResponseCommunes(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        TextView errorText = contentEror.findViewById(R.id.error_text);
        TextView nodata = noDataLayout.findViewById(R.id.no_data);

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
            errorText.setText(messageError);
            connexionLose.setVisibility(View.INVISIBLE);
            noDataLayout.setVisibility(View.INVISIBLE);
            contentEror.setVisibility(View.VISIBLE);
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
            nodata.setText(message);
            connexionLose.setVisibility(View.INVISIBLE);
            noDataLayout.setVisibility(View.VISIBLE);
            contentEror.setVisibility(View.INVISIBLE);
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Commune>>() {}.getType();

            assert outputPaginate != null;
            communes = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            createItems(communes);
            connexionLose.setVisibility(View.INVISIBLE);
            noDataLayout.setVisibility(View.INVISIBLE);
            contentEror.setVisibility(View.VISIBLE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("error: " + error + ",\n" +
            "error_message: " + messageError + "\n" +
            "sucess: " + sucess + "\n" +
            "message_sucess " + message + "\n" +
            "data: " + outputPaginate + "\n" +
            "communes " + communes);

    }




}
