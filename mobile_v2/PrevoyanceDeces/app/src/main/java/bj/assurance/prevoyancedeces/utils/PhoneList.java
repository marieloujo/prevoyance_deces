package bj.assurance.prevoyancedeces.utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneList {


    @SerializedName("code")
    private String code;



    @SerializedName("size")
    private int size;


    @SerializedName("phonePrefix")
    private List<PhonePrefix> phonePrefix;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<PhonePrefix> getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(List<PhonePrefix> phonePrefix) {
        this.phonePrefix = phonePrefix;
    }


    @Override
    public String toString() {
        return "PhoneList{" +
                "code='" + code + '\'' +
                ", size=" + size +
                ", phonePrefix=" + phonePrefix +
                '}';
    }
}
