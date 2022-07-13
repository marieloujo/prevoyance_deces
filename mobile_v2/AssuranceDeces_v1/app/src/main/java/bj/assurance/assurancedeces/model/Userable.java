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



    @SerializedName("actif")
    private boolean actif = false;





    public Userable() {
    }





    public Userable(String login, String password, String userableType,
                    Object object, Utilisateur utilisateur) {
        this.login = login;
        this.password = password;
        this.userableType = userableType;
        this.object = object;
        this.utilisateur = utilisateur;
    }

    public Userable(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }



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


    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
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
                ", actif=" + actif +
                '}';
    }


}
