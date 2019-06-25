package com.s251437.KarlsonAdventures.journey;

class Event implements Observable{

    private double chance;
    private double buff;
    private static double multiplier;
    private String description;
    Event(String description, double chance, double buff) throws IllegalArgumentException {
        if (chance <= 1.0f && chance >= 0.0f) {
            this.chance = chance * multiplier;
            this.buff = buff * multiplier;
            this.description = description;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    protected String returnDescription(boolean positive, Kid kid, Karlson karlson){
        return String.format(description, kid.getName(), karlson.getName());
    }

    protected String getDescription(){
        return description;
    }

    protected double getChance(){
        return chance;
    }

    protected void setChance(double chance){
        this.chance = chance;
    }

    protected double getBuff(){
        return buff;
    }

    protected void setBuff(double buff){
        this.buff = buff;
    }

    protected static void setMultiplier(double multiplier){
         Event.multiplier = multiplier;
    }

    @Override
    public String returnState(){
        return "На горизонте чисто, ничего не предвещает беды.";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Событие с шансом %1$d, эффективностью %2$d.", chance, buff);
    }

}
