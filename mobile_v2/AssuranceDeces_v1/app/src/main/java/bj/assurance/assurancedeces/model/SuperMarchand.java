package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

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



    @SerializedName("userable")
    private Userable userable;



    @SerializedName("comptes")
    private List<Compte> commissions;




    @SerializedName("created_at")
    private String dateCreation;




    @SerializedName("updated_at")
    private String dateModification;









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





    public Userable getUserable() {
        return userable;
    }

    public void setUserable(Userable userable) {
        this.userable = userable;
    }




    public List<Compte> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<Compte> commissions) {
        this.commissions = commissions;
    }




    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }



    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }






    @Override
    public String toString() {
        return "SuperMarchand{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", commission='" + commission + '\'' +
                ", marchands=" + marchands +
                ", utilisateur=" + userable +
                ", commissions=" + commissions +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateModification='" + dateModification + '\'' +
                '}';
    }




}
