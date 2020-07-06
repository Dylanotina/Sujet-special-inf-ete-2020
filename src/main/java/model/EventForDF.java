package model;

import java.io.Serializable;

public class EventForDF implements Serializable {
    private String id;
    private String type;
    private String titre_repo;
    private int id_repo;
    private String login;

    public EventForDF(String id, String type, String titre_repo, int id_repo, String login) {
        this.id = id;
        this.type = type;
        this.titre_repo = titre_repo;
        this.id_repo = id_repo;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre_repo() {
        return titre_repo;
    }

    public void setTitre_repo(String titre_repo) {
        this.titre_repo = titre_repo;
    }

    public int getId_repo() {
        return id_repo;
    }

    public void setId_repo(int id_repo) {
        this.id_repo = id_repo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "EventForDF{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", titre_repo='" + titre_repo + '\'' +
                ", id_repo=" + id_repo +
                ", login='" + login + '\'' +
                '}';
    }
}
