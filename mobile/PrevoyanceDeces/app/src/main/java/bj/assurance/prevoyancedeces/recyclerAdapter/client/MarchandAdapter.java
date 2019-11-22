package bj.assurance.prevoyancedeces.recyclerAdapter.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Marchand;
import bj.assurance.prevoyancedeces.viewHolder.client.MarchandViewHolder;

public class MarchandAdapter extends RecyclerView.Adapter<MarchandViewHolder> {

    Context context;
    List<Marchand> marchands;

    public MarchandAdapter(Context context, List<Marchand> marchands) {
        this.context = context;
        this.marchands = marchands;
    }

    @NonNull
    @Override
    public MarchandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarchandViewHolder(LayoutInflater.from(context).inflate(R.layout.item_marchand,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MarchandViewHolder holder, int position) {
        holder.getNomPrenom().setText(marchands.get(position).getUtilisateur().getNom()
                + marchands.get(position).getUtilisateur().getPrenom());

        holder.getMail().setText(marchands.get(position).getUtilisateur().getEmail());

        holder.getTelephone().setText(marchands.get(position).getUtilisateur().getTelephone());
    }

    @Override
    public int getItemCount() {
        return marchands.size();
    }
}
