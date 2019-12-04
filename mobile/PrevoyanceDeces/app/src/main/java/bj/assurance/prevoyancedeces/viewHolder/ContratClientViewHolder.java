package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class ContratClientViewHolder extends RecyclerView.ViewHolder {

    private TextView nomPrenomAssure, dateCreation, nomPrenomMarchand, portefeuille,duree, impayes;

    public ContratClientViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenomAssure = itemView.findViewById(R.id.assurer_nom_prenom);
        dateCreation = itemView.findViewById(R.id.date);
        nomPrenomMarchand = itemView.findViewById(R.id.nom_prenom_marchand);
        portefeuille = itemView.findViewById(R.id.portefeuil_assurance);
        duree = itemView.findViewById(R.id.duree);
        impayes = itemView.findViewById(R.id.impayees);

    }

    public TextView getNomPrenomAssure() {
        return nomPrenomAssure;
    }

    public void setNomPrenomAssure(TextView nomPrenomAssure) {
        this.nomPrenomAssure = nomPrenomAssure;
    }

    public TextView getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(TextView dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TextView getNomPrenomMarchand() {
        return nomPrenomMarchand;
    }

    public void setNomPrenomMarchand(TextView nomPrenomMarchand) {
        this.nomPrenomMarchand = nomPrenomMarchand;
    }

    public TextView getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(TextView portefeuille) {
        this.portefeuille = portefeuille;
    }

    public TextView getDuree() {
        return duree;
    }

    public void setDuree(TextView duree) {
        this.duree = duree;
    }

    public TextView getImpayes() {
        return impayes;
    }

    public void setImpayes(TextView impayes) {
        this.impayes = impayes;
    }
}
