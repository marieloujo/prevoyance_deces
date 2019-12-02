package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class DiscussionViewHolder extends RecyclerView.ViewHolder {

    TextView nomPrenomMessager, dateEnvoieMessage, contenueMessage;

    public DiscussionViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenomMessager = itemView.findViewById(R.id.nom_prenom_messager);
        dateEnvoieMessage = itemView.findViewById(R.id.date_envoie_message);
        contenueMessage = itemView.findViewById(R.id.contenue_message);
    }

    public TextView getNomPrenomMessager() {
        return nomPrenomMessager;
    }

    public void setNomPrenomMessager(TextView nomPrenomMessager) {
        this.nomPrenomMessager = nomPrenomMessager;
    }

    public TextView getDateEnvoieMessage() {
        return dateEnvoieMessage;
    }

    public void setDateEnvoieMessage(TextView dateEnvoieMessage) {
        this.dateEnvoieMessage = dateEnvoieMessage;
    }

    public TextView getContenueMessage() {
        return contenueMessage;
    }

    public void setContenueMessage(TextView contenueMessage) {
        this.contenueMessage = contenueMessage;
    }
}
