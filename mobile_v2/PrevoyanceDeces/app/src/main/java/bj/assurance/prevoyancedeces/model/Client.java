package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client {

    @SerializedName("id")
    private Long id;

    @SerializedName("profession")
    private String profession;

    @SerializedName("employeur")
    private String employeur;

    @SerializedName("contrats")
    private List<Contrat> contrats = new ArrayList<>();

    @SerializedName("user")
    private Utilisateur utilisateur;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;


    public Client(Long id) {
        this.id = id;
    }

    public Client(String profession, String employeur, Utilisateur utilisateur) {
        this.profession = profession;
        this.employeur = employeur;
        this.utilisateur = utilisateur;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", profession='" + profession + '\'' +
                ", employeur='" + employeur + '\'' +
                ", contrats=" + contrats +
                ", utilisateur=" + utilisateur +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
