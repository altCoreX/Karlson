package com.s251437.KarlsonAdventures.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(){
        super("Заданы недопустимые параметры!");
    }

    public IllegalArgumentException(String message){
        super(message);
    }
}
