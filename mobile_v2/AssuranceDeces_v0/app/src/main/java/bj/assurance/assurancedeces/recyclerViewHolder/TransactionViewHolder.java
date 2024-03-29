package bj.assurance.assurancedeces.recyclerViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;


public class TransactionViewHolder extends RecyclerView.ViewHolder {



    private TextView nomPrenom, solde, date, numero;



    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_clent);
        solde = itemView.findViewById(R.id.montant_paye);
        date = itemView.findViewById(R.id.date_paiement);
        numero = itemView.findViewById(R.id.numero_contrat);

    }





    public TextView getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(TextView nomPrenom) {
        this.nomPrenom = nomPrenom;
    }



    public TextView getSolde() {
        return solde;
    }

    public void setSolde(TextView solde) {
        this.solde = solde;
    }



    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }



    public TextView getNumero() {
        return numero;
    }

    public void setNumero(TextView numero) {
        this.numero = numero;
    }


}
