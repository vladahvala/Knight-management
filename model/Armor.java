package model;

public class Armor extends KnightItem{

    private int protectionLevel;

    public Armor(String name, String type, double weight, int price, int protectionLevel) {
        super(name, type, weight, price);
        this.protectionLevel = protectionLevel;
    }

    public void setProtectionLevel(int protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public int getProtectionLevel() {
        return protectionLevel;
    }
    
    @Override
    public void use(){
        System.out.println("Protecting the knight.");
    }

    @Override
    public String toString() {
        return super.toString() + ", Protection Level: " + protectionLevel;
    }

}
