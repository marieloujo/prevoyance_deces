package bj.assurance.prevoyancedeces.customClass;

public class ListObject {


    private String libelle;

    public ListObject(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return  libelle ;
    }
}
