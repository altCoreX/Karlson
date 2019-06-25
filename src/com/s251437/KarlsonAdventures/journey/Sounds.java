package com.s251437.KarlsonAdventures.journey;

abstract class Sounds{

    abstract private static class Sound extends Event{
        private String state;

        private Sound(String description, String state, double buff){
            super(description, 1.0f, buff);
            this.state = state;
        }

        @Override
        public String returnState(){
            return state;
        }
    }

    private static class AmbientSound extends Sound{

        private AmbientSound(){
            super("%1$s радуется.", "Из окна доносится симфония Чайковского.", -5*Math.random());
        }
    }

    private static class CreepySound extends Sound{

        private CreepySound(){
            super("%1$s напуган.","Из окна доносится истошный плач ребенка.", 5.0f+5*Math.random());
        }
    }

    static Event getRandomSound(){
        double selector = Math.random();
        if(selector > 0.5f){
            return new CreepySound();
        }
        else{
            return new AmbientSound();
        }
    }
}
