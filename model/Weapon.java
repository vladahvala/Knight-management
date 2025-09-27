package model;

public class Weapon extends KnightItem implements MaterialItem{

    private int damage;
    private String material;

    public Weapon(String name, String type, double weight, int price, int damage, String material) {
        super(name, type, weight, price);
        this.damage = damage;
        this.material = material;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(String material) {
        this.material = material;
    }
    
    @Override
    public void use(){
        System.out.println("Causing damage.");
    }

    @Override
    public String toString() {
        return String.format("%-20s %-14s %-6.1f %-6d %-6d %-10s",
                getName(), getType(), getWeight(), (int)getPrice(), getDamage(), getMaterial());
    }

}
