package model;

public class Weapon extends KnightItem{

    private int damage;

    public Weapon(String name, String type, double weight, int price, int damage) {
        super(name, type, weight, price);
        this.damage = damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
    
    @Override
    public void use(){
        System.out.println("Causing damage.");
    }

    @Override
    public String toString() {
        return super.toString() + ", Damage: " + damage; 
    }

}
