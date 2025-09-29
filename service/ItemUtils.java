package service;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Armor;
import model.KnightItem;
import model.MaterialItem;

public class ItemUtils {

    // sorting any inventory item list by weight
    public static void sortItemsWeight(List<KnightItem> items) {
        items.sort(Comparator.comparingDouble(KnightItem::getWeight).reversed());
    }

    // sorting any inventory item list by price
    public static void sortItemsPrice(List<KnightItem> items) {
        items.sort(Comparator.comparingDouble(KnightItem::getPrice).reversed());
    }

    // sorting any inventory item list by type (alphabetical order)
    public static void sortItemsType(List<KnightItem> items) {
        java.util.Collections.sort(items, Collator.getInstance());
    }

    // prints inventory armor hierarchy
    public static void inventoryArmorHierarchy(List<Armor> armors) {
        List<String> allTypes = Arrays.asList("Helmet", "Chestplate", "Pants", "Boots", "Gloves");
    
        allTypes.forEach(type -> 
            armors.stream()
                  .filter(armor -> armor.getType().equalsIgnoreCase(type))
                  .forEach(System.out::println)
        );
    }

    public static void inventoryMaterialHierarchy(List<KnightItem> items) {
        List<String> allMaterials = Arrays.asList(
            "Dragonhide", "Steel", "Iron", "Chainmail", "Bronze", "Gold", "Leather", "Wood"
        );

        Map<String, List<KnightItem>> grouped = items.stream()
                .filter(item -> item instanceof MaterialItem)
                .collect(Collectors.groupingBy(item -> ((MaterialItem) item).getMaterial()));

        for (String material : allMaterials) {
            List<KnightItem> group = grouped.get(material);
            if (group != null && !group.isEmpty()) {
                group.forEach(System.out::println);
            }
        }
    }

    
    // filtering any inventory item list by price range
    public static List<KnightItem> filterItemsByPrice(List<KnightItem> items, double min, double max) {
        return items.stream()
                    .filter(item -> item.getPrice() >= min && item.getPrice() <= max)
                    .collect(Collectors.toList());
    }

    // searching for any inventory item list by type name 
    public static List<KnightItem> findItemsByType(List<KnightItem> items, String type) {
        return items.stream()
                    .filter(item -> item.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
    }

    // utility method to display a list of KnightItem objects 
    public static void printItems(List<KnightItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items to display.");
        } else {
            for (KnightItem item : items) {
                System.out.println(item);
            }
        }
    }
    
}
