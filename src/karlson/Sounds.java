package karlson;

abstract class Sounds implements Observable{
    private String state;

    protected Sounds(String state){
        this.state = state;
    }

    static class AmbientSound extends Sounds{

        protected AmbientSound(){
            super("Из окна доносится симфония Чайковского.");
        }
    }

    static class CreepySound extends Sounds{
        double buff = Math.random()*5+5;
        protected CreepySound(){
            super("Из окна доносится истошный плач ребенка.");
        }

    }

    static Sounds getRandomSound(){
        double selector = Math.random();
        if(selector > 0.5f){
            return new CreepySound();
        }
        else{
            return new AmbientSound();
        }
    }

    @Override
    public String returnState(){
        return state;
    }
}
