package bj.assurance.assurancedeces.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import bj.assurance.assurancedeces.model.Client;
import bj.assurance.assurancedeces.model.Marchand;
import bj.assurance.assurancedeces.model.SuperMarchand;
import bj.assurance.assurancedeces.model.Userable;
import bj.assurance.assurancedeces.model.Utilisateur;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class GetResponseObject {


    private JsonObject jsonObject;


    public GetResponseObject() {
    }




    public CustomJsonObject getResponse() {

        CustomJsonObject customJsonObject = new CustomJsonObject();

        JsonObject errorMessage = getErrorMessage();
        JsonObject sucessMessage = getSuccesMessage();
        JsonObject successData  = getSucessData();


        if (!(errorMessage == null)) {

            customJsonObject.setJsonObject(errorMessage);
            customJsonObject.setType("error_message");

        } else if (!(sucessMessage == null)) {

            customJsonObject.setJsonObject(sucessMessage);
            customJsonObject.setType("success_message");

        } else if (!(successData == null)) {

            customJsonObject.setJsonObject(successData);
            customJsonObject.setType("success_data");

        } else {

            try {
                customJsonObject.setJsonObject(jsonObject);
                customJsonObject.setType("success_data");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return  customJsonObject;


    }



    private JsonObject getErrorMessage() {

        JsonObject errors, errorMessage = null;

        try {


            errors = jsonObject.get("errors").getAsJsonObject();
            Log.w(TAG, "onError: " + errors);
            errorMessage = (JsonObject) errors.get("message");

        } catch (Exception e) {

            e.printStackTrace();

        }


        return errorMessage;

    }



    private JsonObject getSuccesMessage() {

        JsonObject sucess, message = null;


        try {
            sucess = jsonObject.getAsJsonObject("success");

            message =  (JsonObject) sucess.get("message");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return message;

    }



    private JsonObject getSucessData() {

        JsonObject sucess = null;


        try {
            sucess = jsonObject.getAsJsonObject("success");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sucess;

    }



    public Object checkAuthype(Userable userable) {


        Gson gson = new Gson();

        String object = gson.toJson(userable.getObject());


        switch (userable.getUserableType()) {


            case "App\\Models\\Marchand":

                Marchand marchand = gson.fromJson(object, Marchand.class);
                marchand.setUtilisateur(userable.getUtilisateur());

                return marchand;


            case "App\\Models\\Client":

                Client client = gson.fromJson(object, Client.class);
                client.setUtilisateur(userable.getUtilisateur());

                return client;


            case "App\\Models\\SuperMarchand":

                SuperMarchand superMarchand = gson.fromJson(object, SuperMarchand.class);
                superMarchand.setUtilisateur(userable.getUtilisateur());

                return superMarchand;



            default:

                return  userable.getObject();

        }

    }




    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
