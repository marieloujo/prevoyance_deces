package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.BottomSheet.ActionMarchandBottomDialogFragment;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.fragment.supermarchand.Accueil;
import bj.assurance.assurancedeces.recyclerViewHolder.MesMarchandsViewHolder;


public class MesMarchandAdapter extends RecyclerView.Adapter<MesMarchandsViewHolder> {



    private Context context;
    private List<Accueil.CustumMarchand> custumMarchands;

    public MesMarchandAdapter(Context context, List<Accueil.CustumMarchand> marchands) {
        this.context = context;
        this.custumMarchands = marchands;
    }




    @NonNull
    @Override
    public MesMarchandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MesMarchandsViewHolder(

                LayoutInflater.from(context).
                        inflate(R.layout.item_mes_marchands, parent, false)

        );


    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MesMarchandsViewHolder holder, final int position) {


        try {

            holder.getMatricule().setText(

                    custumMarchands.get(position).getMarchand().getMatricule()
            );

        }catch (Exception e) {

            e.printStackTrace();

        }


        try {

            if (custumMarchands.get(position).getClients() != -1) {

                if (custumMarchands.get(position).getClients() > 1) {

                    holder.getNbContrat().setText(
                            String.valueOf(custumMarchands.get(position).getClients()) + " clients");
                } else {

                    holder.getNbContrat().setText(
                            String.valueOf(custumMarchands.get(position).getClients()) + " client");
                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }




        try {
            holder.getNomprenom().setText(

                    custumMarchands.get(position).getMarchand().getUserable().getUtilisateur().getNom()
                            + " " +
                    custumMarchands.get(position).getMarchand().getUserable().getUtilisateur().getPrenom()

            );


        } catch (Exception e) {
            e.printStackTrace();
        }




        holder.getOnpenOption().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(new Gson().toJson(custumMarchands.get(position).getMarchand()));

                ActionMarchandBottomDialogFragment addPhotoBottomDialogFragment
                        = new ActionMarchandBottomDialogFragment(custumMarchands.get(position).getMarchand());

                addPhotoBottomDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(),
                        ActionMarchandBottomDialogFragment.TAG);

            }
        });



       if (custumMarchands.get(position).getClients() == -1) {


           holder.getCardView().setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   context.startActivity(new Intent(context, bj.assurance.assurancedeces.activity.FragmentActivity.class)
                           .putExtra("key", "Clients")
                           .putExtra("id", custumMarchands.get(position).getMarchand().getId()));

               }
           });


       }



    }






    @Override
    public int getItemCount() {
        return custumMarchands.size();
    }




}
