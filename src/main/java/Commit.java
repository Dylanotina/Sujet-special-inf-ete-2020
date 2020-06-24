import java.util.List;

public class Commit {
    private String url;
    private String message;
    private Author author;

    public Commit(String url, String message, Author author) {
        this.url = url;
        this.message = message;
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
