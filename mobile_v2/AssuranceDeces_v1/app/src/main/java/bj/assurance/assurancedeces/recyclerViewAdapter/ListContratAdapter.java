package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.gson.JsonObject;
import com.labters.lottiealertdialoglibrary.ClickListener;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Contrat;
import bj.assurance.assurancedeces.model.Message;
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
                        .inflate(R.layout.item_contrat, parent, false), context
        );

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListContratViewHolder holder, int position) {

        final Contrat contrat = contrats.get(position);


        try {
            holder.onBind(contrat, new Utilisateur());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.getImageView().setVisibility(View.VISIBLE);


        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu = new PopupMenu(context,holder.getImageView());
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        makePopup(contrat, item.getItemId());

                        return false;
                    }
                });
                popupMenu.show();

            }
        });

    }









    private void makePopup(final Contrat contrat, final int id ) {

     switch (id){

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
                                                    contrat.getAssurer().getUserable().getUtilisateur().getPrenom()
                                                    + " " +
                                                    contrat.getAssurer().getUserable().getUtilisateur().getNom());

                                    /*sendNotification(
                                            new Message("ghgh", new Utilisateur((long) 81)),
                                    );*/

                                }
                            })

                            .build().show();

                    //lirePlusTards(elementList.get(i).getTitle(),elementList.get(i).getContenue(),elementList.get(i).getImage());

                    break;

            }

    }












    private static Date addMonth(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();

    }




    private void sendNotification(Message message, Long id) {

        new UtilisateurServiceImplementation(context)
                .createNotification( message, id,

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
