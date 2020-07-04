package streaming;

import java.io.Serializable;

public class Payload implements Serializable {
    private String ref;
    private String head;
    private String before;


    public Payload(String ref, String head, String before) {
        this.ref = ref;
        this.head = head;
        this.before = before;
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


   /*   @Override
  public String toString() {
        return "streaming.Payload{" +
                "ref='" + ref + '\'' +
                ", head='" + head + '\'' +
                ", before='" + before + '\'' +
                ", commits=" + commits +
                '}';
    }*/
}
