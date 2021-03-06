package model;

import java.io.Serializable;

public class Actor implements Serializable {
    private String login;
    private String url;

    public Actor(String login, String url) {
        this.login = login;
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   /* @Override
    public String toString() {
        return "model.Actor{" +
                "login='" + login + '\'' +
                ", url='" + url + '\'' +
                '}';
    }*/
}
