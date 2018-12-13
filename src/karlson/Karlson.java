package karlson;

public class Karlson extends Person{

    private double mood = 60.0f;
    private Propeller propeller = new Propeller("красный", 17.3f);
    public Karlson(String name){
        super(name);
    }

    private class Propeller{
        private String colour;
        private double diameter;
        private Propeller(String colour, double diameter) {
            this.colour = colour;
            this.diameter = diameter;
        }
            private String getColour() {
                return colour;
            }

            private void setColour(String colour){
            this.colour = colour;
            }

            private double getDiameter(){
            return diameter;
            }
    }

    public String getPropellerColour() {
        return propeller.getColour();
    }

    public void setPropellerColour(String colour) {
        propeller.setColour(colour);
    }

    public double getPropellerDiameter(){
        return propeller.getDiameter();
    }

    public void setPropeller(String colour, double diameter){
        propeller = new Propeller(colour, diameter);
    }

    public double getMood(){
        return mood;
    }

    @Override
    public void go(){
        mood -= 5.0f;
    }

    @Override
    public void beInDanger(Event event){
        mood+= event.getEfficiency()*1.5f;
    }

    @Override
    public String returnState(){
        return String.format("%s делает шаг. Уровень настроения равен: %.2f", getName(), this.mood);
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
        return super.toString();
    }

}
