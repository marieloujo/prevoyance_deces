package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Utilisateur {



    @SerializedName("id")
    private Long id;


    @SerializedName("nom")
    private String nom;


    @SerializedName("prenom")
    private String prenom;


    @SerializedName("telephone")
    private String telephone;


    @SerializedName("email")
    private String email;



    @SerializedName("sexe")
    private String sexe;



    @SerializedName("date_naissance")
    private String dateNaissance;



    @SerializedName("situation_matrimoniale")
    private String situationMatrimoniale;



    @SerializedName("adresse")
    private String adresse;



    @SerializedName("prospect")
    private boolean prospect = false;



    @SerializedName("actif")
    private boolean actif = false;




    @SerializedName("commune")
    private Commune commune;



    @SerializedName("remember_token")
    private String rememberToken;



    @SerializedName("created_at")
    private String dateCreation;



    @SerializedName("updated_at")
    private String dateModification;




    @SerializedName("marchand")
    private Marchand marchand;



    @SerializedName("conversations")
    private List<Conversation> conversations;



    @SerializedName("conversations_user")
    private List<ConversationUser> conversationsUser;



    @SerializedName("messages")
    private Message message;




    @SerializedName("userables")
    private List<Userable> userables;






    public Utilisateur(Long id) {
        this.id = id;
    }

    public Utilisateur() {
    }


    public Utilisateur(String nom, String prenom, String telephone, String email, String sexe,
                       String dateNaissance, String situationMatrimoniale, String adresse,
                       boolean prospect, boolean actif, Commune commune) {

        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.situationMatrimoniale = situationMatrimoniale;
        this.adresse = adresse;
        this.prospect = prospect;
        this.actif = actif;
        this.commune = commune;

    }






    public List<Userable> getUserables() {
        return userables;
    }

    public void setUserables(List<Userable> userables) {
        this.userables = userables;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public void setSituationMatrimoniale(String situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public boolean isProspect() {
        return prospect;
    }

    public void setProspect(boolean prospect) {
        this.prospect = prospect;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
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

    public Marchand getMarchand() {
        return marchand;
    }

    public void setMarchand(Marchand marchand) {
        this.marchand = marchand;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<ConversationUser> getConversationsUser() {
        return conversationsUser;
    }

    public void setConversationsUser(List<ConversationUser> conversationsUser) {
        this.conversationsUser = conversationsUser;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", situationMatrimoniale='" + situationMatrimoniale + '\'' +
                ", adresse='" + adresse + '\'' +
                ", prospect=" + prospect +
                ", actif=" + actif +
                ", commune=" + commune +
                ", rememberToken='" + rememberToken + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                ", marchand=" + marchand +
                ", conversations=" + conversations +
                ", conversationsUser=" + conversationsUser +
                ", message=" + message +
                '}';
    }
}
