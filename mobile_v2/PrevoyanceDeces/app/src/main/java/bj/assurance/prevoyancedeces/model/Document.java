package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("id")
    private Long id;

    @SerializedName("url")
    private String url;

    @SerializedName("documentable")
    private Object documentable;

    @SerializedName("documentable_type")
    private String documentType;

    @SerializedName("documentable_id")
    private String documentId;


    public Document(String url) {
        this.url = url;
    }

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

    public Object getDocumentable() {
        return documentable;
    }

    public void setDocumentable(Object contrat) {
        this.documentable = contrat;
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
                ", documentable=" + documentable +
                ", documentType='" + documentType + '\'' +
                ", documentId='" + documentId + '\'' +
                '}';
    }
}
