package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Marchand {

    @SerializedName("id")
    private Long id;

    @SerializedName("matricule")
    private String matricule;

    private String creditVirtuel;

    private String commission;

    @SerializedName("information_personnelle")
    private Utilisateur utilisateur;

    private SuperMarchand superMarchand;

    private Date dateCreation;

    private Date dateModification;

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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public SuperMarchand getSuperMarchand() {
        return superMarchand;
    }

    public void setSuperMarchand(SuperMarchand superMarchand) {
        this.superMarchand = superMarchand;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
}
