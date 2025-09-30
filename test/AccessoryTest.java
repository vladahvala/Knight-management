package test;

import model.Accessory;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccessoryTest {
    @Test
    public void testConstructorAndGetters() {
        Accessory accessory = new Accessory("Magic Amulet", "Amulet", 1.0, 2000, "Mana+20");
        assertEquals("Magic Amulet", accessory.getName());
        assertEquals("Amulet", accessory.getType());
        assertEquals(1.0, accessory.getWeight(), 0.01);
        assertEquals(2000, accessory.getPrice(), 0.01);
        assertEquals("Mana+20", accessory.getEffect());
    }

    @Test
    public void testUseMethod() {
        Accessory accessory = new Accessory("Magic Amulet", "Amulet", 1.0, 2000, "Mana+20");
        accessory.use();
    }

    @Test
    public void testToStringAndGetHeader() {
        Accessory accessory = new Accessory("Magic Amulet", "Amulet", 1.0, 2000, "Mana+20");

        String expectedHeader = String.format("%-25s %-14s %-10s %-9s %-6s",
                "Name", "Type", "Weight", "Price", "Effect");
        String expectedToString = String.format("%-25s %-14s %-10.1f %-9d %-6s",
                "Magic Amulet", "Amulet", 1.0, 2000, "Mana+20");

        assertEquals(expectedHeader, accessory.getHeader());
        assertEquals(expectedToString, accessory.toString());
    }

    @Test
    public void testSetters() {
        Accessory accessory = new Accessory("Magic Amulet", "Amulet", 1.0, 2000, "Mana+20");
        accessory.setEffect("Health+2");
        assertEquals("Health+2", accessory.getEffect());
    }

}