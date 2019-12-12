package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Conversation {

    @SerializedName("id")
    private Long id;

    @SerializedName("messages")
    private List<Message> messages;

    @SerializedName("users")
    private List<Utilisateur> utilisateurs;

    @SerializedName("conversation_users")
    private List <ConversationUser> conversationUsers;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<ConversationUser> getConversationUsers() {
        return conversationUsers;
    }

    public void setConversationUsers(List<ConversationUser> conversationUsers) {
        this.conversationUsers = conversationUsers;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", messages=" + messages +
                ", utilisateurs=" + utilisateurs +
                ", conversationUsers=" + conversationUsers +
                '}';
    }
}
