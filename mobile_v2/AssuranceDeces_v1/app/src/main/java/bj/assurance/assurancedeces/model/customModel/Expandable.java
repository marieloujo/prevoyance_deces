package bj.assurance.assurancedeces.model.customModel;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;



public class Expandable  implements ParentObject {



    private String title;


    private List<Object> objects;




    public Expandable(String title, List<Object> objects) {
        this.title = title;
        this.objects = objects;
    }







    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }






    public List<Object> getObjects() {
        return objects;
    }


    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }


    @Override
    public List<Object> getChildObjectList() {
        return objects;
    }

    @Override
    public void setChildObjectList(List<Object> list) {

    }
}
