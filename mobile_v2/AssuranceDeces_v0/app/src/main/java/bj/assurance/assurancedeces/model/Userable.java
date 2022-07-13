package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;



public class Userable {



    @SerializedName("id")
    private Long id;



    @SerializedName("login")
    private String login;



    @SerializedName("password")
    private String password;



    @SerializedName("userable_type")
    private String userableType;



    @SerializedName("userable_id")
    private Long userableId;



    @SerializedName("userable")
    private Object object;



    @SerializedName("user")
    private Utilisateur utilisateur;












    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public String getUserableType() {
        return userableType;
    }

    public void setUserableType(String userableType) {
        this.userableType = userableType;
    }




    public Long getUserableId() {
        return userableId;
    }

    public void setUserableId(Long userableId) {
        this.userableId = userableId;
    }




    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }





    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }




    @Override
    public String toString() {
        return "Userable{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userableType='" + userableType + '\'' +
                ", userableId=" + userableId +
                ", object=" + object +
                ", utilisateur=" + utilisateur +
                '}';
    }



}
