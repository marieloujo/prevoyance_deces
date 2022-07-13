package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Compte {

    @SerializedName("id")
    private Long id;

    @SerializedName("montant")
    private String montant;

    @SerializedName("compte")
    private String compte;

    @SerializedName("compteable")
    private Object compteable;

    @SerializedName("compteable_type")
    private String compteableType;

    @SerializedName("compteable_id")
    private Long compteableId;


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

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public Object getCompteable() {
        return compteable;
    }

    public void setCompteable(Object compteable) {
        this.compteable = compteable;
    }

    public String getCompteableType() {
        return compteableType;
    }

    public void setCompteableType(String compteableType) {
        this.compteableType = compteableType;
    }

    public Long getCompteableId() {
        return compteableId;
    }

    public void setCompteableId(Long compteableId) {
        this.compteableId = compteableId;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", montant=" + montant +
                ", compte=" + compte +
                ", compteable=" + compteable +
                ", compteableType='" + compteableType + '\'' +
                ", compteableId=" + compteableId +
                '}';
    }
}
