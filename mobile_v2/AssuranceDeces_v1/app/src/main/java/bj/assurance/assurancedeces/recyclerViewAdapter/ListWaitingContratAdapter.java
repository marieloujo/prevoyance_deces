package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;

import java.util.List;

import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.model.customModel.Expandable;
import bj.assurance.assurancedeces.recyclerViewHolder.ListContratViewHolder;
import bj.assurance.assurancedeces.recyclerViewHolder.ListExpandViewHolder;

public class ListWaitingContratAdapter extends ExpandableRecyclerAdapter<ListExpandViewHolder,
        ListContratViewHolder> {


    private Context context;


    public ListWaitingContratAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);

        this.context = context;
    }







    @Override
    public ListExpandViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {

        return new ListExpandViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_collapse,
                    viewGroup,
                    false)
        );

    }



    @Override
    public ListContratViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {

        return new ListContratViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_contrat_marchand,
                        viewGroup,
                        false), context
        );

    }



    @Override
    public void onBindParentViewHolder(ListExpandViewHolder listExpandViewHolder, int i, Object o) {

        listExpandViewHolder.getTextView().setText(((Expandable) o).getTitle());

    }



    @Override
    public void onBindChildViewHolder(ListContratViewHolder listContratViewHolder, int i, Object o) {

       try {

           Contrat contrat = new Gson().fromJson(new Gson().toJson(o), Contrat.class);


           listContratViewHolder.onBindWaitingContrat(contrat);

       } catch (Exception e) {

           String string = (String) o;

           System.out.println(string);

           listContratViewHolder.getErrorText().setVisibility(View.VISIBLE);
           ((TextView)listContratViewHolder.getErrorText().findViewById(R.id.error_text)).setText(string);


       }

    }



}
