package com.s251437.KarlsonAdventures.net;

import com.s251437.KarlsonAdventures.journey.Kid;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

public class Message implements Serializable {
    private String message;
    private String login;
    private String password;
    private String SID;
    private ConcurrentSkipListSet<Kid> kids;
    private boolean hasKids;

    public Message(String command){
        this.message = command;
        this.kids = new ConcurrentSkipListSet<Kid>();
        this.hasKids = false;
        SID = null;
        password = null;
        login = null;
    }

    public String getCommand(){
        return message;
    }

    public ConcurrentSkipListSet<Kid> getKids(){
        return kids;
    }

    public boolean isKids(){
        return hasKids;
    }

    public void setKids(ConcurrentSkipListSet<Kid> kids){
        this.kids = kids;
        hasKids = true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }
}
