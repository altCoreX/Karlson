package com.s251437.KarlsonAdventures;

import java.io.Serializable;
import java.util.HashSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Message implements Serializable {
    private String message;
    private ConcurrentSkipListSet<Kid> kids;
    private boolean hasKids;

    public Message(String command){
        this.message = command;
        this.kids = new ConcurrentSkipListSet<Kid>();
        this.hasKids = false;
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
}
