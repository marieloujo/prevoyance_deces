package bj.assurance.assurancedeces.recyclerViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;



public class ListContratViewHolder extends ChildViewHolder {


    private TextView nomPrenomAssure, referenceContrat, portefeuilleContrat, statutPaiement;
    private Button notifierPaiement;
    private ImageView imageView;



    public ListContratViewHolder(@NonNull View itemView) {
        super(itemView);


        nomPrenomAssure = itemView.findViewById(R.id.nom_prenom_assure);
        referenceContrat = itemView.findViewById(R.id.reference_contat);
        portefeuilleContrat = itemView.findViewById(R.id.portefeuille_contrat);
        statutPaiement = itemView.findViewById(R.id.statut_paiement);

        imageView = itemView.findViewById(R.id.item_trend_flag);

        notifierPaiement = itemView.findViewById(R.id.notifier_paiement);


    }




    public TextView getNomPrenomAssure() {
        return nomPrenomAssure;
    }

    public void setNomPrenomAssure(TextView nomPrenomAssure) {
        this.nomPrenomAssure = nomPrenomAssure;
    }

    public TextView getReferenceContrat() {
        return referenceContrat;
    }

    public void setReferenceContrat(TextView referenceContrat) {
        this.referenceContrat = referenceContrat;
    }

    public TextView getPortefeuilleContrat() {
        return portefeuilleContrat;
    }

    public void setPortefeuilleContrat(TextView portefeuilleContrat) {
        this.portefeuilleContrat = portefeuilleContrat;
    }

    public TextView getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(TextView statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public Button getNotifierPaiement() {
        return notifierPaiement;
    }

    public void setNotifierPaiement(Button notifierPaiement) {
        this.notifierPaiement = notifierPaiement;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
