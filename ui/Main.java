package ui;

import java.util.List;

import model.Accessory;
import model.Armor;
import model.Knight;
import model.Weapon;
import service.FileHandler;

public class Main {
    public static void main(String[] args) {
        // Menu menu = new Menu();
        // menu.start(); // start menu
        FileHandler fileHandler = new FileHandler("data/armors.txt");
        List<Armor> armors = fileHandler.loadArmors();

        System.out.println("Armors loaded:");
        for (Armor armor : armors) {
            System.out.println(armor);
        }

    }
}
