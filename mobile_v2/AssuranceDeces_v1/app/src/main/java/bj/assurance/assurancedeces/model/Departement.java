package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Departement {

    @SerializedName("id")
    private Long id;

    @SerializedName("code")
    private String code;

    @SerializedName("nom")
    private String nom;

    @SerializedName("communes")
    private List<Commune> communes;









    public Departement(Long id) {
        this.id = id;
    }

    public Departement(String nom) {
        this.nom = nom;
    }





    public Departement(Long id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
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


    public String getCode() {
        return code;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    @Override
    public String toString() {
        return nom;
    }
}
