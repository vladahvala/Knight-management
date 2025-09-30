package test;

import model.Weapon;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WeaponTest {
    @Test
    public void testConstructorAndGetters() {
        Weapon weapon = new Weapon("Steel blade", "Blade", 3.0, 200, 50, "Steel");
        assertEquals("Steel blade", weapon.getName());
        assertEquals("Blade", weapon.getType());
        assertEquals(3.0, weapon.getWeight(), 0.01);
        assertEquals(200, weapon.getPrice(), 0.01);
        assertEquals(50, weapon.getDamage());
        assertEquals("Steel", weapon.getMaterial());
    }

    @Test
    public void testUseMethod() {
        Weapon weapon = new Weapon("Steel blade", "Blade", 3.0, 1200, 50, "Steel");
        weapon.use();
    }

    @Test
    public void testToStringAndGetHeader() {
        Weapon weapon = new Weapon("Steel blade", "Blade", 3.0, 1200, 50, "Steel");

        String expectedHeader = String.format("%-20s %-14s %-10s %-9s %-10s %-10s",
                "Name", "Type", "Weight", "Price", "Damage", "Material");
        String expectedToString = String.format("%-20s %-14s %-10.1f %-9d %-10d %-10s",
                "Steel blade", "Blade", 3.0, 1200, 50, "Steel");
        assertEquals(expectedHeader, weapon.getHeader());
        assertEquals(expectedToString, weapon.toString());
    }

    @Test
    public void testSetters() {
        Weapon weapon = new Weapon("Steel blade", "Blade", 3.0, 1200, 50, "Steel");
        weapon.setDamage(60);
        weapon.setMaterial("Steel");
        assertEquals(60, weapon.getDamage());
        assertEquals("Steel", weapon.getMaterial());
    }

}
