package com.s251437.KarlsonAdventures.journey;

import java.util.Arrays;

public class OldJourney {
    private int stepCounter = 0;
    private Weather currentWeather;
    private Kid kid;
    private Karlson karlson;
    private Event[] events = {};
    private Event event;
    private Observable[] objects = new Observable[3];

    public OldJourney(Kid kid, Karlson karlson) {
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

    public void addEvent(Event d) {
        events = Arrays.copyOf(events, events.length + 1);
        events[events.length - 1] = d;
    }

    public void start() {
        addEvent(new Event("Ничего не произошло, %2$s и %2s спокойно идут дальше.", 1.0f, -5.0f));
        objects[0] = kid;
        objects[1] = karlson;
        System.out.printf("Погода %1$s\n", currentWeather.getName());
        System.out.println("Приключение начинается!\n");

        while (karlson.getMood() > 50.0f && kid.getPulse() <= 110.0f) {
            stepCounter += 1;

            for (Event d : events) { //Определяется угроза
                if (d.getChance() > Math.random()) {
                    event = d;
                    objects[2] = d;
                    break;
                }
            }

            System.out.printf("### Шаг %d ###\n", stepCounter);

            karlson.go();
            kid.go();

            for (Observable item : this.objects) {
                System.out.println(item.returnState());
            }

            if (event.getChance() > Math.random() / 3) {
                kid.applyBuff(event.getBuff());
                karlson.applyBuff(event.getBuff());
                System.out.printf("%1$s Пульс %2$.2f, настроение %3$.2f.\n", event.returnDescription(true, kid, karlson), kid.getPulse(), karlson.getMood());
            } else {
                System.out.println(event.returnDescription(false, kid, karlson));
            }

            System.out.println();
        }


        if (karlson.getMood() <= 50.0f) {
            System.out.printf("%s вдоволь позабавился, теперь он с %sом идет домой есть варенье.", karlson.getName(), kid.getName());
        } else {
            System.out.printf("%s cлишком напуган, поэтому %s отвел его домой.", kid.getName(), karlson.getName());
        }
    }


    protected Kid getKid(){
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
