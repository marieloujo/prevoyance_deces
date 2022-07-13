package bj.assurance.assurancedeces.recyclerViewHolder;


import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import androidx.annotation.NonNull;
import bj.assurance.assurancedeces.R;


public class ListClientViewHolder extends ParentViewHolder {


    private TextView nomPrenom, telephone;


    public ListClientViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_client);
        telephone = itemView.findViewById(R.id.telephone_client);

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



    @Override
    public void onClick(View view) {

    }




}
