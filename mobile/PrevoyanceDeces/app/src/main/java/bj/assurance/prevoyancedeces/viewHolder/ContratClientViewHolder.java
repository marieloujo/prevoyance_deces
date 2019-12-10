package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class ContratClientViewHolder extends RecyclerView.ViewHolder {

    private TextView referenceContrat, nomPrenomAssure, nomPrenomMarchand, portefeuille,duree, impayes, texteImpayees;

    public ContratClientViewHolder(@NonNull View itemView) {
        super(itemView);

        referenceContrat = itemView.findViewById(R.id.reference_contat);
        nomPrenomAssure = itemView.findViewById(R.id.assurer_nom_prenom);
        nomPrenomMarchand = itemView.findViewById(R.id.nom_prenom_marchand);
        portefeuille = itemView.findViewById(R.id.portefeuil_assurance);
        duree = itemView.findViewById(R.id.duree);
        impayes = itemView.findViewById(R.id.impayees);
        texteImpayees = itemView.findViewById(R.id.texte_impayees);

    }

    public TextView getReferenceContrat() {
        return referenceContrat;
    }

    public void setReferenceContrat(TextView referenceContrat) {
        this.referenceContrat = referenceContrat;
    }

    public TextView getNomPrenomAssure() {
        return nomPrenomAssure;
    }

    public void setNomPrenomAssure(TextView nomPrenomAssure) {
        this.nomPrenomAssure = nomPrenomAssure;
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

    public TextView getTexteImpayees() {
        return texteImpayees;
    }

    public void setTexteImpayees(TextView texteImpayees) {
        this.texteImpayees = texteImpayees;
    }
}
