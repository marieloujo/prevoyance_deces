package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Portefeuille {

    @SerializedName("id")
    private Long id;

    @SerializedName("montant")
    private String montant;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("update_at")
    private String updateAt;

    @SerializedName("contrat")
    private Contrat contrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "id=" + id +
                ", montant='" + montant + '\'' +
                ", contrat=" + contrat +
                '}';
    }
}
