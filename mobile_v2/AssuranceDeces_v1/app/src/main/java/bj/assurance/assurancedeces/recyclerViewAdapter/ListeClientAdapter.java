package bj.assurance.assurancedeces.recyclerViewAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.recyclerViewHolder.ListClientViewHolder;


public class ListeClientAdapter extends RecyclerView.Adapter<ListClientViewHolder> {


    private Context context;
    private List<Client> clients = new ArrayList<>();
    private static Client idClient;


    public ListeClientAdapter(@NonNull List<Client> parentList, Context context) {

        clients = parentList;

        this.context  = context;
    }



    @NonNull
    @Override
    public ListClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ListClientViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_clients,
                                parent,
                                false));
    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListClientViewHolder holder, final int position) {

        final Client client = clients.get(position);

        try {


            holder.getNomPrenom().setText(client.getUserable().getUtilisateur().getNom() + " " +
                    client.getUserable().getUtilisateur().getPrenom());

        } catch (Exception e) {}


        try {

            holder.getTelephone().setText(client.getUserable().getUtilisateur().getTelephone());


        } catch (Exception e) {

            e.printStackTrace();

        }



    }




    @Override
    public int getItemCount() {
        return clients.size();
    }


    public static Client getIdClient() {
        return idClient;
    }

    public static void setIdClient(Client idClient) {
        ListeClientAdapter.idClient = idClient;
    }
}




