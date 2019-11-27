package bj.assurance.prevoyancedeces.model;

import com.google.gson.annotations.SerializedName;

public class Assurer {

    @SerializedName("id")
    private Long id;

    @SerializedName("profession")
    private String profession;

    @SerializedName("pivot")
    private Assurance assurance;

    public Long getId() {
        return id;
    }

    public Assurance getAssurance() {
        return assurance;
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
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
}
