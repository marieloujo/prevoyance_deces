package bj.assurance.prevoyancedeces.viewHolder.client;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class MarchandViewHolder extends RecyclerView.ViewHolder {

    TextView nomPrenom, telephone, mail;

    public MarchandViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_marchand);
        telephone = itemView.findViewById(R.id.phone_marchand);
        mail = itemView.findViewById(R.id.mail_marchand);

    }

    public TextView getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(TextView nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public TextView getTelephone() {
        return telephone;
    }

    public void setTelephone(TextView telephone) {
        this.telephone = telephone;
    }

    public TextView getMail() {
        return mail;
    }

    public void setMail(TextView mail) {
        this.mail = mail;
    }
}
