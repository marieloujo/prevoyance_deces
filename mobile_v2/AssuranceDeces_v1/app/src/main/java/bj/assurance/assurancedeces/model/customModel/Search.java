package bj.assurance.assurancedeces.model.customModel;

public class Search {




    private Long id;



    private String contenue;


    public Search(Long id, String contenue) {
        this.id = id;
        this.contenue = contenue;
    }


    public Long getId() {
        return id;
    }

    public String getContenue() {
        return contenue;
    }
}
