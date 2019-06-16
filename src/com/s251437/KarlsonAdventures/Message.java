package com.s251437.KarlsonAdventures;

import java.io.Serializable;
import java.util.HashSet;

public class Message implements Serializable {
    private String message;

    public Message(String command){
        this.message = command;
    }

    public String getCommand(){
        return message;
    }
}
