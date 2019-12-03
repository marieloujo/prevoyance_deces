package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.viewHolder.ListeSouscriptionViewHolder;

public class ListeSouscriptionAdpter extends RecyclerView.Adapter<ListeSouscriptionViewHolder> {

    Context context;
    List<Contrat> contrats;

    public ListeSouscriptionAdpter(Context context, List<Contrat> contrats) {
        this.context = context;
        this.contrats = contrats;
    }

    @NonNull
    @Override
    public ListeSouscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_souscription,parent,false);
        return new ListeSouscriptionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListeSouscriptionViewHolder holder, int position) {
        try {
            holder.getNomPrenom().setText(contrats.get(position).getAssurer().getUtilisateur().getNom() + " " +
                    contrats.get(position).getAssurer().getUtilisateur().getPrenom());
            holder.getNumeroAssurance().setText(contrats.get(position).getNumeroPolice());

            Integer portefeuil = 0;

            for (int i = 0; i < contrats.get(position).getTransactions().size(); i++)
                portefeuil += Integer.valueOf(contrats.get(position).getTransactions().get(i).getMontant());

            holder.getPortefeuil().setText(portefeuil);

        } catch (Exception e) {}

    }

    @Override
    public int getItemCount() {
        return contrats.size();
    }
}
