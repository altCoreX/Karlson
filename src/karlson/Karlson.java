package karlson;

public class Karlson extends Person{

    private double mood = 60.0f;

    public Karlson(String name){
        super(name);
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
