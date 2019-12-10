package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class ConversationUser {

    @SerializedName("id")
    private Long id;

    @SerializedName("user")
    private Utilisateur utilisateur;

    @SerializedName("conversation")
    private Conversation conversation;

    @SerializedName("read")
    private boolean read;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
