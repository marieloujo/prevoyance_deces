package bj.assurance.assurancedeces.model;

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



    @SerializedName("userable")
    private Userable userable;








    // @SerializedName("etat")
    //private boolean etat;



    public Assurer(String profession, Userable userable, boolean etat) {
        this.profession = profession;
        this.userable = userable;
        //this.etat = etat;
    }






    public Assurer(String profession, String employeur, Userable userable, boolean etat) {
        this.profession = profession;
        this.employeur = employeur;
        this.userable = userable;
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





    public String getEmployeur() {
        return employeur;
    }

    public void setEmployeur(String employeur) {
        this.employeur = employeur;
    }





    public Userable getUserable() {
        return userable;
    }

    public void setUserable(Userable userable) {
        this.userable = userable;
    }





    /*public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }*/

    @Override
    public String toString() {
        return "Assurer{" +
                "id=" + id +
                ", profession='" + profession + '\'' +
                ", employeur='" + employeur + '\'' +
                ", contrats=" + contrats +
                ", userable=" + userable +
                '}';
    }
}
