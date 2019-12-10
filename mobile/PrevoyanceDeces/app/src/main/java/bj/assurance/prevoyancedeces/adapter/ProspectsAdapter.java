package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Utilisateur;
import bj.assurance.prevoyancedeces.viewHolder.ProspectsViewHolder;

public class ProspectsAdapter extends RecyclerView.Adapter<ProspectsViewHolder> {

    Context context;
    private List<Utilisateur> utilisateurs;

    public ProspectsAdapter(Context context, List<Utilisateur> utilisateurs) {
        this.context = context;
        this.utilisateurs = utilisateurs;
    }

    @NonNull
    @Override
    public ProspectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProspectsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_marchands, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProspectsViewHolder holder, int position) {
        holder.getNomPrenom().setText(utilisateurs.get(position).getNom() + " " +
                utilisateurs.get(position).getPrenom());
        holder.getTelephone().setText(utilisateurs.get(position).getTelephone());
    }

    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }
}
