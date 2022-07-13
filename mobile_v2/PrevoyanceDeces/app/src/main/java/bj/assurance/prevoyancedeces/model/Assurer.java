package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assurer {

    @SerializedName("id")
    private Long id;

    @SerializedName("profession")
    private String profession;

    @SerializedName("employeur")
    private String employeur;

    @SerializedName("contrats")
    private List<Contrat> contrats;

    @SerializedName("user")
    private Utilisateur utilisateur;

    public Assurer() {
    }

    public Assurer(Long id) {
        this.id = id;
    }


    // @SerializedName("etat")
    //private boolean etat;

    public Assurer(String profession, Utilisateur utilisateur, boolean etat) {
        this.profession = profession;
        this.utilisateur = utilisateur;
        //this.etat = etat;
    }

    public Assurer(String profession, String employeur, Utilisateur utilisateur, boolean etat) {
        this.profession = profession;
        this.employeur = employeur;
        this.utilisateur = utilisateur;
        //this.etat = etat;
    }

    public Long getId() {
        return id;
    }

    public List<Contrat> getContrat() {
        return contrats;
    }

    public void setContrat(List<Contrat> contrat) {
        this.contrats = contrat;
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

    /*public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }*/

    @Override
    public String toString() {
        return "Assurer{ \n" +
                "id=" + id +
                ", profession='" + profession + '\'' +
                ", contrats=" + contrats +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
