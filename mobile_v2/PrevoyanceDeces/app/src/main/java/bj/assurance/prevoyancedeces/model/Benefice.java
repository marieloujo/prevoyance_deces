package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Benefice {

    @SerializedName("id")
    private Long id;

    @SerializedName("statut")
    private String statut;

    @SerializedName("taux")
    private String taux;

    @SerializedName("beneficiaire")
    private Beneficiaire beneficiaire;

    @SerializedName("contrat")
    private Contrat contrat;

    public Benefice(String statut, String taux, Beneficiaire beneficiaire) {
        this.statut = statut;
        this.taux = taux;
        this.beneficiaire = beneficiaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTaux() {
        return taux;
    }

    public void setTaux(String taux) {
        this.taux = taux;
    }

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    @Override
    public String toString() {
        return "Benefice{" +
                "id=" + id +
                ", statut='" + statut + '\'' +
                ", taux='" + taux + '\'' +
                ", beneficiaire=" + beneficiaire +
                ", contrat=" + contrat +
                '}';
    }
}
