package bj.assurance.assurancedeces.model.pagination;

import com.google.gson.annotations.SerializedName;

public class MetaPaginate {

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("form")
    private int form;

    @SerializedName("last_page")
    private int lastPage;

    @SerializedName("path")
    private String path;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("to")
    private int to;

    @SerializedName("total")
    private int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MetaPaginate{" +
                "currentPage=" + currentPage +
                ", form=" + form +
                ", lastPage=" + lastPage +
                ", path='" + path + '\'' +
                ", perPage=" + perPage +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
