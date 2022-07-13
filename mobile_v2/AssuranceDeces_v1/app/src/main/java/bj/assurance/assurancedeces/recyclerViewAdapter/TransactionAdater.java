package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.recyclerViewHolder.TransactionViewHolder;


public class TransactionAdater extends RecyclerView.Adapter<TransactionViewHolder> {



    private Context context;
    private List<Portefeuille> transactions;



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

           holder.getNomPrenom().setText(
                   transactions.get(position).getContrat().getClient().getUserable().getUtilisateur().getNom()
                           + " " +
                           transactions.get(position).getContrat().getClient().getUserable().getUtilisateur().getPrenom()
           );

       } catch (Exception e) {

           e.printStackTrace();

       }


       try {
           holder.getSolde().setText(
                   transactions.get(position).getMontant()
                           + " fcfa"
           );

        } catch (Exception e) {

            e.printStackTrace();

        }

        try {
           holder.getDate().setText(
                   transactions.get(position).getCreatedAt()
           );


        } catch (Exception e) {

                e.printStackTrace();

        }

        try {
           holder.getNumero().setText(
                   transactions.get(position).getContrat().getNumero()
           );

       } catch (Exception e) {

           e.printStackTrace();

       }

    }




    @Override
    public int getItemCount() {
        return transactions.size();
    }



}
