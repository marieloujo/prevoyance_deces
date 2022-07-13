package bj.assurance.prevoyancedeces.utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValidationEror {


    @SerializedName("nom")
    private List<String> errorNom;


    @SerializedName("prenom")
    private List<String> errorPrenom;


    @SerializedName("telephone")
    private List<String> errorTelephone;


    @SerializedName("commune")
    private List<String> errorcommune;


    @SerializedName("sexe")
    private List<String> errorsexe;


    @SerializedName("date_naissance")
    private List<String> errorDateNaissance;


    @SerializedName("email")
    private List<String>  errorEmail;


    @Override
    public String toString() {
        return "ValidationEror{" +
                "errorNom=" + errorNom +
                ", errorPrenom=" + errorPrenom +
                ", errorTelephone=" + errorTelephone +
                ", errorcommune=" + errorcommune +
                ", errorsexe=" + errorsexe +
                ", errorDateNaissance=" + errorDateNaissance +
                ", errorEmail=" + errorEmail +
                '}';
    }

    public List<String> getErrorEmail() {
        return errorEmail;
    }

    public void setErrorEmail(List<String> errorEmail) {
        this.errorEmail = errorEmail;
    }

    public List<String> getErrorNom() {
        return errorNom;
    }

    public void setErrorNom(List<String> errorNom) {
        this.errorNom = errorNom;
    }

    public List<String> getErrorPrenom() {
        return errorPrenom;
    }

    public void setErrorPrenom(List<String> errorPrenom) {
        this.errorPrenom = errorPrenom;
    }

    public List<String> getErrorTelephone() {
        return errorTelephone;
    }

    public void setErrorTelephone(List<String> errorTelephone) {
        this.errorTelephone = errorTelephone;
    }

    public List<String> getErrorcommune() {
        return errorcommune;
    }

    public void setErrorcommune(List<String> errorcommune) {
        this.errorcommune = errorcommune;
    }

    public List<String> getErrorsexe() {
        return errorsexe;
    }

    public void setErrorsexe(List<String> errorsexe) {
        this.errorsexe = errorsexe;
    }

    public List<String> getErrorDateNaissance() {
        return errorDateNaissance;
    }

    public void setErrorDateNaissance(List<String> errorDateNaissance) {
        this.errorDateNaissance = errorDateNaissance;
    }
}
