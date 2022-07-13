package bj.assurance.assurancedeces.model;


import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Client implements Parent<Contrat> {




    @SerializedName("id")
    private Long id;



    @SerializedName("profession")
    private String profession;



    @SerializedName("employeur")
    private String employeur;



    @SerializedName("contrats")
    private List<Contrat> contrats = new ArrayList<>();




    @SerializedName("userable")
    private Userable userable;




    @SerializedName("created_at")
    private String createdAt;



    @SerializedName("updated_at")
    private String updatedAt;



    private Utilisateur utilisateur;





    public Client(String profession, String employeur, Userable userable) {
        this.profession = profession;
        this.employeur = employeur;
        this.userable = userable;
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





    public Userable getUserable() {
        return userable;
    }

    public void setUserable(Userable userable) {
        this.userable = userable;
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




    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }




    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", profession='" + profession + '\'' +
                ", employeur='" + employeur + '\'' +
                ", contrats=" + contrats +
                ", userable=" + userable +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }


    @Override
    public List<Contrat> getChildList() {
        return this.contrats;
    }



    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


}
