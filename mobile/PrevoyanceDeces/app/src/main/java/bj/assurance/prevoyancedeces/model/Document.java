package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("id")
    private Long id;

    @SerializedName("url")
    private String url;

    @SerializedName("documentable")
    private Object documenteable;

    @SerializedName("documentable_type")
    private String documentType;

    @SerializedName("documentable_id")
    private String documentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getDocumenteable() {
        return documenteable;
    }

    public void setDocumenteable(Object contrat) {
        this.documenteable = contrat;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", documenteable=" + documenteable +
                ", documentType='" + documentType + '\'' +
                ", documentId='" + documentId + '\'' +
                '}';
    }
}
