package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.fragment.supermachand.Accueil;
import bj.assurance.prevoyancedeces.viewHolder.MesMarchandsViewHolder;

public class MesMarchandAdapter extends RecyclerView.Adapter<MesMarchandsViewHolder> {

    Context context;
    List<Accueil.CustumMarchand> custumMarchands;

    public MesMarchandAdapter(Context context, List<Accueil.CustumMarchand> marchands) {
        this.context = context;
        this.custumMarchands = marchands;
    }

    @NonNull
    @Override
    public MesMarchandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MesMarchandsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mes_marchands, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MesMarchandsViewHolder holder, int position) {
        try {
            holder.getMatricule().setText(custumMarchands.get(position).getMarchand().getMatricule());
            holder.getNbContrat().setText(String.valueOf(custumMarchands.get(position).getClients()) + " clients");
            holder.getNomprenom().setText(custumMarchands.get(position).getMarchand().getUtilisateur().getNom() + " " +
                    custumMarchands.get(position).getMarchand().getUtilisateur().getPrenom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return custumMarchands.size();
    }
}
