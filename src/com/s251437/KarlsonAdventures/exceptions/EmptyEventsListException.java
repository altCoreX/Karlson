package com.s251437.KarlsonAdventures.exceptions;

public class EmptyEventsListException extends Exception {
    public EmptyEventsListException(){
        super("Список событий пуст!");
    }

    public EmptyEventsListException(String message){
            super(message);
    }
}
