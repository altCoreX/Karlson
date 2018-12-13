package karlson;

public class Kid extends Person implements Updatable{

    private double pulse = 60.0f;

    public Kid (String name){
        super(name);
    }

    public double getPulse(){
        return pulse;
    }

    @Override
    protected void go(){
        if(pulse > 63.0f){
            pulse -= 3.0f;
        }
        else if(pulse > 60.0f && pulse < 63.0f){
            pulse = 60.0f;
        }
    }

    @Override
    protected void beInDanger(Event event){
        pulse+= event.getEfficiency()*3.5f;
    }

    @Override
    public String returnState(){
        return String.format("%s делает шаг. Частота сердцебиения равна: %.2f", getName(), this.pulse);
    }

    @Override
    public void updateStats() {
        pulse = 60.0f;
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
