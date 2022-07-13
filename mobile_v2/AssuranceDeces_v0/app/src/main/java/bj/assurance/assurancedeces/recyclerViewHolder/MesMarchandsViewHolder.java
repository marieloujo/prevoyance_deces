package bj.assurance.assurancedeces.recyclerViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;



public class MesMarchandsViewHolder extends RecyclerView.ViewHolder {



    private TextView nomprenom, matricule, nbContrat;



    public MesMarchandsViewHolder(@NonNull View itemView) {
        super(itemView);


        nomprenom = itemView.findViewById(R.id.nom_prenom_super);
        matricule = itemView.findViewById(R.id.matricule);
        nbContrat = itemView.findViewById(R.id.nd_clients);



    }









    public TextView getNomprenom() {
        return nomprenom;
    }

    public void setNomprenom(TextView nomprenom) {
        this.nomprenom = nomprenom;
    }




    public TextView getMatricule() {
        return matricule;
    }

    public void setMatricule(TextView matricule) {
        this.matricule = matricule;
    }




    public TextView getNbContrat() {
        return nbContrat;
    }

    public void setNbContrat(TextView nbContrat) {
        this.nbContrat = nbContrat;
    }



}
