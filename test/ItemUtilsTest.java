package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Accessory;
import model.Armor;
import model.KnightItem;
import model.Weapon;
import service.ItemUtils;

public class ItemUtilsTest {
    @Test
    public void testSortItemsWeight() {
        List<KnightItem> items = List.of(
                new Armor("Helmet", "Helmet", 3.0, 100, 5, "Iron"),
                new Weapon("Sword", "Blade", 5.0, 150, 20, "Steel"),
                new Accessory("Ring", "Magic", 0.1, 50, "Increase mana"));

        List<KnightItem> sorted = new ArrayList<>(items);
        ItemUtils.sortItemsWeight(sorted);

        assertEquals("Ring", sorted.get(0).getName());
        assertEquals("Helmet", sorted.get(1).getName());
        assertEquals("Sword", sorted.get(2).getName());
    }

    @Test
    public void testSortItemsPrice() {
        List<KnightItem> items = List.of(
                new Armor("Helmet", "Helmet", 3.0, 100, 5, "Iron"),
                new Weapon("Sword", "Blade", 5.0, 150, 20, "Steel"),
                new Accessory("Ring", "Magic", 0.1, 50, "Increase mana"));

        List<KnightItem> sorted = new ArrayList<>(items);
        ItemUtils.sortItemsPrice(sorted);

        assertEquals("Ring", sorted.get(0).getName());
        assertEquals("Helmet", sorted.get(1).getName());
        assertEquals("Sword", sorted.get(2).getName());
    }

    @Test
    public void testGetArmorHierarchy() {
        List<Armor> armors = List.of(
                new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron"),
                new Armor("Steel Chestplate", "Chestplate", 15.0, 200, 50, "Steel"),
                new Armor("Leather Gloves", "Gloves", 1.0, 30, 5, "Leather"));

        List<Armor> sorted = ItemUtils.getArmorHierarchy(armors);

        assertEquals("Iron Helmet", sorted.get(0).getName());
        assertEquals("Steel Chestplate", sorted.get(1).getName());
        assertEquals("Leather Gloves", sorted.get(2).getName());
    }

    @Test
    public void testGetMaterialHierarchy() {
        List<KnightItem> items = List.of(
                new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide"),
                new Weapon("Steel Sword", "Sword", 7.0, 100, 15, "Steel"),
                new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron"),
                new Weapon("Wooden Bow", "Bow", 4.5, 85, 12, "Wood"));

        List<KnightItem> sorted = ItemUtils.getMaterialHierarchy(items);

        assertEquals("Dragon Armor", sorted.get(0).getName());
        assertEquals("Steel Sword", sorted.get(1).getName());
        assertEquals("Iron Helmet", sorted.get(2).getName());
        assertEquals("Wooden Bow", sorted.get(3).getName());
    }

    @Test
    public void testFilterItemsByPrice() {
        List<KnightItem> items = List.of(
                new Armor("Helmet", "Helmet", 3.0, 100, 5, "Iron"),
                new Weapon("Sword", "Blade", 5.0, 150, 20, "Steel"),
                new Accessory("Ring", "Magic", 0.1, 50, "Increase mana"));

        List<KnightItem> filtered = ItemUtils.filterItemsByPrice(items, 60, 200);

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(i -> i.getName().equals("Helmet")));
        assertTrue(filtered.stream().anyMatch(i -> i.getName().equals("Sword")));
        assertFalse(filtered.stream().anyMatch(i -> i.getName().equals("Ring")));
    }

    @Test
    public void testFindItemsByType() {
        List<KnightItem> items = List.of(
                new Armor("Helmet", "Helmet", 3.0, 100, 5, "Iron"),
                new Weapon("Sword", "Blade", 5.0, 150, 20, "Steel"),
                new Accessory("Ring", "Magic", 0.1, 50, "Increase mana"));

        List<KnightItem> filtered = ItemUtils.findItemsByType(items, "Blade");

        assertEquals(1, filtered.size());
        assertTrue(filtered.stream().anyMatch(i -> i.getType().equals("Blade")));
    }

}
