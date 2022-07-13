package bj.assurance.prevoyancedeces.utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse  {

    @SerializedName("errors")
    private List<ErrorMessage> errorMessages;


    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
