package bj.assurance.prevoyancedeces.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.activity.ChatFrame;
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

        holder.getContenueMessage().setText(conversationUsers.get(position).getConversation().getMessages().get(0).getBody());

        holder.getNomPrenomMessager().setText(conversationUsers.get(position).getConversation().getMessages()
                .get(0).getUtilisateur().getNom() + " " +
                conversationUsers.get(position).getConversation().getMessages().get(0).getUtilisateur().getPrenom());

        try {
            holder.getDateEnvoieMessage().setText(dateFormat.format(dateFormat.parse(conversationUsers.get(position).getConversation().getMessages()
                    .get(0).getDateCreation())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

       if (conversationUsers.get(position).getConversation()
               .getMessages().get(0).getBody().length() >= 100) {
           holder.getContenueMessage().setText(conversationUsers.get(position).getConversation()
                   .getMessages().get(0).getBody().substring(0, 100) + " ...");
       }

       holder.getContentDiscussion().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(context, ChatFrame.class);
               intent.putExtra("conversationUsers", new Gson().toJson(conversationUsers.get(position)));
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return conversationUsers.size();
    }
}
