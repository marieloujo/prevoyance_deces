package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    private Long id;

    @SerializedName("body")
    private String body;

    @SerializedName("conversation")
    private Conversation conversation;

    @SerializedName("created_at")
    private String dateCreation;

    @SerializedName("updated_at")
    private String dateModification;

    @SerializedName("notification")
    private boolean notification = false;

    @SerializedName("user")
    private Utilisateur utilisateur;


    public Message(String body, Utilisateur utilisateur) {
        this.body = body;
        this.utilisateur = utilisateur;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", conversation=" + conversation +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateModification='" + dateModification + '\'' +
                ", notification=" + notification +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
