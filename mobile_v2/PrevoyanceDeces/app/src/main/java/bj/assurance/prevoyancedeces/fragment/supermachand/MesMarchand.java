package bj.assurance.prevoyancedeces.fragment.supermachand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.client.Marchands;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;


public class MesMarchand extends Fragment {

    private ExpandingList mExpandingList;

    public MesMarchand() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mes_marchand, container, false);

        mExpandingList = view.findViewById(R.id.expanding_list_main);

        createItems(Accueil.getMarchands());

        return view;
    }

    private void getClient(AccessToken accessToken, Long id, ExpandingItem view) {
        Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(id, 1);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseClient(response.body(), view);
                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getResponseClient(JsonObject jsonObject, ExpandingItem expandingItem) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;

        try {
            error = jsonObject.getAsJsonObject("errors");
            messageError = error.get("message").getAsString();
        }catch (Exception ignored) {}

        try {
            sucess = jsonObject.getAsJsonObject("success");
            message = sucess.get("message").getAsString();
        } catch (Exception ignored) {}

        try {
            outputPaginate = new Gson().fromJson(jsonObject, OutputPaginate.class);
        } catch (Exception e) {
            e.printStackTrace();}

        try {

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Client>>(){}.getType();

            List<Client> clients = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            System.out.println("clients" + clients);
            addSubItem(clients, expandingItem);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createItems(List<Marchand> marchands) {

        for (int i = 0; i < marchands.size(); i++) {
                addItem(marchands.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    private void addItem(Marchand marchands) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout_mes_marchands);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(R.color.gery_inactive);
            item.setIndicatorIconRes(R.drawable.ic_person);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item

            try {
                ((TextView) item.findViewById(R.id.nom_prenom_marchand)).setText(marchands.getUtilisateur().getNom() + " " +
                        marchands.getUtilisateur().getPrenom());

                ((TextView) item.findViewById(R.id.numero_marchand)).setText(marchands.getUtilisateur().getTelephone());
            } catch (Exception ignored) {}

            try {
                getClient(TokenManager.getInstance(getActivity()
                        .getSharedPreferences("prefs", MODE_PRIVATE))
                        .getToken(), marchands.getId(), item);
            } catch (Exception ignored) {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void addSubItem(List<Client> clients, ExpandingItem view){
        view.createSubItems(clients.size());

        for (int i = 0; i < clients.size(); i++) {
            View subItemZero = view.getSubItemView(i);

            try {
                ((TextView) subItemZero.findViewById(R.id.nom_prenom_marchand)).setText(clients.get(i).getUtilisateur().getNom() + " " +
                        clients.get(0).getUtilisateur().getPrenom());

                ((TextView) subItemZero.findViewById(R.id.numero_marchand)).setText(clients.get(0).getUtilisateur().getTelephone());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
