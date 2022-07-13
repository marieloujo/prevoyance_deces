package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

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


    @SerializedName("supermarchand")
    private SuperMarchand superMarchand;




    @SerializedName("contrats")
    private List<Contrat> contrats;




    @SerializedName("portefeuilles")
    private List<Portefeuille> portefeuilles;



    @SerializedName("userable")
    private Userable userable;



    @SerializedName("prospects")
    private List<Utilisateur> prospects;



    @SerializedName("created_at")
    private String dateCreation;



    @SerializedName("updated_at")
    private String dateModification;




    public Marchand(Long id) {
        this.id = id;
    }




    public Marchand(String creditVirtuel, String commission, SuperMarchand superMarchand, Userable userable) {
        this.creditVirtuel = creditVirtuel;
        this.commission = commission;
        this.superMarchand = superMarchand;
        this.userable = userable;
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




    public Userable getUserable() {
        return userable;
    }


    public void setUserable(Userable userable) {
        this.userable = userable;
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
                ", portefeuilles=" + portefeuilles +
                ", utilisateur=" + userable +
                ", prospects=" + prospects +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateModification='" + dateModification + '\'' +
                '}';
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



}
