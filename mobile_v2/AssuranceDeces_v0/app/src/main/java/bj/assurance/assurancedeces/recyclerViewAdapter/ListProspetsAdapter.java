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
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.recyclerViewHolder.ListClientViewHolder;




public class ListProspetsAdapter  extends RecyclerView.Adapter<ListClientViewHolder> {


    private Context context;
    private List<Utilisateur> utilisateurs =  new ArrayList<>();




    public ListProspetsAdapter(Context context, List<Utilisateur> utilisateurs) {
        this.context = context;
        this.utilisateurs = utilisateurs;
    }






    @NonNull
    @Override
    public ListClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ListClientViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_clients, parent, false)
        );

    }





    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListClientViewHolder holder, int position) {

        holder.getNomPrenom().setText(utilisateurs.get(position).getNom() + " " +
                        utilisateurs.get(position).getPrenom());


        holder.getTelephone().setText(utilisateurs.get(position).getTelephone());


    }




    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }




}
