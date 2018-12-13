package karlson;

public enum Weather {RAINING(0.25f, 1.2f, "дождливая"), CLEAR(0.45f, 1f, "спокойная"), WINDY(0.35f, 1.3f, "ветренная");
    final double Chance;
    final double Multiplier;
    final String Name;
    Weather(double Chance, double Multiplier, String Name){
        this.Chance = Chance;
        this.Multiplier = Multiplier;
        this.Name = Name;
    }
    protected double getChance(){
        return Chance;
    }
    protected double getMultiplier(){
        return Multiplier;
    }
    protected String getName(){
        return Name;
    }
}
