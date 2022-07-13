package bj.assurance.assurancedeces.recyclerViewHolder;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Portefeuille;
import bj.assurance.assurancedeces.model.Utilisateur;
import bj.assurance.assurancedeces.serviceImplementation.SmsServiceImplementation;
import bj.assurance.assurancedeces.utils.ShowDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListContratViewHolder extends ChildViewHolder {


    private TextView nomPrenomAssure, referenceContrat, portefeuilleContrat, statutPaiement,
            statutContrat, etatContratActif, etatContratExpirer;

    private Button notifierPaiement;
    private ImageView imageView;
    private LinearLayout errorText;


    private Context context;
    private ShowDialog showdialog;


    public ListContratViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.context = context;


        nomPrenomAssure = itemView.findViewById(R.id.nom_prenom_assure);
        referenceContrat = itemView.findViewById(R.id.reference_contat);
        portefeuilleContrat = itemView.findViewById(R.id.portefeuille_contrat);
        statutPaiement = itemView.findViewById(R.id.statut_paiement);
        statutContrat = itemView.findViewById(R.id.statut_contrat);
        etatContratActif = itemView.findViewById(R.id.etatContratActif);
        etatContratExpirer = itemView.findViewById(R.id.etatContratExpirer);
        errorText = itemView.findViewById(R.id.error_text_layout);

        imageView = itemView.findViewById(R.id.item_trend_flag);

        notifierPaiement = itemView.findViewById(R.id.notifier_paiement);


    }







    @SuppressLint("SetTextI18n")
    public void onBind(Contrat contrat, final Utilisateur utilisateur) throws ParseException {

        System.out.println(utilisateur);

        showdialog = new ShowDialog(context);

        this.getReferenceContrat().setText(contrat.getNumero());

        this.getNomPrenomAssure().setText(
                contrat.getAssurer().getUserable().getUtilisateur().getNom() + " " +
                        contrat.getAssurer().getUserable().getUtilisateur().getPrenom()
        );


        Integer portefeuil = getPortefeuil(contrat.getTransactions());
        this.getPortefeuilleContrat().setText(String.valueOf(portefeuil) + " fcfa") ;



        if (contrat.isFin())  {
            this.etatContratExpirer.setVisibility(View.VISIBLE);
            this.etatContratActif.setVisibility(View.INVISIBLE);

            this.getStatutPaiement().setText("Paiement cloturé");
            this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.black_background));

        } else {

            this.etatContratExpirer.setVisibility(View.INVISIBLE);
            this.etatContratActif.setVisibility(View.VISIBLE);


            if (portefeuil == 12000) {

                this.getStatutPaiement().setText("Paiement cloturé");
                this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.colorGreen));

            } else {

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                //Date date = simpleDateFormat.parse("2020-10-15 03:42:24");

                int daysBetween = daysBeteween(dateFormat.format(simpleDateFormat.parse(contrat.getDateEffet())), dateFormat.format(new Date()));


                if (daysBetween != -1) {

                    int monthsBetween = daysBetween / 30;

                    if (monthsBetween == 0) {

                        if (portefeuil > 0) {

                            this.getStatutPaiement().setText("Avance de " + portefeuil + " fcfa");
                            this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.black_background));

                        } else if (portefeuil == 0) {

                            this.getStatutPaiement().setText("Aucun paiement");
                            this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.black_background));

                        }

                    } else {

                        final int montant = (monthsBetween * 1000) - portefeuil;


                        if (montant < 0) {

                            this.getStatutPaiement().setText("Avance de " + montant + " fcfa");
                            this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.colorGreen));

                        } else if (montant > 0) {


                            if (utilisateur != null) {

                                this.getStatutPaiement().setText("Impayée(s) " + montant / 1000 + " mois");
                                this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.red_active));

                                this.getNotifierPaiement().setVisibility(View.VISIBLE);
                                this.getNotifierPaiement().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        relancer(utilisateur, montant / 1000);
                                    }
                                });

                            }


                        } else if (montant == 0) {

                            this.getStatutPaiement().setText("En règle");
                            this.getStatutPaiement().setTextColor(context.getResources().getColor(R.color.black_background));

                        }


                    }


                }


            }


        }



    }










    @SuppressLint("SetTextI18n")
    public void onBindWaitingContrat(Contrat contrat) {



        try {

            this.getReferenceContrat().setText(contrat.getNumero());
        } catch (Exception e) {}



        try {

            this.getNomPrenomAssure().setText(
                    contrat.getAssurer().getUserable().getUtilisateur().getNom() + " " +
                            contrat.getAssurer().getUserable().getUtilisateur().getPrenom()
            );

        } catch (Exception e) {}



        try {

            this.getPortefeuilleContrat().setText(
                    contrat.getClient().getUserable().getUtilisateur().getNom() + " " +
                            contrat.getClient().getUserable().getUtilisateur().getPrenom()
            );



        } catch (Exception e) {}


        this.getStatutPaiement().setVisibility(View.INVISIBLE);
        this.getStatutContrat().setVisibility(View.VISIBLE);

    }






    private int daysBeteween(String date, String date1) {


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        System.out.println(date + " " + date1);

        try {

            Date dateAvant = sdf.parse(date);
            Date dateApres = sdf.parse(date1);

            long diff = dateApres.getTime() - dateAvant.getTime();
            int res = (int) (diff / (1000*60*60*24));

            return Math.abs(res);

        } catch (Exception e) {
            e.printStackTrace();


            return -1;
        }


    }










    private Integer getPortefeuil(List<Portefeuille> portefeuilles) {

        Integer portefeuil = 0;

        for (int j = 0; j < portefeuilles.size(); j++)

            portefeuil += Integer.valueOf(portefeuilles.get(j).getMontant());


        return portefeuil;
    }






    private void relancer(final Utilisateur utilisateur, int mois) {

        String sexe = "", string = "";

        if (utilisateur.getSexe().equals("")) {

            sexe = "Mr ";
            string = " qu'il";

        } else {

            sexe = "Mme ";
            string = " qu'elle";
        }

        final String finalSexe = sexe;
        final String finalSexe1 = sexe;
        new ShowDialog(context).showQuestionDialog("Conformation", "Voulez vous notifier à " + sexe +

                utilisateur.getNom() + " " + utilisateur.getPrenom() + string + " est en impayé ?? ")
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

                        String string = "Salut " + finalSexe1 + utilisateur.getNom() + " " + utilisateur.getPrenom() +
                                " nous tenons à vous informer que vous ";


                        sendSms("branlycaele", "jetaimebranly", "EHWLINMI",
                                "22964250705", string,"5617");

                        showdialog.showSuccessDialog("Requête traiter",
                                " Un sms ầ été envoyé au client pour le relancer par rapport au paiement")
                                .setPositiveListener(new ClickListener() {
                                    @Override
                                    public void onClick(@NotNull LottieAlertDialog lottieAlertDialog) {
                                        lottieAlertDialog.dismiss();
                                    }
                                })
                                .build().show();


                    }
                })

                .build().show();


    }











    private void sendSms(String user, String password, String from, String to, String text, String api) {

        new SmsServiceImplementation()
                .sendSms(user, password, from, to, text, api)

                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()) {

                            showdialog.getAlertDialog().dismiss();
                            System.out.println(response.body());

                        } else {

                            System.out.println(response.code());

                        }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }
                });


    }













    public TextView getNomPrenomAssure() {
        return nomPrenomAssure;
    }


    public TextView getReferenceContrat() {
        return referenceContrat;
    }



    public TextView getPortefeuilleContrat() {
        return portefeuilleContrat;
    }


    public TextView getStatutPaiement() {
        return statutPaiement;
    }


    public Button getNotifierPaiement() {
        return notifierPaiement;
    }



    public ImageView getImageView() {
        return imageView;
    }


    public TextView getStatutContrat() {
        return statutContrat;
    }


    public LinearLayout getErrorText() {
        return errorText;
    }
}
