package bj.assurance.assurancedeces.recyclerViewAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.BottomSheet.ActionBottomDialogFragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.Expandable;
import bj.assurance.assurancedeces.recyclerViewHolder.ListClientViewHolder;
import bj.assurance.assurancedeces.recyclerViewHolder.ListExpandViewHolder;
import bj.assurance.assurancedeces.recyclerViewHolder.ListProspectViewHolder;


public class ListProspetsAdapter extends ExpandableRecyclerAdapter<ListExpandViewHolder, ListProspectViewHolder> {


    private Context context;


    public ListProspetsAdapter(Context context, List<ParentObject> utilisateurs) {
        super(context, utilisateurs);


        this.context = context;
    }






    @Override
    public ListExpandViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {

        return new ListExpandViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_collapse, viewGroup, false)
        );

    }




    @Override
    public ListProspectViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {

        return new ListProspectViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_clients, viewGroup, false)
        );

    }




    @Override
    public void onBindParentViewHolder(ListExpandViewHolder listExpandViewHolder, int i, Object o) {


        listExpandViewHolder.getTextView().setText(( (Expandable) o).getTitle());



    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onBindChildViewHolder(ListProspectViewHolder listProspectViewHolder, int i, Object o) {


        try {

            final Utilisateur utilisateurs = new Gson().fromJson(new Gson().toJson(o), Utilisateur.class);

            listProspectViewHolder.getNomPrenom().setText(utilisateurs.getNom() + " " +
                    utilisateurs.getPrenom());


            listProspectViewHolder.getTelephone().setText(utilisateurs.getTelephone());


            listProspectViewHolder.getItemClient().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showBottomSheet(utilisateurs);


                }
            });


        } catch (Exception e) {

            String s = (String) o;

            System.out.println(s);

            listProspectViewHolder.getContentMain().setVisibility(View.INVISIBLE);
            listProspectViewHolder.getErrorLayout().setVisibility(View.VISIBLE);
            ((TextView) listProspectViewHolder.getErrorLayout().findViewById(R.id.error_text)).setText(s);

        }


    }





    private void showBottomSheet(Utilisateur utilisateur) {

        ActionBottomDialogFragment addPhotoBottomDialogFragment = new ActionBottomDialogFragment(utilisateur);

        addPhotoBottomDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
    }





}
