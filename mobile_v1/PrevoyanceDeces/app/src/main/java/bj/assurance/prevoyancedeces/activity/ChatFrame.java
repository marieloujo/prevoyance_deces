package bj.assurance.prevoyancedeces.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import bj.assurance.prevoyancedeces.R;
import bj.assurance.prevoyancedeces.adapter.MessageReceiveAdapter;
import bj.assurance.prevoyancedeces.adapter.MessageSendAdapter;
import bj.assurance.prevoyancedeces.model.ConversationUser;
import bj.assurance.prevoyancedeces.model.Message;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramotion.foldingcell.FoldingCell;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatFrame extends AppCompatActivity {

    private RecyclerView sendRecycler, receiveRecycler;

    private List<Message> receive = new ArrayList<>();

    private List<Message> send = new ArrayList<>();

    private ConversationUser conversationUser ;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_frame);

        init();
        getValue();

    }

    @SuppressLint("WrongConstant")
    private void init() {
        receiveRecycler = findViewById(R.id.recycler);
        //sendRecycler = findViewById(R.id.recycler_send);
        textView = findViewById(R.id.nom_prenom_messager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatFrame.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager linearLayoutManagerReceive = new LinearLayoutManager(ChatFrame.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //sendRecycler.setLayoutManager(linearLayoutManager);
        receiveRecycler.setLayoutManager(linearLayoutManagerReceive);
    }

    private void getValue() {
        String conversation = getIntent().getExtras().getString("conversationUsers");

        Gson gson = new Gson();

        conversationUser = gson.fromJson(conversation, ConversationUser.class);

        textView.setText(conversationUser.getUtilisateur().getNom() + " " + conversationUser.getUtilisateur().getPrenom());
        //System.out.println("Chatframe: conversationUsers: " + conversationUser.toString());

        for (Message message : conversationUser.getConversation().getMessages()) {
            if (message.getUtilisateur().getId().equals(conversationUser.getUtilisateur().getId())) {
                send.add(message);
            } else receive.add(message);
        }

        System.out.println("Chatframe: receive: \n" + receive.toString() + "\n\n");
        System.out.println("Chatframe: send: \n" + send.toString() + "\n\n");


        MessageReceiveAdapter messageReceiveAdapter = new MessageReceiveAdapter(ChatFrame.this, receive);
        receiveRecycler.setAdapter(messageReceiveAdapter);

        MessageSendAdapter messageSendAdapter = new MessageSendAdapter(ChatFrame.this, send);
        //receiveRecycler.setAdapter(messageSendAdapter);

    }
}
