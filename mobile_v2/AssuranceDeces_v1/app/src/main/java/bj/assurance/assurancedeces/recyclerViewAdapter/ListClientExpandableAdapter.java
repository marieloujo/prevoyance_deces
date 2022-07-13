package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arthurivanets.bottomsheets.BaseBottomSheet;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.BottomSheet.ActionBottomDialogFragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.recyclerViewHolder.ListClientViewHolder;
import bj.assurance.assurancedeces.recyclerViewHolder.ListContratViewHolder;

public class ListClientExpandableAdapter extends ExpandableRecyclerAdapter<ListClientViewHolder,
        ListContratViewHolder> {



    private Context context ;
    private LayoutInflater inflater;
    private static Utilisateur utilisateur = new Utilisateur();




    public ListClientExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);

        this.context = context;

        try {

            inflater = LayoutInflater.from(context);

        } catch (Exception ignored) {}

    }





    @Override
    public ListClientViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {

        return new ListClientViewHolder(
                inflater.inflate(R.layout.item_clients,
                viewGroup,
                false)
        );

    }



    @Override
    public ListContratViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {

        return new ListContratViewHolder(
                inflater.inflate(R.layout.item_contrat_marchand,
                viewGroup,
                false), context
        );

    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindParentViewHolder(ListClientViewHolder listClientViewHolder, int i, Object o) {


        final Client client = (Client) o;
        utilisateur = client.getUserable().getUtilisateur();

        listClientViewHolder.getNomPrenom().setText(client.getUserable().getUtilisateur().getNom() +
            " " + client.getUserable().getUtilisateur().getPrenom());


        listClientViewHolder.getTelephone().setText(client.getUserable().getUtilisateur().getTelephone());


        listClientViewHolder.getItemClient().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBottomSheet();


            }
        });

    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onBindChildViewHolder(ListContratViewHolder listContratViewHolder, int i, Object o) {

        try {
            System.out.println(o);

            Contrat contrat  = new Gson().fromJson(new Gson().toJson(o), Contrat.class);

            try {

                listContratViewHolder.onBind(contrat, utilisateur);


            } catch (ParseException e) {

                e.printStackTrace();

            }

        } catch (Exception e) {

            String s = (String) o;


            System.out.println(s);

            listContratViewHolder.getErrorText().setVisibility(View.VISIBLE);
            ((TextView)listContratViewHolder.getErrorText().findViewById(R.id.error_text)).setText(s);



        }

    }


    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }








    private void showBottomSheet() {

        ActionBottomDialogFragment addPhotoBottomDialogFragment = new ActionBottomDialogFragment(utilisateur);

        addPhotoBottomDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
    }









}
