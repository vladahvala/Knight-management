package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Armor;
import model.Weapon;
import model.Accessory;

public class FileHandler {
    private String fileName;

    public FileHandler (String fileName){
        this.fileName = fileName;
    }

    public List<Armor> loadArmors() {
        List<Armor> armors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String type = parts[1];
                double weight = Double.parseDouble(parts[2]);
                int price = Integer.parseInt(parts[3]);
                int protectionLevel = Integer.parseInt(parts[4]);
                armors.add(new Armor(name, type, weight, price, protectionLevel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return armors;
    }

    public List<Weapon> loadWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String type = parts[1];
                double weight = Double.parseDouble(parts[2]);
                int price = Integer.parseInt(parts[3]);
                int damage = Integer.parseInt(parts[4]);
                weapons.add(new Weapon(name, type, weight, price, damage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weapons;
    }

    public List<Accessory> loadAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String type = parts[1];
                double weight = Double.parseDouble(parts[2]);
                int price = Integer.parseInt(parts[3]);
                String effect = parts[4];
                accessories.add(new Accessory(name, type, weight, price, effect));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessories;
    }

}
