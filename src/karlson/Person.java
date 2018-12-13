package karlson;

abstract class Person implements Observable{

    private String name;

    Person (String name) {
        this.name = name;
    }

    protected String getName(){
        return this.name;
    }

    protected abstract void go();

    protected abstract void beInDanger(Event event);
}
