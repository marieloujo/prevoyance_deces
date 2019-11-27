package bj.assurance.prevoyancedeces.fragment.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Client;
import bj.assurance.prevoyancedeces.recyclerAdapter.client.SouscriptionAdapter;
import bj.assurance.prevoyancedeces.retrofit.RetrofitClientInstance;
import bj.assurance.prevoyancedeces.retrofit.Service.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonCarnet extends Fragment {

    private TextView nomPrenomClient, professionClient, nbSouscription, dureeTotale, solde;
    private Client client;
    private RecyclerView recyclerView;
    private SouscriptionAdapter souscriptionAdapter;

    public MonCarnet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_mon_carnet, container, false);

        nomPrenomClient = view.findViewById(R.id.nom_prenom_client);
        professionClient = view.findViewById(R.id.profession_client);
        nbSouscription = view.findViewById(R.id.nb_souscription);
        dureeTotale = view.findViewById(R.id.duree_souscription);
        solde = view.findViewById(R.id.solde_souscription);

        recyclerView = view.findViewById(R.id.recycler_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(linearLayoutManager);

        getClient();

        return view ;
    }

    public void setValue() {
        nomPrenomClient.setText(client.getUtilisateur().getNom() + client.getUtilisateur().getPrenom());
        professionClient.setText(client.getProfession());
    }

    public void getClient(){
        ClientService service = RetrofitClientInstance.getRetrofitInstance().create(ClientService.class);
        Call<Client> call = service.getClientbyId();

        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                client = response.body();
                Toast.makeText(getContext(), client.toString(),Toast.LENGTH_LONG).show();

                setValue();
            }
            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(getContext(), "failure", Toast.LENGTH_LONG).show();
            }
        });
    }

}
