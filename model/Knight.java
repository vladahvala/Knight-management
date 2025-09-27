package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.Comparator;

public class Knight {
    private String name;
    private int level;
    private int coins;
    private int health;
    private int maxWeapons = 4;
    private double totalWeight;
    private double totalPrice;
    private boolean isEquipped;
    private boolean isFullyEquipped;
    private List<Armor> armors;
    private List<Weapon> weapons;
    private List<Accessory> accessories;

    public Knight(String name, int level, int coins, int health, int maxWeapons, double totalWeight, 
    double totalPrice, boolean isEquipped, boolean isFullyEquipped) {
        this.name = name;
        this.level = level;
        this.coins = coins;
        this.health = health;
        this.maxWeapons = maxWeapons;
        this.totalWeight = totalWeight;
        this.totalPrice = totalPrice;
        this.isEquipped = isEquipped;
        this.isFullyEquipped = isFullyEquipped;
        this.armors = new ArrayList<>();
        this.weapons = new ArrayList<>();
        this.accessories = new ArrayList<>();
    }

    // Getters/Setters
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }
 
    public void setLevel(int level) {
        this.level = level;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setMaxWeapons(int maxWeapons) {
        this.maxWeapons = maxWeapons;
    }

    public int getMaxWeapons() {
        return maxWeapons;
    }
 
    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
 
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }
 
    public void setIsEquipped (boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    public boolean getIsFullyEquipped() {
        return isFullyEquipped;
    }
 
    public void setIsFullyEquipped (boolean isFullyEquipped) {
        this.isFullyEquipped = isFullyEquipped;
    }

    public List<Armor> getArmors() {
        return armors;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }


    // Knight methods

    // Equip
    public void equipArmor(Armor a){
        armors.add(a);
        equipmentCheck();
    }

    public void equipWeapon(Weapon w){
        weapons.add(w);
        equipmentCheck();
    }

    public void equipAccessory(Accessory ac){
        accessories.add(ac);
    }

    // checks isEquipped/isFullyEquipped
    public void equipmentCheck(){
       if(!armors.isEmpty() || !weapons.isEmpty()) isEquipped=true;
       if(hasAllArmorTypes() && !weapons.isEmpty()) isFullyEquipped=true;
    }

    public void neededEquipment(){
        System.out.println("Needed equipment: \n");
        for (String type : missingArmorTypes()) {
            System.out.println(type + "\n");
        }
        if(weapons.isEmpty()) System.out.println("weapon");
    }

    public boolean hasAllArmorTypes() {
        List<String> allTypes = Arrays.asList("Helmet", "Chestplate", "Boots", "Pants", "Gloves");
        Set<String> typesInList = new HashSet<>();
        for (Armor armor : armors) {
            typesInList.add(armor.getType());
        }
        return typesInList.containsAll(allTypes);
    }

    public List<String> missingArmorTypes() {
        List<String> allTypes = Arrays.asList("Helmet", "Chestplate", "Boots", "Pants", "Gloves");
        Set<String> typesInList = new HashSet<>();
    
        for (Armor armor : armors) {
            typesInList.add(armor.getType());
        }
    
        List<String> missing = new ArrayList<>();
        for (String type : allTypes) {
            if (!typesInList.contains(type)) {
                missing.add(type);
            }
        }
        return missing;
    }
    
    public boolean hasArmorType(String type) {
        return armors.stream()
                     .anyMatch(armor -> armor.getType().equalsIgnoreCase(type));
    }

    // Couning weapons
    public int countWeapons() {
        return weapons.size();
    }    
    

    // calculating sum of items    
    public <T extends KnightItem> double calculateSum(List<T> items, Function<T, Double> getter) {
        return items.stream()
                    .map(getter)
                    .reduce(0.0, Double::sum);
    }

    // sorting items
    public void sortArmorsByWeight(){
        armors.sort(Comparator.comparingDouble(Armor::getWeight));
    }

    public void sortWeaponsByWeight(){
        weapons.sort(Comparator.comparingDouble(Weapon::getWeight));
    }

    public void sortAccessoriesByWeight(){
        accessories.sort(Comparator.comparingDouble(Accessory::getWeight));
    }

    // returns the list of affordable items
    public <T extends KnightItem> List<String> affordableItems(List<T> items, Function<T, Double> priceGetter) {
        List<String> affordable = new ArrayList<>();
        for (T item : items) {
            if (priceGetter.apply(item) <= getCoins()) {
                affordable.add(item.getName());
            }
        }
        return affordable;
    }    
    
}
