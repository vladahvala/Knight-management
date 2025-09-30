package test;

import model.Armor;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArmorTest {
    @Test
    public void testConstructorAndGetters() {
        Armor armor = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        assertEquals("Dragon Armor", armor.getName());
        assertEquals("Chestplate", armor.getType());
        assertEquals(15.0, armor.getWeight(), 0.01);
        assertEquals(1200, armor.getPrice(), 0.01);
        assertEquals(50, armor.getProtectionLevel());
        assertEquals("Dragonhide", armor.getMaterial());
    }

    @Test
    public void testUseMethod() {
        Armor armor = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        armor.use();
    }

    @Test
    public void testToStringAndGetHeader() {
        Armor armor = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");

        String expectedHeader = String.format("%-30s %-14s %-9s %-9s %-14s %-10s",
                "Name", "Type", "Weight", "Price", "Protection", "Material");
        String expectedToString = String.format("%-30s %-14s %-9.1f %-9d %-14d %-10s",
                "Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");

        assertEquals(expectedHeader, armor.getHeader());
        assertEquals(expectedToString, armor.toString());
    }

    @Test
    public void testSetters() {
        Armor armor = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        armor.setProtectionLevel(60);
        armor.setMaterial("Steel");
        assertEquals(60, armor.getProtectionLevel());
        assertEquals("Steel", armor.getMaterial());
    }

}
