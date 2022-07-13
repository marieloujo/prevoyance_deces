package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        try {
            holder.getNomPrenomAssure().setText(contrats.get(position).getAssurer().getUtilisateur().getNom() + " "
                    + contrats.get(position).getAssurer().getUtilisateur().getPrenom());
        }catch (Exception e) {
            e.printStackTrace(); }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            holder.getReferenceContrat().setText(contrats.get(position).getNumero());
        } catch (Exception e) {
            e.printStackTrace(); }

        try {
            holder.getNomPrenomMarchand().setText(contrats.get(position).getMarchand().getUtilisateur().getNom() + " " +
                    contrats.get(position).getMarchand().getUtilisateur().getPrenom());
        } catch (Exception e) {
            e.printStackTrace(); }


        Integer solde = 0;
        for (int j = 0; j < contrats.get(position).getTransactions().size(); j++) {
            solde += Integer.valueOf(contrats.get(position).getTransactions().get(j).getMontant());
        }
        holder.getPortefeuille().setText(String.valueOf(solde) + " fcfa");

        int monthsBetween = 0;
        try {
            monthsBetween = new Date().getMonth() - simpleDateFormat.parse(contrats.get(position).getDateEffet()).getMonth();
        } catch (ParseException e) {
            e.printStackTrace(); }

        try {
            //if (monthsBetween == 0)
            holder.getDuree().setText(monthsBetween + " mois");
        } catch (Exception e) {
            e.printStackTrace(); }

        if (monthsBetween == 0) {
            holder.getTexteImpayees().setText("En règle");
        } else {
            int mois = ((monthsBetween * 1000) - solde) / 1000;
            if (mois == 0) {
                holder.getTexteImpayees().setText("En règle");
            } else if (mois < 0) {
                holder.getTexteImpayees().setText("Impayées");
                holder.getImpayes().setText(monthsBetween + " mois");
                for (Drawable drawable : holder.getTexteImpayees().getCompoundDrawables()) {
                    if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.red_active),PorterDuff.Mode.SRC_IN));
                    }
                }
            } else {
                holder.getTexteImpayees().setText("Avance");
                holder.getImpayes().setText(monthsBetween + " mois");
            }
        }


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
