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
    private List<Object> objects;

    @SerializedName("super_marchand")
    private SuperMarchand superMarchand;

    @SerializedName("contrats")
    private List<Contrat> contrats;

    @SerializedName("portefeuilles")
    private List<Portefeuille> portefeuilles;

    @SerializedName("user")
    private Utilisateur utilisateur;

    @SerializedName("prospects")
    private List<Utilisateur> prospects;

    public Marchand(Long id) {
        this.id = id;
    }

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

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
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

    public List<Portefeuille> getPortefeuilles() {
        return portefeuilles;
    }

    public void setPortefeuilles(List<Portefeuille> portefeuilles) {
        this.portefeuilles = portefeuilles;
    }

    public List<Utilisateur> getProspects() {
        return prospects;
    }

    public void setProspects(List<Utilisateur> prospects) {
        this.prospects = prospects;
    }

    @Override
    public String toString() {
        return "Marchand{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", creditVirtuel='" + creditVirtuel + '\'' +
                ", commission='" + commission + '\'' +
                ", objects=" + objects +
                ", superMarchand=" + superMarchand +
                ", contrats=" + contrats +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
