package com.s251437.KarlsonAdventures.journey;

import java.io.Serializable;
import java.time.ZonedDateTime;

abstract public class Person implements Observable, Serializable {

    private String name;
    private byte age;
    private ZonedDateTime initDateTime;

    private double pulse = 60.0f + Math.random()*2;

    Person (String name, byte age) {
        this.name = name;
        this.age = age;
        initDateTime = java.time.ZonedDateTime.now();
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

    public ZonedDateTime getInitDateTime() {
        return initDateTime;
    }

    public short getAge(){
        return this.age;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + initDateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Имя: %s, возраст: %d", name, age);
    }
}
