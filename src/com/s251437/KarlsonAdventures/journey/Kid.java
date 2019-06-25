package com.s251437.KarlsonAdventures.journey;

import java.io.Serializable;
import java.util.Comparator;

public class Kid extends Person implements Updatable, Serializable, Comparable<Kid> {

    public Kid(String name, byte age) {
        super(name, age);
    }

    public String toJson(){
        return String.format("{\"name\":\"%1$s\";\"age\":\"%2$d\"}", getName(), getAge());
    }

    @Override
    public String returnState() {
        return String.format("%s делает шаг. Частота сердцебиения равна: %.2f", getName(), getPulse());
    }

    public int compareTo(Kid p){
        return Comparator.comparing(Kid::getName).thenComparing(Kid::getAge).compare(this, p);
    }

    @Override
    public void updateStats() {
        super.applyBuff(-100.0f);
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$d", getName(), getAge());
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Kid){
            if (((Kid) obj).getAge() == this.getAge() && ((Kid) obj).getName().equals(this.getName())){
                return true;
            }
        }
        return false;
    }

}
