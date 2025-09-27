package ui;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Predicate;

import model.Accessory;
import model.Armor;
import model.Knight;
import model.KnightItem;
import model.Weapon;
import service.ItemUtils;
import service.FileHandler;

public class Menu {
    private boolean exit = false;
    private Knight knight; 

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
                    showAllItemsMenu(scanner);
                    break;
                case 2:
                    startArmorHierarchyTypeMenu(scanner);
                    break;
                case 3:
                    itemHierarchyByMaterialMenu(scanner);
                    break;
                case 4:
                    equipKnightMenu(scanner);
                    break;
                case 5:
                    disarmKnightMenu(scanner);
                    break;
                case 6:
                    calcCostMenu(scanner);
                    break;
                case 7:
                    calcWeightMenu(scanner);
                    break;
                case 8:
                    sortByWeightMenu(scanner);
                    break;
                case 9:
                    findByPriceRangeMenu(scanner);
                    break;
                case 10:
                    if(knight.getIsFullyEquipped()) System.out.println("Is fully equipped");
                    else{
                        System.out.println("Is not fully equipped\n");
                        knight.neededEquipment();
                    }
                    break;
                case 11:
                    affordableItemsMenu(scanner);
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
    private void showAllItemsMenu(Scanner scanner) {
        System.out.println("\n==== Show all items Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                showAllArmors(scanner);
                break;
            case 2:
                showAllWeapons(scanner);
                break;
            case 3:
                showAllAccessories(scanner);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void showAllArmors(Scanner scanner) {
        showAllItems(scanner,
                "data/armors.txt",
                FileHandler::loadArmors,
                knight::getArmors,
                "Armors");
    }
    
    private void showAllWeapons(Scanner scanner) {
        showAllItems(scanner,
                "data/weapons.txt",
                FileHandler::loadWeapons,
                knight::getWeapons,
                "Weapons");
    }
    
    private void showAllAccessories(Scanner scanner) {
        showAllItems(scanner,
                "data/accessories.txt",
                FileHandler::loadAccessories,
                knight::getAccessories,
                "Accessories");
    }

    private <T> void showAllItems(
        Scanner scanner,
        String filePath,
        Function<FileHandler, List<T>> loader,
        Supplier<List<T>> knightGetter,
        String itemName
    ) {
        System.out.println("\n==== " + itemName + " Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                List<T> items = loader.apply(fileHandler);
                System.out.println("\n===============================" + itemName + " Inventory ===============================");
                items.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                System.out.println("\n===============================" + itemName + " Knight ===============================");
                knightItems.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Armor Hierarchy by type------------------------------------------------------------------
    private void startArmorHierarchyTypeMenu(Scanner scanner) {
        FileHandler fileHandler = new FileHandler("data/armors.txt");
        List<Armor> armors = fileHandler.loadArmors();
        System.out.println("\n===============================Armor Hierarchy (INVENTORY)===============================");
        ItemUtils.inventoryArmorHierarchy(armors);
    }


    //----------------------Armor Hierarchy by material------------------------------------------------------------------
    private <T extends KnightItem> void itemHierarchyByMaterialMenuGeneric(
        Scanner scanner,
        String filePath,
        Function<FileHandler, List<T>> loader,
        String itemName
    ) {
        FileHandler fileHandler = new FileHandler(filePath);
        List<T> items = loader.apply(fileHandler);
        List<KnightItem> itemList = new ArrayList<>(items);

        System.out.println("\n=============================== " + itemName + " Hierarchy by Material ===============================");
        ItemUtils.inventoryMaterialHierarchy(itemList);
        itemList.forEach(System.out::println);
    }

    private void itemHierarchyByMaterialMenu(Scanner scanner) {
        System.out.println("\n==== Item Hierarchy by Material Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                itemHierarchyByMaterialMenuGeneric(scanner, "data/armors.txt", FileHandler::loadArmors, "Armor");
                break;
            case 2:
                itemHierarchyByMaterialMenuGeneric(scanner, "data/weapons.txt", FileHandler::loadWeapons, "Weapon");
                break;
            case 3:
                itemHierarchyByMaterialMenuGeneric(scanner, "data/accessories.txt", FileHandler::loadAccessories, "Accessory");
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
                equipItem(scanner,
                        "data/armors.txt",
                        FileHandler::loadArmors,
                        knight::equipArmor,
                        (writer, armor) -> {
                            Armor a = (Armor) armor;
                            writer.println(a.getName() + "," + a.getType() + "," + a.getWeight() + "," +
                                    (int) a.getPrice() + "," + a.getProtectionLevel() + "," + a.getMaterial());
                        },
                        item -> {
                            Armor a = (Armor) item;
                            return !knight.hasArmorType(a.getType());
                        });
                break;
    
            case 2:
                equipItem(scanner,
                        "data/weapons.txt",
                        FileHandler::loadWeapons,
                        knight::equipWeapon,
                        (writer, weapon) -> {
                            Weapon w = (Weapon) weapon;
                            writer.println(w.getName() + "," + w.getType() + "," + w.getWeight() + "," +
                                    (int) w.getPrice() + "," + w.getDamage() + "," + w.getMaterial());
                        },
                        item -> knight.countWeapons() < knight.getMaxWeapons());
                break;
    
            case 3:
                equipItem(scanner,
                        "data/accessories.txt",
                        FileHandler::loadAccessories,
                        knight::equipAccessory,
                        (writer, accessory) -> {
                            Accessory ac = (Accessory) accessory;
                            writer.println(ac.getName() + "," + ac.getType() + "," + ac.getWeight() + "," +
                                    (int) ac.getPrice() + "," + ac.getEffect());
                        },
                        item -> true);
                break;
    
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }
    
    private <T> void equipItem(
        Scanner scanner,
        String filePath,
        Function<FileHandler, List<T>> loader,
        Consumer<T> equipAction,
        BiConsumer<PrintWriter, T> writerAction,
        Predicate<T> condition
    ) {
        FileHandler fileHandler = new FileHandler(filePath);
        List<T> items = loader.apply(fileHandler);

        System.out.println("\n================ Available Items ================");
        items.forEach(System.out::println);

        System.out.print("Enter the name of the item you want to equip: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        T chosenItem = items.stream()
                .filter(i -> {
                    try {
                        return i.getClass().getMethod("getName").invoke(i)
                                .toString().equalsIgnoreCase(name);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);

        if (chosenItem != null) {
            if (!condition.test(chosenItem)) {
                System.out.println("Cannot equip this item (condition failed).");
                return;
            }

            equipAction.accept(chosenItem);
            System.out.println(name + " equipped on the knight.");

            items.remove(chosenItem);

            try (PrintWriter writer = new PrintWriter(filePath)) {
                for (T item : items) {
                    writerAction.accept(writer, item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Item with name '" + name + "' not found.");
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
                disarmKnightItem(scanner,
                    knight::getArmors,
                    armor -> knight.equipmentCheck(),
                    "data/armors.txt",
                    (writer, item) -> {
                        Armor a = (Armor) item;
                        writer.println(a.getName() + "," + a.getType() + "," + a.getWeight() + "," +
                                (int) a.getPrice() + "," + a.getProtectionLevel() + "," + a.getMaterial());
                    },
                    "Armor");
            break;
            case 2:
                disarmKnightItem(scanner,
                    knight::getWeapons,
                    weapon -> knight.equipmentCheck(),
                    "data/weapons.txt",
                    (writer, item) -> {
                        Weapon w = (Weapon) item;
                        writer.println(w.getName() + "," + w.getType() + "," + w.getWeight() + "," +
                                (int) w.getPrice() + "," + (int) w.getDamage() + "," + w.getMaterial());
                    },
                    "Weapon");
                break;
            case 3:
                disarmKnightItem(scanner,
                    knight::getAccessories,
                    accessory -> knight.equipmentCheck(),
                    "data/accessories.txt",
                    (writer, item) -> {
                        Accessory a = (Accessory) item;
                        writer.println(a.getName() + "," + a.getType() + "," + a.getWeight() + "," +
                                (int) a.getPrice() + "," + a.getEffect());
                    },
                    "Accessory");
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private <T extends KnightItem> void disarmKnightItem(
            Scanner scanner,
            Supplier<List<T>> knightGetter,
            Consumer<T> disarmAction,
            String filePath,
            BiConsumer<PrintWriter, T> writeToFile,
            String itemName
    ) {
        List<T> items = knightGetter.get();

        System.out.println("\n================ " + itemName + " on the Knight ================");
        items.forEach(System.out::println);

        System.out.print("Enter the name of the " + itemName.toLowerCase() + " you want to disarm: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        T chosenItem = items.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (chosenItem != null) {
            items.remove(chosenItem);
            disarmAction.accept(chosenItem);
            System.out.println(chosenItem.getName() + " has been disarmed from the knight.");

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
                writeToFile.accept(writer, chosenItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(itemName + " with name '" + name + "' not found on the knight.");
        }
    }


    //----------------------Calculate Knight`s items cost------------------------------------------------------------------
    private void calcCostMenu(Scanner scanner) {
        System.out.println("\n==== Calculate the cost Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        double total = 0.0;

        switch (subChoice) {
            case 1:
                total = knight.calculateSum(knight.getArmors(), Armor::getPrice);
                System.out.println("Total cost of knight's armors: " + total);
                break;
            case 2:
                total = knight.calculateSum(knight.getWeapons(), Weapon::getPrice);
                System.out.println("Total cost of knight's weapons: " + total);
                break;
            case 3:
                total = knight.calculateSum(knight.getAccessories(), Accessory::getPrice);
                System.out.println("Total cost of knight's accessories: " + total);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Calculate Knight`s items weight------------------------------------------------------------------
    private void calcWeightMenu(Scanner scanner) {
        System.out.println("\n==== Calculate the weight Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        double total = 0.0;

        switch (subChoice) {
            case 1:
                total = knight.calculateSum(knight.getArmors(), Armor::getWeight);
                System.out.println("Total cost of knight's armors: " + total);
                break;
            case 2:
                total = knight.calculateSum(knight.getWeapons(), Weapon::getWeight);
                System.out.println("Total cost of knight's weapons: " + total);
                break;
            case 3:
                total = knight.calculateSum(knight.getAccessories(), Accessory::getWeight);
                System.out.println("Total cost of knight's accessories: " + total);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Sort Items by Weight------------------------------------------------------------------
    private void sortByWeightMenu(Scanner scanner) {
        System.out.println("\n==== Sort by Weight Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                sortItemsByWeight(scanner, knight::getArmors, FileHandler::loadArmors, "data/armors.txt", "Armor");
                break;
            case 2:
                sortItemsByWeight(scanner, knight::getWeapons, FileHandler::loadWeapons, "data/weapons.txt", "Weapon");
                break;
            case 3:
                sortItemsByWeight(scanner, knight::getAccessories, FileHandler::loadAccessories, "data/accessories.txt", "Accessory");
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private <T extends KnightItem> void sortItemsByWeight(
            Scanner scanner,
            Supplier<List<T>> knightGetter,
            Function<FileHandler, List<T>> loader,
            String filePath,
            String itemName
    ) {
        System.out.println("\n==== " + itemName + " Weight Sorting Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                List<T> items = loader.apply(fileHandler);
                List<KnightItem> sortList = new ArrayList<>(items);
                ItemUtils.sortItemsWeight(sortList);
                sortList.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                List<KnightItem> sortKnightList = new ArrayList<>(knightItems);
                ItemUtils.sortItemsWeight(sortKnightList);
                sortKnightList.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Find items by price range------------------------------------------------------------------
    private void findByPriceRangeMenu(Scanner scanner) {
        System.out.println("\n==== Find by price range Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                findItemsByPriceRange(scanner, knight::getArmors, FileHandler::loadArmors, "data/armors.txt", "Armor");
                break;
            case 2:
                findItemsByPriceRange(scanner, knight::getWeapons, FileHandler::loadWeapons, "data/weapons.txt", "Weapon");
                break;
            case 3:
                findItemsByPriceRange(scanner, knight::getAccessories, FileHandler::loadAccessories, "data/accessories.txt", "Accessory");
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private <T extends KnightItem> void findItemsByPriceRange(
            Scanner scanner,
            Supplier<List<T>> knightGetter,
            Function<FileHandler, List<T>> loader,
            String filePath,
            String itemName
    ) {
        System.out.println("\n==== " + itemName + " Weight Sorting Menu ====");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");
        int subChoice = scanner.nextInt();

        System.out.println("Enter minimum price: ");
        int minChoice = scanner.nextInt();

        System.out.println("Enter maximum price: ");
        int maxChoice = scanner.nextInt();

        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                List<T> items = loader.apply(fileHandler);
                List<KnightItem> sortList = new ArrayList<>(items);
                ItemUtils.filterItemsByPrice(sortList, minChoice, maxChoice);
                sortList.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                List<KnightItem> sortKnightList = new ArrayList<>(knightItems);
                ItemUtils.filterItemsByPrice(sortKnightList, minChoice, maxChoice);
                sortKnightList.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }


    //----------------------Showing affordable items------------------------------------------------------------------
    private void affordableItemsMenu(Scanner scanner) {
        System.out.println("\n==== Show all the affordable items Menu ====");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");
    
        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                List<String> affordableArmors = knight.affordableItems(knight.getArmors(), Armor::getPrice);
                System.out.println("The affordable armors: " + affordableArmors);
                break;
            case 2:
                List<String> affordableWeapons = knight.affordableItems(knight.getWeapons(), Weapon::getPrice);
                System.out.println("The affordable weapons: " + affordableWeapons);
                break;
            case 3:
                List<String> affordableAccessories = knight.affordableItems(knight.getAccessories(), Accessory::getPrice);
                System.out.println("The affordable accessories: " + affordableAccessories);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private void printMenu() {
        System.out.println("\n=========== Knight menu ============");
        System.out.println("1. Show all items");
        System.out.println("2. Define the hierarchy of the armor by type");
        System.out.println("3. Define the hierarchy of the items by material");
        System.out.println("4. Equip the knight");
        System.out.println("5. Disarm the knight");
        System.out.println("6. Calculate the cost of the Knight`s items");
        System.out.println("7. Calculate the weight of the Knight`s items");
        System.out.println("8. Sort the items by weight");
        System.out.println("9. Find items by price range");
        System.out.println("10. Check if the Knight is fully equipped");
        System.out.println("11. Show all the affordable items");
        System.out.println("\n=========== Exit ============");
        System.out.println("0. Exit");
        System.out.print("Choose an action: ");
    }
}
