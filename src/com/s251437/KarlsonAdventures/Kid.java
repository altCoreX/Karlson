package com.s251437.KarlsonAdventures;

public class Kid extends Person implements Updatable, Comparable<Kid> {

    public Kid(String name, byte age) {
        super(name, age);
    }

    @Override
    public String returnState() {
        return String.format("%s делает шаг. Частота сердцебиения равна: %.2f", getName(), getPulse());
    }

    public int compareTo(Kid p){
        return this.getAge() - p.getAge();
    }

    @Override
    public void updateStats() {
        super.applyBuff(-100.0f);
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$d", getName(), getAge());
    }

}
