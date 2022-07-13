package bj.assurance.assurancedeces.recyclerViewAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.recyclerViewHolder.ListClientViewHolder;
import bj.assurance.assurancedeces.recyclerViewHolder.ListContratViewHolder;

public class ListeClientAdapter extends ExpandableRecyclerAdapter<Client, Contrat, ListClientViewHolder, ListContratViewHolder> {


    private Context context;
    private List<Client> clients = new ArrayList<>();


    public ListeClientAdapter(@NonNull List<Client> parentList, Context context) {
        super(parentList);

        clients = parentList;

        this.context  = context;
    }




    @NonNull
    @Override
    public ListClientViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {

        return new ListClientViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_clients,
                                parentViewGroup,
                                false)
        );

    }

    @NonNull
    @Override
    public ListContratViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {


        return new ListContratViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_contrat,
                                childViewGroup,
                                false)
        );


    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindParentViewHolder(@NonNull ListClientViewHolder parentViewHolder, int parentPosition, @NonNull Client parent) {


        parentViewHolder.getNomPrenom().setText(

                parent.getUtilisateur().getNom()
                    + " " +
                parent.getUtilisateur().getPrenom()
        );


    }



    @Override
    public void onBindChildViewHolder(@NonNull ListContratViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Contrat child) {


        childViewHolder.getNomPrenomAssure().setText(

                clients.get(parentPosition).getContrats().get(childPosition).getAssurer().getUtilisateur().getNom()
        );


    }



    @Override
    public int getItemCount() {
        return clients.size();
    }


}




