package bj.assurance.assurancedeces.recyclerViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import bj.assurance.assurancedeces.R;


public class ListProspectViewHolder extends ChildViewHolder {


    private final ImageView itemClient;
    private TextView nomPrenom, telephone;

    private LinearLayout contentMain, errorLayout;


    public ListProspectViewHolder(@NonNull View itemView) {
        super(itemView);

        nomPrenom = itemView.findViewById(R.id.nom_prenom_client);
        telephone = itemView.findViewById(R.id.telephone_client);
        itemClient = itemView.findViewById(R.id.onpenOption);


        contentMain = itemView.findViewById(R.id.layout_content);
        errorLayout = itemView.findViewById(R.id.error_text_layout);


    }







    public TextView getNomPrenom() {
        return nomPrenom;
    }

    public TextView getTelephone() {
        return telephone;
    }

    public void setTelephone(TextView telephone) {
        this.telephone = telephone;
    }


    public LinearLayout getContentMain() {
        return contentMain;
    }

    public LinearLayout getErrorLayout() {
        return errorLayout;
    }


    public ImageView getItemClient() {
        return itemClient;
    }
}
