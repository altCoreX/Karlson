package com.s251437.KarlsonAdventures.journey;

import java.io.Serializable;
import java.security.acl.Owner;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.time.OffsetDateTime;

public class Kid extends Person implements Updatable, Serializable, Comparable<Kid> {

    private  OffsetDateTime init;
    private String owner;
    public Kid(String name, byte age) {
        super(name, age);
        init = OffsetDateTime.now();
        owner = "system";
    }

    public String toJson(){
        return String.format("{\"name\":\"%1$s\";\"age\":\"%2$d\"}", getName(), getAge());
    }

    @Override
    public String returnState() {
        return String.format("%s делает шаг. Частота сердцебиения равна: %.2f", getName(), getPulse());
    }

    public int compareTo(Kid p){
        return Comparator.comparing(Kid::getName).thenComparing(Kid::getAge).thenComparing(Kid::getOwner).compare(this, p);
    }

    public String getOwner(){
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    @Override
    public void updateStats() {
        super.applyBuff(-100.0f);
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$d (Владелец: %3$s)", getName(), getAge(), getOwner());
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Kid){
            if (((Kid) obj).getAge() == this.getAge() && ((Kid) obj).getName().equals(this.getName()) && ((Kid) obj).getOwner().equals(this.getOwner())){
                return true;
            }
        }
        return false;
    }

}
