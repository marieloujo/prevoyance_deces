package bj.assurance.assurancedeces.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Commune {

    @SerializedName("id")
    private Long id;

    @SerializedName("nom")
    private String nom;

    @SerializedName("departement")
    private Departement departement;

    @SerializedName("users")
    private List<Utilisateur> utilisateurs;




    public Commune() {
    }




    public Commune(String nom) {
        this.nom = nom;
    }




    public Commune(Long id, String nom, Long idDepartement) {


        this.id = id;
        this.nom = nom;
        this.departement = new Departement(idDepartement);


    }












    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public String toString() {
        return  nom ;
    }
}
