package karlson;

class Event implements Observable{

    private double chance;
    private double efficiency;
    private static double multiplier;
    private String description;
    Event(String description, double chance, double efficiency) {
        this.chance = chance * multiplier;
        this.efficiency = efficiency * multiplier;
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

    protected static void setMultiplier(double multiplier){
         Event.multiplier = multiplier;
    }

    @Override
    public String returnState(){
        return "На горизонте чисто, ничего не предвещает беды.";
    }

}
