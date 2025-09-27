package ui;

import java.util.List;

import model.Accessory;
import model.Armor;
import model.Knight;
import model.Weapon;
import service.FileHandler;

public class Main {
    public static void main(String[] args) {
        Knight knight = new Knight("Sir Lancelot", 10, 100, 100, 4, 0, 0, false, false);
        // Armor gloves = new Armor("Reinforced Gloves", "Gloves", 2.8, 70, 3, "Iron");
        // knight.equipArmor(gloves);        
        Menu menu = new Menu(knight);
        menu.start(); // start menu
    }
}
