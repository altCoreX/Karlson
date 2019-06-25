package com.s251437.KarlsonAdventures.journey;

import com.s251437.KarlsonAdventures.exceptions.EmptyEventsListException;

public class Main {

    public static void main(String[] args) {

        Journey journey = new Journey(new Kid("Малыш", (byte) 8), new Karlson("Карлсон", (byte) 45));
        journey.addEvent(new UpdatableEvent("Щель"));
        journey.addEvent(new UpdatableEvent("Щель", "%1$s чуть не упал, но %2$s поймал его. %2$s довольно смеется.", "%1s и %2s обошли щель стороной.\n"));
        journey.addVisitor(new Kid("Мартин", (byte) 24));
        journey.addVisitor(new Kid("Гаврилов", (byte) 52));
        journey.addVisitor(new Kid("Балакшин", (byte) 32));

        try {
            journey.start();
        }
        catch (EmptyEventsListException e){
            System.out.println(e.getMessage());
        }
    }
}