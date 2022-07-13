package bj.assurance.assurancedeces.recyclerViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;


public class NotificationViewHolder extends RecyclerView.ViewHolder {



    private TextView nomPrenomDateNotification, contenuNotification;



    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenomDateNotification = itemView.findViewById(R.id.nom_prenom_and_date_of_notification);
        contenuNotification = itemView.findViewById(R.id.contenu_notification);

    }




    public TextView getNomPrenomDateNotification() {
        return nomPrenomDateNotification;
    }

    public void setNomPrenomDateNotification(TextView nomPrenomDateNotification) {
        this.nomPrenomDateNotification = nomPrenomDateNotification;
    }

    public TextView getContenuNotification() {
        return contenuNotification;
    }

    public void setContenuNotification(TextView contenuNotification) {
        this.contenuNotification = contenuNotification;
    }


}
