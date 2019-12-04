package bj.assurance.prevoyancedeces.model.pagination;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class OutputPaginate {

    @SerializedName("data")
    private List<Object> objects;

    @SerializedName("links")
    private LinksPaginate linksPaginate;

    @SerializedName("meta")
    private MetaPaginate metaPaginate;

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> clients) {
        this.objects = clients;
    }

    public LinksPaginate getLinksPaginate() {
        return linksPaginate;
    }

    public void setLinksPaginate(LinksPaginate linksPaginate) {
        this.linksPaginate = linksPaginate;
    }

    public MetaPaginate getMetaPaginate() {
        return metaPaginate;
    }

    public void setMetaPaginate(MetaPaginate metaPaginate) {
        this.metaPaginate = metaPaginate;
    }

    @Override
    public String toString() {
        return "OutputPaginate{" +
                "objects=" + objects +
                ", linksPaginate=" + linksPaginate +
                ", metaPaginate=" + metaPaginate +
                '}';
    }
}
