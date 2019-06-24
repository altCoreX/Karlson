package com.s251437.KarlsonAdventures;

public class Karlson extends Person {

    private double mood = 100.0f;
    private Propeller propeller = new Propeller("красный", 17.3f);

    public Karlson(String name, byte age) {
        super(name, age);
    }

    private class Propeller {
        private String colour;
        private double diameter;

        private Propeller(String colour, double diameter) {
            this.colour = colour;
            this.diameter = diameter;
        }

        private String getColour() {
            return colour;
        }

        private void setColour(String colour) {
            this.colour = colour;
        }

        private double getDiameter() {
            return diameter;
        }
    }

    public String getPropellerColour() {
        return propeller.getColour();
    }

    public void setPropellerColour(String colour) {
        propeller.setColour(colour);
    }

    public double getPropellerDiameter() {
        return propeller.getDiameter();
    }

    public void setPropeller(String colour, double diameter) {
        propeller = new Propeller(colour, diameter);
    }

    public double getMood() {
        return mood;
    }

    @Override
    public void go() {
        if(getPulse() > 100){
            mood+= getPulse()-100;
        }
        super.go();
        mood -= 5.0f;
    }

    @Override
    public void applyBuff(double buff) {
        super.applyBuff(buff);
        mood += buff * 0.4f;
    }

    @Override
    public String returnState() {
        return String.format("%s делает шаг. Уровень настроения равен: %.2f", getName(), this.mood);
    }

    @Override
    public int hashCode() {
        return String.format("Карлсон %s %d", this.getName(), this.getAge()).hashCode();
    }

    @Override
    public String toString() {
        return String.format("%1$s: пульс: %2$d", getName(), getMood());
    }
}
