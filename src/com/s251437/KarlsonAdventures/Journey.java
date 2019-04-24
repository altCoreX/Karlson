package com.s251437.KarlsonAdventures;

import com.s251437.KarlsonAdventures.exceptions.EmptyEventsListException;

import java.util.Arrays;
import java.util.HashSet;

public class Journey {
    private Kid kid;
    private Karlson karlson;
    private Weather currentWeather;
    private int stepCounter = 0;
    private Observable[] objects = new Observable[2];
    private Person[] visitors = {};
    private Person[] characters;
    private Event[] events = {};
    private Event sound;
    private Event event;

    private Person Satan = new Person("Люцифер", (byte) 255) {
        @Override
        protected void go() {
            class Armagedon extends Event {
                private Armagedon() {
                    super("%1$s и %2$s напуганы, адское пламя обожгло их.", 1.0f, 666f);
                }

                @Override
                public String returnState() {
                    return "ВРАТА В АД ОТКРЫЛИСЬ, АПОКАЛИПСИС НАСТУПИЛ.";
                }
            }
            currentWeather = Weather.HELL;
            setCurrentEvent(new Armagedon());
        }

        @Override
        protected void applyBuff(double buff) {
            kid.applyBuff(666.0f);
            karlson.applyBuff(-666.0f);
        }

        @Override
        public String returnState() {
            return "САТАНА ПРОБУДИЛСЯ И ЖАЖДЕТ КРОВИ!";
        }
    };

    public Journey(Kid kid, Karlson karlson) {
        this.kid = kid;
        this.karlson = karlson;
    }

    {
        currentWeather = selectWeather();
        Event.setMultiplier(currentWeather.getMultiplier());
    }

    private Weather selectWeather() {
        Weather[] weathers = {Weather.RAINING, Weather.CLEAR, Weather.WINDY};
        double selector = 0f;
        double token = Math.random();
        Weather selectedWeather = Weather.CLEAR;
        for (Weather w : weathers) {
            if (token > selector) {
                selector += w.getChance();
            } else {
                selectedWeather = w;
            }
        }
        return selectedWeather;
    }


    private void setCharacters(){
        characters = Arrays.copyOf(visitors, visitors.length + 2);
        characters[characters.length - 2] = kid;
        characters[characters.length - 1] = karlson;
    }

    private void startPass(){
        for(Person visitor: visitors){
            if(visitor.getPulse() > 100.0f){
                setCurrentEvent(new Event("Посетители отдохнули.", 1.0f, 0.0f));
                visitor.applyBuff(-30);
            }

        }
    }

    private void buffCharacters(double buff){
        for(Person character: characters){
            character.applyBuff(buff);
        }
    }

    private void setCurrentEvent(Event event){
        this.event = event;
        objects[1] = event;
    }

    public void addEvent(Event event) {
        events = Arrays.copyOf(events, events.length + 1);
        events[events.length - 1] = event;
    }

    public void addVisitor(Kid visitor) {
        visitors = Arrays.copyOf(visitors, visitors.length + 1);
        visitors[visitors.length - 1] = visitor;
    }

    public void start() throws EmptyEventsListException {
        setCharacters();
        if (events.length != 0) {
            addEvent(new Event("Ничего не произошло, %2$s и %2s спокойно идут дальше.", 1.0f, -5.0f));

            System.out.printf("Погода %1$s\n", currentWeather.getName());
            System.out.println("Приключение начинается!\n");

            while (karlson.getMood() > 50.0f && kid.getPulse() <= 110.0f) {
                stepCounter += 1;
                sound = Sounds.getRandomSound();
                objects[0] = sound;

                for (Event d : events) { //Определяется угроза
                    if (d.getChance() > Math.random()) {
                        setCurrentEvent(d);
                        break;
                    }
                }

                startPass();

                if (stepCounter == 666) {
                    System.out.println(Satan.returnState());
                    Satan.go();
                }

                System.out.printf("### Шаг %d ###\n", stepCounter);

                for(Person character: characters){
                    character.go();
                    System.out.println(character.returnState());
                }

                for (Observable item : this.objects) {
                    System.out.println(item.returnState());
                }

                //Применяются бафы
                buffCharacters(sound.getBuff());

                if (event.getChance() > Math.random() / 3) {
                    buffCharacters(event.getBuff());
                    System.out.printf("%1$s Пульс %2$.2f, настроение %3$.2f.\n", event.returnDescription(true, kid, karlson), kid.getPulse(), karlson.getMood());
                } else {
                    System.out.println(event.returnDescription(false, kid, karlson));
                }

                System.out.println();


            }

        } else {
            throw new EmptyEventsListException();
        }

        if (karlson.getMood() <= 50.0f) {
            System.out.printf("%s вдоволь позабавился, теперь он с %sом идет домой есть варенье.", karlson.getName(), kid.getName());
        } else {
            System.out.printf("%s cлишком напуган, поэтому %s отвел его домой.", kid.getName(), karlson.getName());
        }

    }

    protected Kid getKid() {
        return kid;
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
