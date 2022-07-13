package bj.assurance.assurancedeces.utils;

import com.google.gson.JsonObject;

public class CustomJsonObject {

    private JsonObject jsonObject;

    private String type;


    public CustomJsonObject() {
    }



    public CustomJsonObject(JsonObject jsonObject, String type) {
        this.jsonObject = jsonObject;
        this.type = type;
    }



    public JsonObject getJsonObject() {
        return jsonObject;
    }



    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    @Override
    public String toString() {
        return "CustomJsonObject{" +
                "jsonObject=" + jsonObject +
                ", type='" + type + '\'' +
                '}';
    }

}
