package ui;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Accessory;
import model.Armor;
import model.Knight;
import model.KnightItem;
import model.Weapon;
import service.ItemUtils;
import service.FileHandler;

public class Menu {
    private boolean exit = false;
    private Knight knight; // оголоси поле в класі Menu

    public Menu(Knight knight) {
        this.knight = knight;
    }


    public void start() {
        Scanner scanner = new Scanner(System.in);

        printMenu();
        while (!exit) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showAllArmors(scanner);
                    break;
                case 2:
                    showAllWeapons(scanner);
                    break;
                case 3:
                    showAllAccessories(scanner);
                    break;
                case 4:
                    startArmorHierarchyMenu(scanner);
                    break;
                case 5:
                    equipKnightMenu(scanner);
                    break;
                case 6:
                    disarmKnightMenu(scanner);
                    break;
                case 7:
                    calculateCost();
                    break;
                case 8:
                    sortArmorByWeight(scanner);
                    break;
                case 9:
                    sortWeaponsByWeight(scanner);
                    break;
                case 10:
                    sortAccessoriesByWeight(scanner);
                    break;
                case 11:
                    findByPriceRange();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Wrong choice. Please, try again");
            }

            if (!exit) {
                printMenu();
            }
        }
        scanner.close();
    }

    //----------------------Show All Items------------------------------------------------------------------
    private void showAllArmors(Scanner scanner) {
        System.out.println("\n==== Armors Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/armors.txt");
                List<Armor> armors = fileHandler.loadArmors();
                armors.forEach(System.out::println);
                break;
            case 2:
                knight.getArmors();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void showAllWeapons(Scanner scanner) {
        System.out.println("\n==== Weapons Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/weapons.txt");
                List<Weapon> weapons = fileHandler.loadWeapons();
                weapons.forEach(System.out::println);
                break;
            case 2:
                knight.getWeapons();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void showAllAccessories(Scanner scanner) {
        System.out.println("\n==== Accessories Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/accessories.txt");
                List<Accessory> accessories = fileHandler.loadAccessories();
                accessories.forEach(System.out::println);
                break;
            case 2:
                knight.getAccessories();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    //----------------------Armor Hierarchy------------------------------------------------------------------
    private void inventoryArmorHierarchy(List<Armor> armors) { 
        ItemUtils.inventoryArmorHierarchy(armors);
    }

    private void knightArmorHierarchy() { 
        knight.sortArmorsByHierarchy();
    }

    private void startArmorHierarchyMenu(Scanner scanner) {
        System.out.println("\n==== Armor Hierarchy Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/armors.txt");
                System.out.println("\n===============================Armor Hierarchy (INVENTORY)===============================");
                inventoryArmorHierarchy(fileHandler.loadArmors());
                break;
            case 2:
                System.out.println("\n===============================Armor Hierarchy (KNIGHT)===============================");
                knightArmorHierarchy();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Equip Knight------------------------------------------------------------------
    private void equipKnightMenu(Scanner scanner) {
        System.out.println("\n==== Equip the Knight Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                equipKnightWithArmor(scanner);
                break;
            case 2:
                equipKnightWithWeapon(scanner);
                break;
            case 3:
                equipKnightWithAccessory(scanner);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void equipKnightWithArmor(Scanner scanner) {
        FileHandler fileHandler = new FileHandler("data/armors.txt");
        List<Armor> armors = fileHandler.loadArmors();

        System.out.println("\n================ Available Armors ================");
        armors.forEach(System.out::println);

        System.out.print("Enter the name of the armor you want to equip: ");
        scanner.nextLine(); 
        String armorName = scanner.nextLine();

        Armor chosenArmor = armors.stream()
            .filter(a -> a.getName().equalsIgnoreCase(armorName))
            .findFirst()
            .orElse(null);

        if (chosenArmor != null) {
            if (knight.hasArmorType(chosenArmor.getType())) {
                System.out.println("The knight is already equipped with a " + chosenArmor.getType());
            } else {
                knight.equipArmor(chosenArmor);
                System.out.println(chosenArmor.getName() + " equipped on the knight.");

                armors.remove(chosenArmor);

                try (PrintWriter writer = new PrintWriter("data/armors.txt")) {
                    for (Armor armor : armors) {
                        writer.println(armor.getName() + "," + armor.getType() + "," + armor.getWeight() + "," +
                                    (int)armor.getPrice() + "," + armor.getProtectionLevel() + "," + armor.getMaterial());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Armor with name '" + armorName + "' not found.");
        }
    }

    private void equipKnightWithWeapon(Scanner scanner) {
        FileHandler fileHandler = new FileHandler("data/weapons.txt");
        List<Weapon> weapons = fileHandler.loadWeapons();
    
        System.out.println("\n================ Available Weapons ================");
        weapons.forEach(System.out::println);
    
        System.out.print("Enter the name of the weapon you want to equip: ");
        scanner.nextLine(); 
        String weaponName = scanner.nextLine();
    
        Weapon chosenWeapon = weapons.stream()
            .filter(a -> a.getName().equalsIgnoreCase(weaponName))
            .findFirst()
            .orElse(null);

        if(knight.countWeapons()==knight.getMaxWeapons()){
            System.out.println("The Knight already has maximum weapons");
        }
        else{
            if (chosenWeapon != null) {
                knight.equipWeapon(chosenWeapon);
                System.out.println(chosenWeapon.getName() + " equipped on the knight.");

                weapons.remove(chosenWeapon);

                try (PrintWriter writer = new PrintWriter("data/weapons.txt")) {
                    for (Weapon weapon : weapons) {
                        writer.println(weapon.getName() + "," + weapon.getType() + "," + weapon.getWeight() + "," +
                                    (int)weapon.getPrice() + "," + weapon.getDamage() + "," + weapon.getMaterial());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Weapon with name '" + weaponName + "' not found.");
            }
        }
    }

    private void equipKnightWithAccessory(Scanner scanner) {
        FileHandler fileHandler = new FileHandler("data/accessories.txt");
        List<Accessory> accessories = fileHandler.loadAccessories();
    
        System.out.println("\n================ Available Weapons ================");
        accessories.forEach(System.out::println);
    
        System.out.print("Enter the name of the weapon you want to equip: ");
        scanner.nextLine(); 
        String accessoryName = scanner.nextLine();
    
        Accessory chosenAccessory = accessories.stream()
            .filter(a -> a.getName().equalsIgnoreCase(accessoryName))
            .findFirst()
            .orElse(null);
    
        if (chosenAccessory != null) {
            knight.equipAccessory(chosenAccessory);
            System.out.println(chosenAccessory.getName() + " equipped on the knight.");

            accessories.remove(chosenAccessory);

            try (PrintWriter writer = new PrintWriter("data/accessories.txt")) {
                for (Accessory accessory : accessories) {
                    writer.println(accessory.getName() + "," + accessory.getType() + "," + accessory.getWeight() + "," +
                                (int)accessory.getPrice() + "," + accessory.getEffect());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Accessory with name '" + accessoryName + "' not found.");
        }
    }


    //----------------------Disarm Knight------------------------------------------------------------------
    private void disarmKnightMenu(Scanner scanner) {
        System.out.println("\n==== Disarm the Knight Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                disarmKnightArmor(scanner);
                break;
            case 2:
                disarmKnightWeapon(scanner);
                break;
            case 3:
                disarmKnightAccessory(scanner);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    
    private void disarmKnightArmor(Scanner scanner) {
        List<Armor> armors = knight.getArmors();
    
        System.out.println("\n================ Armors on the Knight ================");
        armors.forEach(System.out::println);
    
        System.out.print("Enter the name of the armor you want to disarm: ");
        scanner.nextLine(); 
        String armorName = scanner.nextLine();
    
        Armor chosenArmor = armors.stream()
            .filter(a -> a.getName().equalsIgnoreCase(armorName))
            .findFirst()
            .orElse(null);
    
        if (chosenArmor != null) {
            armors.remove(chosenArmor);  
            knight.equipmentCheck();     
            System.out.println(chosenArmor.getName() + " has been disarmed from the knight.");
    
            try (FileWriter fw = new FileWriter("data/armors.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter writer = new PrintWriter(bw)) {

                writer.println(String.join(",",
                    chosenArmor.getName(),
                    chosenArmor.getType(),
                    String.valueOf(chosenArmor.getWeight()),
                    String.valueOf((int) chosenArmor.getPrice()),
                    String.valueOf(chosenArmor.getProtectionLevel()),
                    chosenArmor.getMaterial()
                ));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Armor with name '" + armorName + "' not found on the knight.");
        }            
    }

    private void disarmKnightWeapon(Scanner scanner) {
        List<Weapon> weapons = knight.getWeapons();
    
        System.out.println("\n================ Weapons on the Knight ================");
        weapons.forEach(System.out::println);
    
        System.out.print("Enter the name of the weapon you want to disarm: ");
        scanner.nextLine(); 
        String weaponName = scanner.nextLine();
    
        Weapon chosenWeapon = weapons.stream()
            .filter(a -> a.getName().equalsIgnoreCase(weaponName))
            .findFirst()
            .orElse(null);
    
        if (chosenWeapon != null) {
            weapons.remove(chosenWeapon);  
            knight.equipmentCheck();     
            System.out.println(chosenWeapon.getName() + " has been disarmed from the knight.");
    
            try (FileWriter fw = new FileWriter("data/armors.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter writer = new PrintWriter(bw)) {

                writer.println(String.join(",",
                        chosenWeapon.getName(),
                        chosenWeapon.getType(),
                        String.valueOf(chosenWeapon.getWeight()),
                        String.valueOf((int) chosenWeapon.getPrice()),
                        String.valueOf((int) chosenWeapon.getDamage()),
                        chosenWeapon.getMaterial()
                    ));
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Weapon with name '" + weaponName + "' not found on the knight.");
        }            
    }
    
    private void disarmKnightAccessory(Scanner scanner) {
        List<Accessory> accessories = knight.getAccessories();
    
        System.out.println("\n================ Accessories on the Knight ================");
        accessories.forEach(System.out::println);
    
        System.out.print("Enter the name of the accessory you want to disarm: ");
        scanner.nextLine(); 
        String accessoryName = scanner.nextLine();
    
        Accessory chosenAccessory = accessories.stream()
            .filter(a -> a.getName().equalsIgnoreCase(accessoryName))
            .findFirst()
            .orElse(null);
    
        if (chosenAccessory != null) {
            accessories.remove(chosenAccessory);  
            knight.equipmentCheck();     
            System.out.println(chosenAccessory.getName() + " has been disarmed from the knight.");
    
            try (FileWriter fw = new FileWriter("data/armors.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter writer = new PrintWriter(bw)) {

                writer.println(String.join(",",
                        chosenAccessory.getName(),
                        chosenAccessory.getType(),
                        String.valueOf(chosenAccessory.getWeight()),
                        String.valueOf((int) chosenAccessory.getPrice()),
                        chosenAccessory.getEffect()
                    ));
                }
            catch (IOException e) {
                e.printStackTrace();
            }
    
        } else {
            System.out.println("Accessory with name '" + accessoryName + "' not found on the knight.");
        }            
    }
    

    //----------------------Calculate Knight`s armor cost------------------------------------------------------------------
    private int calculateCost() {
        int cost = 0;
        List<Armor> armors = knight.getArmors();
        for(Armor armor : armors){
            cost+=armor.getPrice();
        }
        return cost;
    }

    //----------------------Sort Items by Weight------------------------------------------------------------------
    private void sortArmorByWeight(Scanner scanner) { 
        System.out.println("\n==== Armor Weight Sorting Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/armors.txt");
                List<Armor> armors = fileHandler.loadArmors();
                List<KnightItem> items = new ArrayList<>(armors);
                ItemUtils.sortItemsWeight(items);
                items.forEach(System.out::println);
                break;
            case 2:
                knight.sortArmorsByWeight();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void sortWeaponsByWeight(Scanner scanner) { 
        System.out.println("\n==== Weapon Weight Sorting Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/weapons.txt");
                List<Weapon> weapons = fileHandler.loadWeapons();
                List<KnightItem> items = new ArrayList<>(weapons);
                ItemUtils.sortItemsWeight(items);
                items.forEach(System.out::println);
                break;
            case 2:
                knight.sortWeaponsByWeight();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void sortAccessoriesByWeight(Scanner scanner) { 
        System.out.println("\n==== Accessories Weight Sorting Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler("data/accessories.txt");
                List<Accessory> accessories = fileHandler.loadAccessories();
                List<KnightItem> items = new ArrayList<>(accessories);
                ItemUtils.sortItemsWeight(items);
                items.forEach(System.out::println);
                break;
            case 2:
                knight.sortAccessoriesByWeight();
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void findByPriceRange() { System.out.println("Found by price range."); }

    private void printMenu() {
        System.out.println("\n=========== Knight menu ============");
        System.out.println("1. Show all armors");
        System.out.println("2. Show all weapons");
        System.out.println("3. Show all accessories");
        System.out.println("4. Define the hierarchy of the armor");
        System.out.println("5. Equip the knight");
        System.out.println("6. Disarm the knight");
        System.out.println("7. Calculate the cost of the Knight`s armor");
        System.out.println("8. Sort the armor by weight");
        System.out.println("9. Sort the weapons by weight");
        System.out.println("10. Sort the accessories by weight");
        System.out.println("11. Find armor pieces by price range");
        System.out.println("0. Exit");
        System.out.print("Choose an action: ");
    }
}
