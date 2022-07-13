package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.activity.ClientActivity;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Message;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.recyclerViewHolder.ListContratViewHolder;
import bj.assurance.assurancedeces.serviceImplementation.UtilisateurServiceImplementation;
import bj.assurance.assurancedeces.utils.ShowDialog;
import bj.assurance.assurancedeces.utils.token.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ListContratAdapter extends RecyclerView.Adapter<ListContratViewHolder> {



    private Context context;
    private List<Contrat> contrats = new ArrayList<>();




    public ListContratAdapter(Context context, List<Contrat> contrats) {
        this.context = context;
        this.contrats = contrats;
    }




    @NonNull
    @Override
    public ListContratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ListContratViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_contrat, parent, false)
        );

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListContratViewHolder holder, int position) {

        final Contrat contrat = contrats.get(position);



       try {

           holder.getNomPrenomAssure().setText(
                   contrat.getAssurer().getUserable().getUtilisateur().getNom() + " " +
                           contrat.getAssurer().getUserable().getUtilisateur().getNom()
           );

       } catch (Exception e) {
           e.printStackTrace();
       }


        holder.getReferenceContrat().setText(contrat.getNumero());


        Integer portefeuil = 0, paiement = 0;

        for (int i = 0; i < contrats.size(); i++) {
            for (int j = 0; j < contrats.get(i).getTransactions().size(); j++)
                portefeuil += Integer.valueOf(contrats.get(i).getTransactions().get(j).getMontant());
        }

        paiement = 12000 * contrats.size();


        holder.getPortefeuilleContrat().setText(String.valueOf(portefeuil)+ " fcfa");

        holder.getStatutPaiement();


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");

        int monthsBetween = 0;
        try {

            monthsBetween = new Date().getMonth() - simpleDateFormat.parse(contrats.get(position).getDateEffet()).getMonth();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int montant = (monthsBetween * 1000) - portefeuil;

        if (montant > 0) {

            holder.getStatutPaiement().setText("+ " + portefeuil + " fcfa");
            holder.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.colorGreen));

        } else if (montant < 0) {

            holder.getStatutPaiement().setText("- " + montant + " fcfa");
            holder.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.red_active));

        } else {

            holder.getStatutPaiement().setText("En règle");
            holder.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.black));
        }



        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.getImageView());
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.notifier_sinistre:


                                new ShowDialog(context).showQuestionDialog("Conformation", "Voulez vous notifier le déces " +

                                contrat.getAssurer().getUserable().getUtilisateur().getPrenom() + " " + contrat.getAssurer().getUserable().getUtilisateur().getNom() +

                                " ??")
                                        .setNegativeListener(new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                lottieAlertDialog.dismiss();
                                            }
                                        })

                                        .setPositiveListener(new ClickListener() {
                                            @Override
                                            public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                                lottieAlertDialog.dismiss();

                                                new ShowDialog(context).showLoaddingDialog("Traitement de la requête",
                                                        "Notification du décès de " +
                                                contrat.getAssurer().getUserable().getUtilisateur().getPrenom() + " " + contrat.getAssurer().getUserable().getUtilisateur().getNom());

                                                sendNotification(
                                                        new  Message("ghgh", new Utilisateur((long) 81))
                                                );

                                            }
                                        })

                                        .build().show();

                              //lirePlusTards(elementList.get(i).getTitle(),elementList.get(i).getContenue(),elementList.get(i).getImage());

                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }




    private void sendNotification( Message message) {

        new UtilisateurServiceImplementation(context)
                .createNotification( message,

                    TokenManager.getInstance(
                            ((FragmentActivity)context).getSharedPreferences("prefs", MODE_PRIVATE)).
                            getToken()
                )

                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                        if (response.isSuccessful()) {

                            new ShowDialog(context).showSuccessDialog("Notification envoyé avec sccuess", "")
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })
                                    .build()
                                    .show()
                            ;


                        } else {

                            new ShowDialog(context).showErrorDialog("Echec", "", "")
                                    .setPositiveListener(new ClickListener() {
                                        @Override
                                        public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                            lottieAlertDialog.dismiss();
                                        }
                                    })
                                    .build()
                                    .show();

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

    }


    @Override
    public int getItemCount() {
        return contrats.size();
    }


}
