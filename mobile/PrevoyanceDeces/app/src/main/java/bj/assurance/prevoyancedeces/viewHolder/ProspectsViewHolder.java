package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class ProspectsViewHolder extends RecyclerView.ViewHolder {

    private TextView nomPrenom, telephone;

    public ProspectsViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_marchand);
        telephone = itemView.findViewById(R.id.numero_marchand);
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
}
