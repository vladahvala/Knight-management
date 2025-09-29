package model;

public abstract class KnightItem {
    private String name;
    private String type;
    private double weight;
    private int price;

    public KnightItem(String name, String type, double weight, int price) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.price = price;
    }

    // abstract methods
    public abstract void use();
    public abstract String getHeader();
    
    // Getters/Setters
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }
 
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }
 
    public void setPrice(int price) {
        this.price = price;
    }

    // toString
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Weight: " + weight + ", Price: " + price;
    }

}

