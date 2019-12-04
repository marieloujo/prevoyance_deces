package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Compte;
import bj.assurance.prevoyancedeces.model.Contrat;
import bj.assurance.prevoyancedeces.viewHolder.ContratClientViewHolder;

public class ContratClientAdapter extends RecyclerView.Adapter<ContratClientViewHolder> {

    Context context;
    List<Contrat> contrats;

    public ContratClientAdapter(Context context, List<Contrat> contrats) {
        this.context = context;
        this.contrats = contrats;
    }

    @NonNull
    @Override
    public ContratClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_contrat, parent, false);

        return new ContratClientViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContratClientViewHolder holder, int position) {


            holder.getNomPrenomAssure().setText(contrats.get(position).getAssurer().getUtilisateur().getNom() + " "
                    + contrats.get(position).getAssurer().getUtilisateur().getPrenom());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


            try {
                holder.getDateCreation().setText(simpleDateFormat.format(contrats.get(position).getDateDebut()));
            } catch (Exception e) {}

            try {
                holder.getDuree().setText(simpleDateFormat.format(contrats.get(position).getDateDebut()) + " " +
                        simpleDateFormat.format(contrats.get(position).getDateFin()));
            } catch (Exception e) {}

            try {
                Integer portefeuil = 0;
                for (int i = 0; i < contrats.get(position).getTransactions().size(); i++)
                    portefeuil += Integer.valueOf(contrats.get(position).getTransactions().get(i).getMontant());

                holder.getPortefeuille().setText(portefeuil);

            } catch (Exception e) {

            }
            try {
                holder.getNomPrenomMarchand().setText(contrats.get(position).getMarchand().getUtilisateur().getNom() + " " +
                        contrats.get(position).getMarchand().getUtilisateur().getPrenom());
            } catch (Exception e) {

            }

       /* try {
            //long months = getMonthBetween(contrats.get(position).getDateEffet());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public int getItemCount() {
        return contrats.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getMonthBetween(String olderDate) throws ParseException {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        Date date = simpleDateFormat.parse(olderDate);

        long monthsBetween = ChronoUnit.MONTHS.between(
                LocalDate.parse(simpleDateFormat.format(date)).withDayOfMonth(1),
                LocalDate.parse(simpleDateFormat.format(new Date())).withDayOfMonth(1));
        System.out.println(monthsBetween);

        return monthsBetween;
    }
}
