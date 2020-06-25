import java.io.Serializable;
import java.util.List;

public class Payload implements Serializable {
    private String ref;
    private String head;
    private String before;
    private List<Commit> commits;

    public Payload(String ref, String head, String before, List<Commit> commits) {
        this.ref = ref;
        this.head = head;
        this.before = before;
        this.commits = commits;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "ref='" + ref + '\'' +
                ", head='" + head + '\'' +
                ", before='" + before + '\'' +
                ", commits=" + commits +
                '}';
    }
}
