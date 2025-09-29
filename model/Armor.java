package model;

public class Armor extends KnightItem implements MaterialItem{

    private int protectionLevel;
    private String material;

    public Armor(String name, String type, double weight, int price, int protectionLevel, String material) {
        super(name, type, weight, price);
        this.protectionLevel = protectionLevel;
        this.material = material;
    }

    public void setProtectionLevel(int protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public int getProtectionLevel() {
        return protectionLevel;
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
        System.out.println("Protecting the knight.");
    }

    @Override
    public String toString() {
        return String.format("%-30s %-14s %-9.1f %-9d %-14d %-10s",
                getName(), getType(), getWeight(), (int) getPrice(), getProtectionLevel(), getMaterial());
    }

    // names of the columns 
    @Override
    public String getHeader() {
        return String.format("%-30s %-14s %-9s %-9s %-14s %-10s",
                "Name", "Type", "Weight", "Price", "Protection", "Material");
    }
}
