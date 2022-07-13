package bj.assurance.assurancedeces.recyclerViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;


public class ListClientViewHolder extends ParentViewHolder {


    private TextView nomPrenom, telephone;
    private ImageView itemClient;
    private  CardView cardView;


    public ListClientViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_client);
        telephone = itemView.findViewById(R.id.telephone_client);
        itemClient = itemView.findViewById(R.id.onpenOption);
        cardView = itemView.findViewById(R.id.item_client);



    }






    public ImageView getItemClient() {
        return itemClient;
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


    public CardView getCardView() {
        return cardView;
    }
}
