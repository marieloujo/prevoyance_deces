package bj.assurance.assurancedeces.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;




public class Client implements ParentObject {




    @SerializedName("id")
    private Long id;



    @SerializedName("profession")
    private String profession;



    @SerializedName("employeur")
    private String employeur;



    @SerializedName("contrats")
    private List<Object> contrats = new ArrayList<>();




    @SerializedName("userable")
    private Userable userable;




    @SerializedName("created_at")
    private String createdAt;



    @SerializedName("updated_at")
    private String updatedAt;




    public Client() {
    }




    public Client(Long id, String profession, String employeur, Userable userable) {
        this.id = id;
        this.profession = profession;
        this.employeur = employeur;
        this.userable = userable;
        this.userable.setUtilisateur(userable.getUtilisateur());
    }




    public Client(String profession, String employeur, Utilisateur utilisateur) {
        this.profession = profession;
        this.employeur = employeur;

        this.userable = new Userable();
        this.userable.setId(0L);

        this.userable.setUtilisateur(new Utilisateur());
        this.userable.setUtilisateur(utilisateur);
        this.userable.getUtilisateur().setId(0L);


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




    public List<Object> getContrats() {
        return contrats;
    }

    public void setContrats(List<Object> contrats) {
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
    public List<Object> getChildObjectList() {

        Type listType = new TypeToken<List<Object>>() {}.getType();

        return new Gson().fromJson(new Gson().toJson(contrats), listType);
    }




    @Override
    public void setChildObjectList(List<Object> list) {

    }





}
