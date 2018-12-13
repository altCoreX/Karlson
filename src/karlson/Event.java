package karlson;

class Event implements Observable{
    private static Weather currentWeather;
    private double chance;
    private double efficiency;
    private String description;
    Event(String description, double chance, double efficiency) {
        currentWeather = Weather.CLEAR;
        this.chance = chance  * currentWeather.getMultiplier();
        this.efficiency = efficiency  * currentWeather.getMultiplier();
        this.description = description;
    }

    protected String returnDescription(boolean positive, Kid kid, Karlson karlson){
        return String.format(description, kid.getName(), karlson.getName());
    }

    protected String getDescription(){
        return description;
    }

    protected double getChance(){
        return chance;
    }

    protected void setChance(double chance){
        this.chance = chance;
    }

    protected double getEfficiency(){
        return efficiency;
    }

    protected void setEfficiency(double efficiency){
        this.efficiency = efficiency;
    }

    protected static void setCurrentWeather(Weather weather){
        currentWeather = weather;
    }

    @Override
    public String returnState(){
        return "На горизонте чисто, ничего не предвещает беды.";
    }

}
