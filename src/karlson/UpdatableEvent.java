package karlson;


public class UpdatableEvent extends Event implements Updatable{

    private String name;
    private String negativeDescription;

    public UpdatableEvent(String name) {
        super("%1$s оступился, но %2$s поймал его. %1$s напуган, а %2$s довольно смеется.",Math.random()/10+0.15f, Math.random()*6 + 4);
        this.name = name;
        negativeDescription = "%1$s и %2$s миновали опасность.\n";
    }

    public UpdatableEvent(String name, String description) {
        super(description,Math.random()/10+0.15f, Math.random()*6 + 4);
        this.name = name;
        negativeDescription = "%1$s и %2$s миновали опасность.\n";
    }

    public UpdatableEvent(String name, String description, String negativeDescription) {
        super(description,Math.random()/10+0.15f, Math.random()*6 + 4);
        this.name = name;
        this.negativeDescription = negativeDescription;
    }

    @Override
    public String returnState(){
        return String.format("%1$s подкрадывается, шанс провалиться %2$.2f процентов, длина %3$.2fм.", name, getChance()*100, getEfficiency());
    }

    @Override
    protected String returnDescription(boolean positive, Kid kid, Karlson karlson){
        updateStats();
        if(positive) {
            return String.format(getDescription(), kid.getName(), karlson.getName());
        }
        else {
            return String.format(negativeDescription, kid.getName(), karlson.getName());
        }
    }

    @Override
    public void updateStats() {
        setChance(Math.random()/10+0.15f);
        setEfficiency(Math.random()*6 + 4);
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
