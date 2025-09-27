package model;

public class Accessory extends KnightItem{

    private String effect;

    public Accessory(String name, String type, double weight, int price, String effect) {
        super(name, type, weight, price);
        this.effect = effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }
    
    @Override
    public void use(){
        System.out.println("Casting an effect.");
    }

    @Override
    public String toString() {
        return String.format("%-25s %-14s %-6.1f %-6d %-6s",
                getName(), getType(), getWeight(), (int)getPrice(), getEffect());
    }

}