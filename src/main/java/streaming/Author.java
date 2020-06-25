package streaming;

import java.io.Serializable;

public class Author implements Serializable {
    private String email;
    private String name;

    public Author(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "streaming.Author{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}