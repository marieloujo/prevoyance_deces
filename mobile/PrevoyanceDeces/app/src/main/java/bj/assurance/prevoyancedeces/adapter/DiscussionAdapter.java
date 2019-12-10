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
import bj.assurance.prevoyancedeces.viewHolder.DiscussionViewHolder;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionViewHolder> {

    Context context;
    List<ConversationUser> conversationUsers;

    public DiscussionAdapter(Context context, List<ConversationUser> messages) {
        this.context = context;
        this.conversationUsers = messages;
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

        holder.getNomPrenomMessager().setText(conversationUsers.get(position).getConversation().getMessages()
                .get(0).getUtilisateur().getNom() + " " +
                conversationUsers.get(position).getConversation().getMessages().get(0).getUtilisateur().getPrenom());

        holder.getDateEnvoieMessage().setText(dateFormat.format(conversationUsers.get(position).getConversation().getMessages()
                .get(0).getDateCreation()));

        holder.getContenueMessage().setText(conversationUsers.get(position).getConversation()
                .getMessages().get(0).getBody().substring(0, 100) + " ...");
    }

    @Override
    public int getItemCount() {
        return conversationUsers.size();
    }
}
