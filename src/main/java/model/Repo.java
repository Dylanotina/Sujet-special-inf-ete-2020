package model;

import java.io.Serializable;

public class Repo implements Serializable {
    private String name;
    private String url;
    private int id;

    public Repo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 /*     @Override
    public String toString() {
        return "model.Repo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }*/
}
