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
        return String.format("%-20s %-14s %-10.1f %-9d %-10d %-10s",
                getName(), getType(), getWeight(), (int)getPrice(), getDamage(), getMaterial());
    }

    // names of the columns
    @Override
    public String getHeader() {
        return String.format("%-20s %-14s %-10s %-9s %-10s %-10s",
                "Name", "Type", "Weight", "Price", "Damage", "Material");
    }
}
