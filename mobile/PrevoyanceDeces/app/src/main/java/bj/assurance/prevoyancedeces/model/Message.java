package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {

    @SerializedName("id")
    private Long id;

    @SerializedName("body")
    private String body;

    @SerializedName("from_user_id")
    private Utilisateur fromUser;

    @SerializedName("to_user_id")
    private Utilisateur ToUser;

    @SerializedName("created_at")
    private Date dateCreation;

    @SerializedName("updated_at")
    private String dateModification;

    @SerializedName("notification")
    private boolean notification = false;

    @SerializedName("read_at")
    private String readAt;

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

    public Utilisateur getFromUser() {
        return fromUser;
    }

    public void setFromUser(Utilisateur fromUser) {
        this.fromUser = fromUser;
    }

    public Utilisateur getToUser() {
        return ToUser;
    }

    public void setToUser(Utilisateur toUser) {
        ToUser = toUser;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
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

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", fromUser=" + fromUser +
                ", ToUser=" + ToUser +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                ", notification=" + notification +
                ", readAt=" + readAt +
                '}';
    }
}
