package bj.assurance.assurancedeces.model;


import com.google.gson.annotations.SerializedName;

public class Notification  {



    @SerializedName("id")
    private Long id;



    @SerializedName("type")
    private String type;



    @SerializedName("notifiable_type")
    private String notifiableType;



    @SerializedName("notifiable_id")
    private String notifiableId;



    @SerializedName("data")
    private String data;



    @SerializedName("read_at")
    private String readAt;



    @SerializedName("created_at")
    private String createdAt;



    @SerializedName("updated_at")
    private String updatedAt;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifiableType() {
        return notifiableType;
    }

    public void setNotifiableType(String notifiableType) {
        this.notifiableType = notifiableType;
    }

    public String getNotifiableId() {
        return notifiableId;
    }

    public void setNotifiableId(String notifiableId) {
        this.notifiableId = notifiableId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }










    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", notifiableType='" + notifiableType + '\'' +
                ", notifiableId='" + notifiableId + '\'' +
                ", data='" + data + '\'' +
                ", readAt='" + readAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }








}
