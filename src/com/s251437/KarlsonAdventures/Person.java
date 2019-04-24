package com.s251437.KarlsonAdventures;

abstract public class Person implements Observable {

    private String name;
    private byte age;

    private double pulse = 60.0f + Math.random()*2;

    Person (String name, byte age) {
        this.name = name;
        this.age = age;
    }


    public String getName(){
        return this.name;
    }

    protected void go() {
        if (pulse > 63.0f) {
            pulse -= 3.0f;
        } else if (pulse > 60.0f && pulse < 63.0f) {
            pulse = 60.0f + Math.random()*2;
        }
    }

    public double getPulse() {
        return pulse;
    }

    protected void applyBuff(double buff) {
        if (pulse + buff > 60.0f) {
            pulse += buff;
        } else {
            pulse = 60.0f + Math.random()*2;
        }
    }

    public short getAge(){
        return this.age;
    }

    @Override
    public int hashCode() {
        return String.format("%s %d", name, age).hashCode();
    }

    @Override
    public String toString() {
        return String.format("Имя: %s, возраст: %d", name, age);
    }
}
