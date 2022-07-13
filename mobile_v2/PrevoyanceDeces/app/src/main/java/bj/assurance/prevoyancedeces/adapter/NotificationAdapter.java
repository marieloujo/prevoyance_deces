package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.ConversationUser;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.viewHolder.NotificationViewHolder;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    Context context;
    List<ConversationUser> notifications;

    public NotificationAdapter(Context context, List<ConversationUser> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        try {
            holder.getContenuNotification().setText(notifications.get(position).getConversation().getMessages().get(0).getBody());
            holder.getNomPrenomDateNotification().setText(notifications.get(position).getConversation().getMessages().get(0).getUtilisateur().getNom() + " " +
                    notifications.get(position).getConversation().getMessages().get(0).getUtilisateur().getPrenom() + " " +
                    (notifications.get(position).getConversation().getMessages().get(0).getDateModification()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
