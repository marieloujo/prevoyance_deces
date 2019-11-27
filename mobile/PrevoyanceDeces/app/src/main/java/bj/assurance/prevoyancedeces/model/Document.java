package bj.assurance.prevoyancedeces.model;

public class Document {

    private Long id;

    private String url;

    private Assurer assurer;

    private Client client;

    private String documentType;

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

    public Assurer getAssurer() {
        return assurer;
    }

    public void setAssurer(Assurer assurer) {
        this.assurer = assurer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
