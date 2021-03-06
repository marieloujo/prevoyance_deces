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
import bj.assurance.prevoyancedeces.model.Portefeuille;
import bj.assurance.prevoyancedeces.viewHolder.TransactionViewHolder;

public class TransactionAdater extends RecyclerView.Adapter<TransactionViewHolder> {

    Context context;
    List<Portefeuille> transactions;

    public TransactionAdater(Context context, List<Portefeuille> contrats) {
        this.context = context;
        this.transactions = contrats;
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

       try {
           holder.getNomPrenom().setText(transactions.get(position).getContrat().getClient().getUtilisateur().getNom() + " " +
                   transactions.get(position).getContrat().getClient().getUtilisateur().getPrenom());

           holder.getSolde().setText(transactions.get(position).getMontant()+ " fcfa");

           holder.getDate().setText(transactions.get(position).getCreatedAt());

           holder.getNumero().setText(transactions.get(position).getContrat().getNumero());
       } catch (Exception e) {
           //e.printStackTrace();
       }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
