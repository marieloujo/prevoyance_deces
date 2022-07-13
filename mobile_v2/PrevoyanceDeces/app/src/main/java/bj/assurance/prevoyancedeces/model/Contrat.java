package bj.assurance.prevoyancedeces.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Contrat {

    @SerializedName("id")
    private Long id;

    @SerializedName("numero_contrat")
    private String numero;

    @SerializedName("garantie")
    private String garantie;

    @SerializedName("prime")
    private String prime;

    @SerializedName("duree")
    private String duree;

    @SerializedName("date_debut")
    private String dateDebut;

    @SerializedName("date_fin")
    private String dateFin;

    @SerializedName("date_echeance")
    private String dateEcheance;

    @SerializedName("date_effet")
    private String dateEffet;

    @SerializedName("fin")
    private boolean fin;

    @SerializedName("valider")
    private boolean valide;

    @SerializedName("numero_police_assurance")
    private String numeroPolice;

    @SerializedName("portefeuilles")
    private List<Portefeuille> transactions;

    @SerializedName("client")
    private Client client;

    @SerializedName("marchand")
    private Marchand marchand;

    @SerializedName("beneficiaire")
    private List<Benefice> benefices;

    @SerializedName("assure")
    private Assurer assurer;

    @SerializedName("documents")
    private List<Document> documents;

    public Contrat() {
    }

    public Contrat(Long id) {
        this.id = id;
    }

    public Contrat(String garantie, String prime, String duree, String dateDebut, String dateFin, String dateEcheance, String dateEffet, Client client, Marchand marchand, List<Benefice> benefices, Assurer assurer) {
        this.garantie = garantie;
        this.prime = prime;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateEcheance = dateEcheance;
        this.dateEffet = dateEffet;
        this.client = client;
        this.marchand = marchand;
        this.benefices = benefices;
        this.assurer = assurer;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }

    public String getPrime() {
        return prime;
    }

    public void setPrime(String prime) {
        this.prime = prime;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(String dateEffet) {
        this.dateEffet = dateEffet;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public String getNumeroPolice() {
        return numeroPolice;
    }

    public void setNumeroPolice(String numeroPolice) {
        this.numeroPolice = numeroPolice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Marchand getMarchand() {
        return marchand;
    }

    public void setMarchand(Marchand marchand) {
        this.marchand = marchand;
    }

    public List<Benefice> getBenefices() {
        return benefices;
    }

    public void setBenefices(List<Benefice> benefices) {
        this.benefices = benefices;
    }

    public Assurer getAssurer() {
        return assurer;
    }

    public void setAssurer(Assurer assurer) {
        this.assurer = assurer;
    }

    public List<Portefeuille> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Portefeuille> transactions) {
        this.transactions = transactions;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", garantie='" + garantie + '\'' +
                ", prime='" + prime + '\'' +
                ", duree='" + duree + '\'' +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", dateEcheance='" + dateEcheance + '\'' +
                ", dateEffet='" + dateEffet + '\'' +
                ", fin=" + fin +
                ", valide=" + valide +
                ", numeroPolice='" + numeroPolice + '\'' +
                ", transactions=" + transactions +
                ", client=" + client +
                ", marchand=" + marchand +
                ", benefices=" + benefices +
                ", assurer=" + assurer +
                ", documents=" + documents +
                '}';
    }
}
