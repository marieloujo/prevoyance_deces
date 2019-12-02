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
import bj.assurance.prevoyancedeces.viewHolder.TransactionViewHolder;

public class TransactionAdater extends RecyclerView.Adapter<TransactionViewHolder> {

    Context context;
    List<Contrat> contrats;

    public TransactionAdater(Context context, List<Contrat> contrats) {
        this.context = context;
        this.contrats = contrats;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);

        return new TransactionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.getNomPrenom().setText(contrats.get(position).getClient().getUtilisateur().getNom() + " " +
                contrats.get(position).getClient().getUtilisateur().getPrenom());

        //holder.getSolde().setText(contrats.get(position).get);


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
