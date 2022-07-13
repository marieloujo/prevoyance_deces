package bj.assurance.assurancedeces.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Beneficiaire {

    @SerializedName("id")
    private Long id;



    @SerializedName("userable")
    private Userable userable;



    @SerializedName("benefices")
    private List<Benefice> benefices;












    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }





    public Userable getUserable() {
        return userable;
    }

    public void setUserable(Userable userable) {
        this.userable = userable;
    }




    public List<Benefice> getBenefices() {
        return benefices;
    }

    public void setBenefices(List<Benefice> benefices) {
        this.benefices = benefices;
    }



    @Override
    public String toString() {
        return "Beneficiaire{" +
                "id=" + id +
                ", userable=" + userable +
                ", benefices=" + benefices +
                '}';
    }



    public Beneficiaire(Long id,Userable userable) {
        this.id = id;
        this.userable = userable;
    }



    public Beneficiaire(Userable userable, List<Benefice> benefices) {
        this.userable = userable;
        this.benefices = benefices;
    }



}
