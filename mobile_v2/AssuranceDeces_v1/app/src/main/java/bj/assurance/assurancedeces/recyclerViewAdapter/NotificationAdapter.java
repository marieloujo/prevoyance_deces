package bj.assurance.assurancedeces.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.assurancedeces.R;
import bj.assurance.assurancedeces.model.Notification;
import bj.assurance.assurancedeces.recyclerViewHolder.NotificationViewHolder;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private Context context;
    private List<Notification> notifications;



    public NotificationAdapter(Context context, List<Notification> notifications) {
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

            holder.getContenuNotification().setText(notifications.get(position)
                    .getData());


            holder.getNomPrenomDateNotification().setText(notifications.get(position).getCreatedAt());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
