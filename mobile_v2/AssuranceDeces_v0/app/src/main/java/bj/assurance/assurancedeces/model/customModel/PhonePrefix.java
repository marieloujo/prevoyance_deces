package bj.assurance.assurancedeces.model.customModel;

import com.google.gson.annotations.SerializedName;

public class PhonePrefix {


    @SerializedName("num")
    private String num;




    public String getNum() {
        return num;
    }



    public void setNum(String num) {
        this.num = num;
    }


    @Override
    public String toString() {
        return "PhonePrefix{" +
                "num='" + num + '\'' +
                '}';
    }


}
