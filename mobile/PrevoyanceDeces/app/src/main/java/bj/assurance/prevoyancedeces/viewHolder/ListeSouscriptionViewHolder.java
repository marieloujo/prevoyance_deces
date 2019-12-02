package bj.assurance.prevoyancedeces.viewHolder;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class ListeSouscriptionViewHolder extends RecyclerView.ViewHolder {

    TextView nomPrenom, numeroAssurance, statusAssurance, portefeuil;


    public ListeSouscriptionViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_assure);
        numeroAssurance = itemView.findViewById(R.id.numero_assurance);
        statusAssurance = itemView.findViewById(R.id.status_assurance);
        portefeuil = itemView.findViewById(R.id.portefeuil_assurance);
    }

    public TextView getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(TextView nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public TextView getNumeroAssurance() {
        return numeroAssurance;
    }

    public void setNumeroAssurance(TextView numeroAssurance) {
        this.numeroAssurance = numeroAssurance;
    }

    public TextView getStatusAssurance() {
        return statusAssurance;
    }

    public void setStatusAssurance(TextView statusAssurance) {
        this.statusAssurance = statusAssurance;
    }

    public TextView getPortefeuil() {
        return portefeuil;
    }

    public void setPortefeuil(TextView portefeuil) {
        this.portefeuil = portefeuil;
    }
}
