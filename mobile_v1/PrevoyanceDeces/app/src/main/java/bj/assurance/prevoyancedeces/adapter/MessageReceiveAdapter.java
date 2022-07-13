package bj.assurance.prevoyancedeces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.model.Message;
import bj.assurance.prevoyancedeces.viewHolder.MessageReceiveViewHolder;

public class MessageReceiveAdapter extends RecyclerView.Adapter<MessageReceiveViewHolder> {

    private Context context;
    private List<Message> messages;

    public MessageReceiveAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageReceiveViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_receive, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageReceiveViewHolder holder, int position) {
        holder.getMessageBody().setText(messages.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
