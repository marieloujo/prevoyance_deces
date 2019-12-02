package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SuperMarchand {

    @SerializedName("id")
    private Long id;

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("commission")
    private String commission;

    @SerializedName("marchands")
    private List<Marchand> marchands;

    @SerializedName("user")
    private Utilisateur utilisateur;

    @SerializedName("comptes")
    private List<Compte> commissions;

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

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public List<Marchand> getMarchands() {
        return marchands;
    }

    public void setMarchands(List<Marchand> marchands) {
        this.marchands = marchands;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Compte> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<Compte> commissions) {
        this.commissions = commissions;
    }

    @Override
    public String toString() {
        return "SuperMarchand{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", commission='" + commission + '\'' +
                ", marchands=" + marchands +
                ", utilisateur=" + utilisateur +
                ", commissions=" + commissions +
                '}';
    }
}
