package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Marchand {

    @SerializedName("id")
    private Long id;

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("credit_virtuel")
    private String creditVirtuel;

    @SerializedName("commission")
    private String commission;

    @SerializedName("comptes")
    private List<Compte> commissions;

    //@SerializedName("comptes")
    private List<Compte> creditVirtuels;

    @SerializedName("super_marchand")
    private SuperMarchand superMarchand;

    @SerializedName("contrats")
    private List<Contrat> contrats;

    @SerializedName("user")
    private Utilisateur utilisateur;

    public Marchand(String matricule, String creditVirtuel, String commission, SuperMarchand superMarchand, Utilisateur utilisateur) {
        this.matricule = matricule;
        this.creditVirtuel = creditVirtuel;
        this.commission = commission;
        this.superMarchand = superMarchand;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCreditVirtuel() {
        return creditVirtuel;
    }

    public void setCreditVirtuel(String creditVirtuel) {
        this.creditVirtuel = creditVirtuel;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public SuperMarchand getSuperMarchand() {
        return superMarchand;
    }

    public void setSuperMarchand(SuperMarchand superMarchand) {
        this.superMarchand = superMarchand;
    }

    public List<Compte> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<Compte> commissions) {
        this.commissions = commissions;
    }

    public List<Compte> getCreditVirtuels() {
        return creditVirtuels;
    }

    public void setCreditVirtuels(List<Compte> creditVirtuels) {
        this.creditVirtuels = creditVirtuels;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Marchand{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", creditVirtuel='" + creditVirtuel + '\'' +
                ", commission='" + commission + '\'' +
                ", commissions=" + commissions +
                ", creditVirtuels=" + creditVirtuels +
                ", superMarchand=" + superMarchand +
                ", contrats=" + contrats +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
