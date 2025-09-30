package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.Accessory;
import model.Armor;
import model.Knight;
import model.Weapon;

public class KnightTest {
    @Test
    public void testEquipArmorAddsArmorAndSetsEquipped() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);
        Armor armor = new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron");

        knight.equipArmor(armor);

        assertTrue(knight.getArmors().contains(armor));

        assertTrue(knight.getIsEquipped());
    }

    @Test
    public void testEquipWeaponAddsWeaponAndSetsEquipped_thenCountWeapons() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);
        assertEquals(0, knight.countWeapons());

        Weapon sword = new Weapon("Longsword", "Sword", 5.0, 120, 50, "Wood");

        knight.equipWeapon(sword);

        assertTrue(knight.getWeapons().contains(sword));
        assertTrue(knight.getIsEquipped());

        assertEquals(1, knight.countWeapons());
    }

    @Test
    public void testEquipAccessoryAddsAccessory() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);
        Accessory ring = new Accessory("Magic Ring", "Ring", 0.1, 300, "Luck");

        knight.equipAccessory(ring);

        assertTrue(knight.getAccessories().contains(ring));

        assertFalse(knight.getIsEquipped());
    }

    @Test
    public void givenAllArmorAndWeapon_whenEquipmentCheck_thenEquippedAndFullyEquipped() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);
        Armor helmet = new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron");
        Armor chestplate = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        Armor pants = new Armor("Chainmail Pants", "Pants", 8.0, 90, 4, "Chainmail");
        Armor boots = new Armor("Plate Greaves", "Boots", 6.0, 120, 5, "Steel");
        Armor gloves = new Armor("Bronze Gauntlets", "Gloves", 3.0, 45, 2, "Bronze");
        Weapon sword = new Weapon("Longsword", "Sword", 5.0, 120, 50, "Wood");

        knight.equipArmor(helmet);
        knight.equipArmor(chestplate);
        knight.equipArmor(pants);
        knight.equipArmor(boots);
        knight.equipArmor(gloves);

        knight.equipWeapon(sword);

        assertTrue(knight.getIsEquipped());
        assertTrue(knight.getIsFullyEquipped());
    }

    @Test
    public void testKnightEquipmentMethods() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);

        assertFalse(knight.hasAllArmorTypes());

        Armor helmet = new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron");
        Armor chestplate = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        knight.equipArmor(helmet);
        knight.equipArmor(chestplate);

        List<String> missing = knight.missingArmorTypes();
        assertTrue(missing.contains("Boots"));
        assertTrue(missing.contains("Pants"));
        assertTrue(missing.contains("Gloves"));
        assertFalse(missing.contains("Helmet"));
        assertFalse(missing.contains("Chestplate"));

        assertFalse(knight.hasAllArmorTypes());

        assertTrue(knight.hasArmorType("Helmet"));
        assertFalse(knight.hasArmorType("Boots"));

        List<String> needed = knight.neededEquipment();
        assertTrue(needed.containsAll(Arrays.asList("Boots", "Pants", "Gloves", "Weapon")));
        assertFalse(needed.contains("Helmet"));
        assertFalse(needed.contains("Chestplate"));

        Armor pants = new Armor("Chainmail Pants", "Pants", 8.0, 90, 4, "Chainmail");
        Armor boots = new Armor("Plate Greaves", "Boots", 6.0, 120, 5, "Steel");
        Armor gloves = new Armor("Bronze Gauntlets", "Gloves", 3.0, 45, 2, "Bronze");

        Weapon sword = new Weapon("Longsword", "Sword", 5.0, 120, 50, "Wood");

        knight.equipArmor(pants);
        knight.equipArmor(boots);
        knight.equipArmor(gloves);

        knight.equipWeapon(sword);

        assertTrue(knight.neededEquipment().isEmpty());
    }

    @Test
    public void testCalculateSumOfArmorPricesAndWeights() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);

        Armor helmet = new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron");
        Armor chestplate = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        Armor boots = new Armor("Plate Greaves", "Boots", 6.0, 120, 5, "Steel");

        List<Armor> armors = Arrays.asList(helmet, chestplate, boots);

        knight.equipArmor(chestplate);
        knight.equipArmor(boots);
        knight.equipArmor(helmet);

        assertEquals(1370, knight.calculateSum(armors, Armor::getPrice)); // 50 + 1200 + 120
        assertEquals(23.5, knight.calculateSum(armors, Armor::getWeight)); // 2.5 + 15.0 + 6.0
    }

    @Test
    public void testSortArmorsByWeightSortsCorrectly() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);

        Armor helmet = new Armor("Iron Helmet", "Helmet", 2.5, 50, 10, "Iron");
        Armor chestplate = new Armor("Dragon Armor", "Chestplate", 15.0, 1200, 50, "Dragonhide");
        Armor boots = new Armor("Plate Greaves", "Boots", 6.0, 120, 5, "Steel");

        knight.equipArmor(chestplate);
        knight.equipArmor(boots);
        knight.equipArmor(helmet);

        knight.sortArmorsByWeight();

        List<Armor> sorted = knight.getArmors();
        assertEquals(helmet, sorted.get(0));
        assertEquals(boots, sorted.get(1));
        assertEquals(chestplate, sorted.get(2));
    }

    @Test
    public void testSortWeaponsByWeightSortsCorrectly() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);

        Weapon sword = new Weapon("Sword", "Blade", 5.0, 100, 20, "Steel");
        Weapon axe = new Weapon("Axe", "Axe", 7.5, 120, 25, "Iron");
        Weapon dagger = new Weapon("Dagger", "Knife", 2.0, 50, 10, "Steel");

        knight.equipWeapon(sword);
        knight.equipWeapon(axe);
        knight.equipWeapon(dagger);

        knight.sortWeaponsByWeight();

        List<Weapon> sorted = knight.getWeapons();
        assertEquals(dagger, sorted.get(0));
        assertEquals(sword, sorted.get(1));
        assertEquals(axe, sorted.get(2));
    }

    @Test
    public void testSortAccessoriesByWeightSortsCorrectly() {
        Knight knight = new Knight("Arthur", 1, 100, 100, 4, 0.0, 0.0, false, false);

        Accessory ring = new Accessory("Ring", "Magic", 0.1, 50, "Increase mana");
        Accessory amulet = new Accessory("Amulet", "Magic", 0.5, 150, "Increase health");
        Accessory bracelet = new Accessory("Bracelet", "Magic", 0.2, 80, "Increase stamina");

        knight.equipAccessory(amulet);
        knight.equipAccessory(bracelet);
        knight.equipAccessory(ring);

        knight.sortAccessoriesByWeight();

        List<Accessory> sorted = knight.getAccessories();
        assertEquals(ring, sorted.get(0));
        assertEquals(bracelet, sorted.get(1));
        assertEquals(amulet, sorted.get(2));
    }
}
