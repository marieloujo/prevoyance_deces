package bj.assurance.prevoyancedeces.viewHolder.client;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class SouscriptionViewHolder extends RecyclerView.ViewHolder {

    private TextView assurer, dateDebut, marchand, dateDernierPaiement, dateProchainPaiement;

    public SouscriptionViewHolder(@NonNull View itemView) {
        super(itemView);

        assurer = itemView.findViewById(R.id.nom_prenom_assure);
        dateDebut = itemView.findViewById(R.id.date_debut);
        marchand = itemView.findViewById(R.id.nom_prenom_marchand);
        dateDernierPaiement = itemView.findViewById(R.id.date_dernier_paiement);
        dateProchainPaiement = itemView.findViewById(R.id.date_prochain_paiement);

    }

    public TextView getAssurer() {
        return assurer;
    }

    public void setAssurer(TextView assurer) {
        this.assurer = assurer;
    }

    public TextView getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(TextView dateDebut) {
        this.dateDebut = dateDebut;
    }

    public TextView getMarchand() {
        return marchand;
    }

    public void setMarchand(TextView marchand) {
        this.marchand = marchand;
    }

    public TextView getDateDernierPaiement() {
        return dateDernierPaiement;
    }

    public void setDateDernierPaiement(TextView dateDernierPaiement) {
        this.dateDernierPaiement = dateDernierPaiement;
    }

    public TextView getDateProchainPaiement() {
        return dateProchainPaiement;
    }

    public void setDateProchainPaiement(TextView dateProchainPaiement) {
        this.dateProchainPaiement = dateProchainPaiement;
    }

}
