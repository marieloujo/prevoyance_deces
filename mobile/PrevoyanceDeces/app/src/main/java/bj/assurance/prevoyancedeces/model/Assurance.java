package bj.assurance.prevoyancedeces.model;

public class Assurance {

    private Long id;

    private Integer garantie;

    private Integer prime;

    private String duree;

    private String dateDebut;

    private String dateFin;

    private String dateEcheance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGarantie() {
        return garantie;
    }

    public void setGarantie(Integer garantie) {
        this.garantie = garantie;
    }

    public Integer getPrime() {
        return prime;
    }

    public void setPrime(Integer prime) {
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
}
