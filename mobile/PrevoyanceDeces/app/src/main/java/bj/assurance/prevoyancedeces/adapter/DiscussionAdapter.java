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
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.viewHolder.DiscussionViewHolder;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionViewHolder> {

    Context context;
    List<Message> messages;

    public DiscussionAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discussion, parent, false);
        return new DiscussionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.getNomPrenomMessager().setText(messages.get(position).getFromUser().getNom() + " " +
                messages.get(position).getFromUser().getPrenom());
        holder.getDateEnvoieMessage().setText(dateFormat.format(messages.get(position).getDateCreation()));

        holder.getContenueMessage().setText(messages.get(position).getBody().substring(0, 100) + " ...");
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
