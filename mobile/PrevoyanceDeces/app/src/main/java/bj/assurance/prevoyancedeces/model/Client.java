package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Client {

    @SerializedName("id")
    private Long id;

    @SerializedName("profession")
    private String profession;

    @SerializedName("employeur")
    private String employeur;

    @SerializedName("information_personnelle")
    private Utilisateur utilisateur;

    @SerializedName("marchand")
    private List<Marchand> marchand;

    private Date dateCreation;

    private Date dateModification;

    @SerializedName("assures")
    private List<Assurer> assurers;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmployeur() {
        return employeur;
    }

    public void setEmployeur(String employeur) {
        this.employeur = employeur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public List<Assurer> getAssurers() {
        return assurers;
    }

    public void setAssurers(List<Assurer> assurers) {
        this.assurers = assurers;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Marchand> getMarchand() {
        return marchand;
    }

    public void setMarchand(List<Marchand> marchand) {
        this.marchand = marchand;
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
