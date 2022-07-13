package bj.assurance.prevoyancedeces.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;

public class MessageReceiveViewHolder extends RecyclerView.ViewHolder {

    private TextView messageBody;

    public MessageReceiveViewHolder(@NonNull View itemView) {
        super(itemView);

        messageBody = itemView.findViewById(R.id.messageReceiveTexte);
    }

    public TextView getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(TextView messageBody) {
        this.messageBody = messageBody;
    }
}
