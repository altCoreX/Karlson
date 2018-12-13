package karlson;

public class Main {

    public static void main(String[] args) {

        Journey journey = new Journey(new Kid("Малыш"), new Karlson("Карлсон"));
        journey.addEvent(new UpdatableEvent("Щель"));
        journey.addEvent(new UpdatableEvent("Щель", "%1$s чуть не упал, но %2$s поймал его. %2$s довольно смеется.", "%1s и %2s обошли щель стороной.\n"));
        journey.start();

    }
}
