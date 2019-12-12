package bj.assurance.prevoyancedeces.fragment.supermachand;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.MarchandMainActivity;
import bj.assurance.prevoyancedeces.activity.SuperMarchandMainActivity;
import bj.assurance.prevoyancedeces.adapter.ListeSouscriptionAdpter;
import bj.assurance.prevoyancedeces.adapter.MesMarchandAdapter;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.model.pagination.OutputPaginate;
import bj.assurance.prevoyancedeces.retrofit.RetrofitBuildForGetRessource;
import bj.assurance.prevoyancedeces.retrofit.Service.MarchandService;
import bj.assurance.prevoyancedeces.retrofit.Service.SuperMarchandService;
import bj.assurance.prevoyancedeces.retrofit.TokenManager;
import bj.assurance.prevoyancedeces.utils.AccessToken;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;


public class Accueil extends Fragment {

    private TextView nomPrenom, matricule, soldeCommission, viewMore;
    private RecyclerView recyclerView;

    private static List<Marchand> marchands = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private List<CustumMarchand> custumMarchands = new ArrayList<>();
    private MesMarchandAdapter mesMarchandAdapter ;

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

        View view = inflater.inflate(R.layout.fragment_accueil_supermarchand, container, false);

        init(view);
        bindingata();
        setClickListener();
        getMarchand(
            TokenManager.getInstance(getActivity().
                getSharedPreferences("prefs", MODE_PRIVATE)).
                getToken()
        );

        return view;
    }

    @SuppressLint("WrongConstant")
    public void init(View v) {
        nomPrenom = v.findViewById(R.id.nom_prenom_super);
        matricule = v.findViewById(R.id.matricule_super_marchand);
        soldeCommission = v.findViewById(R.id.commissions_super_marchand);
        viewMore = v.findViewById(R.id.view_more);
        recyclerView = v.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mesMarchandAdapter = new MesMarchandAdapter(getContext(), custumMarchands);
        recyclerView.setAdapter(mesMarchandAdapter);
    }

    public void setClickListener() {
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new MesMarchand()).commit();
                SuperMarchandMainActivity.getTitleFrame().setText(getContext().getResources().getString(R.string.historique));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void bindingata() {
        nomPrenom.setText(SuperMarchandMainActivity.getUtilisateur().getNom() + " " +
            SuperMarchandMainActivity.getUtilisateur().getPrenom());
        matricule.setText(SuperMarchandMainActivity.getSuperMarchand().getMatricule());

        getCommission(
                TokenManager.getInstance(getActivity().
                        getSharedPreferences("prefs", MODE_PRIVATE)).
                        getToken()
        );
    }

    private void getMarchand(AccessToken accessToken) {
        retrofit2.Call<JsonObject> call;
        SuperMarchandService superMarchandService = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(SuperMarchandService.class);
        call = superMarchandService.getMarhands(SuperMarchandMainActivity.getSuperMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseContrat(response.body());
                } else System.out.println(response.code());
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void getResponseContrat(JsonObject jsonObject) {
        JsonObject error = null, sucess = null;
        String messageError = null, message = null;
        OutputPaginate outputPaginate = null;
        String string = null, s = null;

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
        } catch (Exception ignored) {
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Marchand>>() {}.getType();

            assert outputPaginate != null;
            string = gson.toJson(outputPaginate.getObjects());
            marchands = gson.fromJson(string, listType);
            marchands.remove(null);
            System.out.println(marchands.size());
            createCustumMarchand();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getClient(AccessToken accessToken, Marchand marchand) {
        retrofit2.Call<JsonObject> call;
        MarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(MarchandService.class);
        call = service.getClients(marchand.getId(), 1);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                   // System.out.println(response.body());
                    getResponseClient(response.body(), marchand);
                } else {
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getResponseClient(JsonObject jsonObject, Marchand marchand) {
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

            clients = gson.fromJson(gson.toJson(outputPaginate.getObjects()), listType);
            custumMarchands.add(new CustumMarchand(marchand, clients.size()));

            mesMarchandAdapter.notifyDataSetChanged();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCustumMarchand() {
        for (Marchand marchand : marchands) {
               getClient(TokenManager.getInstance(getActivity().
                       getSharedPreferences("prefs", MODE_PRIVATE)).
                       getToken(), marchand);
        }

        System.out.println("custumMarchands \n" + custumMarchands);
    }

    private void getCommission(AccessToken accessToken) {
        retrofit2.Call<JsonObject> call;
        SuperMarchandService service = new RetrofitBuildForGetRessource(accessToken).getRetrofit().create(SuperMarchandService.class);
        call = service.getCommission(SuperMarchandMainActivity.getSuperMarchand().getId());
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    getResponseCommission(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getResponseCommission(JsonObject jsonObject) {
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

            Gson gson = new Gson();

            Compte compte = new Gson().fromJson(sucess.get("commission"), Compte.class);

            soldeCommission.setText(compte.getMontant() + " coins");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CustumMarchand  {
        private Marchand marchand;
        private int clients;

        public CustumMarchand(Marchand marchand, int clients) {
            this.marchand = marchand;
            this.clients = clients;
        }

        public int getClients() {
            return clients;
        }

        public void setClients(int clients) {
            this.clients = clients;
        }

        public Marchand getMarchand() {
            return marchand;
        }

        public void setMarchand(Marchand marchand) {
            this.marchand = marchand;
        }

        @Override
        public String toString() {
            return "CustumMarchand{" +
                    "marchand=" + marchand +
                    ", clients=" + clients +
                    '}';
        }
    }

    public static List<Marchand> getMarchands() {
        return marchands;
    }

    public static void setMarchands(List<Marchand> marchands) {
        Accueil.marchands = marchands;
    }

}
