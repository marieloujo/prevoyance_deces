package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Beneficiaire {

    @SerializedName("id")
    private Long id;

    @SerializedName("user")
    private Utilisateur utilisateur;

    @SerializedName("benefices")
    private List<Benefice> benefices;

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

    public List<Benefice> getBenefices() {
        return benefices;
    }

    public void setBenefices(List<Benefice> benefices) {
        this.benefices = benefices;
    }

    @Override
    public String toString() {
        return "Beneficiaire{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", benefices=" + benefices +
                '}';
    }

    public Beneficiaire(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Beneficiaire(Utilisateur utilisateur, List<Benefice> benefices) {
        this.utilisateur = utilisateur;
        this.benefices = benefices;
    }
}
