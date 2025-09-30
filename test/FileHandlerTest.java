package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import model.Armor;
import model.Weapon;
import model.Accessory;
import service.FileHandler;

public class FileHandlerTest {

    // sub method for creating the temp files
    private Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("temp", ".txt");
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }

    // --- Armors ---
    @Test
    public void testLoadArmorsReadsCorrectly() throws IOException {
        String testData = "Iron Helmet,Helmet,2.5,50,10,Iron\n" +
                "Dragon Armor,Chestplate,15.0,1200,50,Dragonhide";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Armor> armors = handler.loadArmors();

        assertEquals(2, armors.size());
        assertEquals("Iron Helmet", armors.get(0).getName());
        assertEquals("Chestplate", armors.get(1).getType());

        Files.delete(tempFile);
    }

    @Test
    public void testLoadArmorsIgnoresBadLines() throws IOException {
        String testData = "Iron Helmet,Helmet,2.5,50,10,Iron\n" +
                "BadLine,Helmet,1.0";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Armor> armors = handler.loadArmors();

        assertEquals(1, armors.size());
        assertEquals("Iron Helmet", armors.get(0).getName());

        Files.delete(tempFile);
    }

    @Test
    public void testLoadArmorsEmptyFileReturnsEmptyList() throws IOException {
        Path tempFile = createTempFile("");
        FileHandler handler = new FileHandler(tempFile.toString());
        List<Armor> armors = handler.loadArmors();
        assertTrue(armors.isEmpty());
        Files.delete(tempFile);
    }

    // --- Weapons ---
    @Test
    public void testLoadWeaponsReadsCorrectly() throws IOException {
        String testData = "Steel Sword,Sword,7.0,100,15,Steel\n" +
                "Longbow,Bow,4.5,85,12,Wood\n" +
                "Spear,Spear,6.5,90,13,Wood";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Weapon> weapons = handler.loadWeapons();

        assertEquals(3, weapons.size());
        assertEquals("Steel Sword", weapons.get(0).getName());
        assertEquals("Bow", weapons.get(1).getType());
        assertEquals(6.5, weapons.get(2).getWeight(), 0.0001);

        Files.delete(tempFile);
    }

    @Test
    public void testLoadWeaponsIgnoresBadLines() throws IOException {
        String testData = "Steel Sword,Sword,7.0,100,15,Steel\n" +
                "BadLine,Sword,1.0";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Weapon> weapons = handler.loadWeapons();

        assertEquals(1, weapons.size());
        assertEquals("Steel Sword", weapons.get(0).getName());

        Files.delete(tempFile);
    }

    @Test
    public void testLoadWeaponsEmptyFileReturnsEmptyList() throws IOException {
        Path tempFile = createTempFile("");
        FileHandler handler = new FileHandler(tempFile.toString());
        List<Weapon> weapons = handler.loadWeapons();
        assertTrue(weapons.isEmpty());
        Files.delete(tempFile);
    }

    // --- Accessories ---
    @Test
    public void testLoadAccessoriesReadsCorrectly() throws IOException {
        String testData = "Magic Ring,Ring,0.1,200,Mana+5\n" +
                "Health Amulet,Amulet,0.3,150,HP+10\n" +
                "Silver Necklace,Necklace,0.2,80,Charisma+2\n" +
                "Protection Talisman,Talisman,0.4,120,Shield+3\n" +
                "Lucky Coin,Coin,0.05,20,Luck+1";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Accessory> accessories = handler.loadAccessories();

        assertEquals(5, accessories.size());
        assertEquals("Magic Ring", accessories.get(0).getName());
        assertEquals("Amulet", accessories.get(1).getType());
        assertEquals(0.2, accessories.get(2).getWeight(), 0.0001);
        assertEquals(120, accessories.get(3).getPrice());
        assertEquals("Luck+1", accessories.get(4).getEffect());

        Files.delete(tempFile);
    }

    @Test
    public void testLoadAccessoriesIgnoresBadLines() throws IOException {
        String testData = "Health Amulet,Amulet,0.3,150,HP+10\n" +
                "BadLine,Amulet,1.0";
        Path tempFile = createTempFile(testData);

        FileHandler handler = new FileHandler(tempFile.toString());
        List<Accessory> accessories = handler.loadAccessories();

        assertEquals(1, accessories.size());
        assertEquals("Health Amulet", accessories.get(0).getName());

        Files.delete(tempFile);
    }

    @Test
    public void testLoadAccessoriesEmptyFileReturnsEmptyList() throws IOException {
        Path tempFile = createTempFile("");
        FileHandler handler = new FileHandler(tempFile.toString());
        List<Accessory> accessories = handler.loadAccessories();
        assertTrue(accessories.isEmpty());
        Files.delete(tempFile);
    }
}
