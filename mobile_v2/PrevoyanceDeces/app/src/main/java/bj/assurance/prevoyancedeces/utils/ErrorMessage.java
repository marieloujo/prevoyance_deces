package bj.assurance.prevoyancedeces.utils;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {


    @SerializedName("message")
    private ValidationEror validationEror;






    public ValidationEror getValidationEror() {
        return validationEror;
    }

    public void setValidationEror(ValidationEror validationEror) {
        this.validationEror = validationEror;
    }


}
