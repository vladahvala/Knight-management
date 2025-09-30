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
        System.out.println("9. Sort the items by price");
        System.out.println("10. Find items by price range");
        System.out.println("11. Check if the Knight is fully equipped");
        System.out.println("\n=========== Exit ============");
        System.out.println("0. Exit");
        System.out.print("Choose an action: ");
    }

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
                    sortByPriceMenu(scanner);
                    break;
                case 10:
                    findByPriceRangeMenu(scanner);
                    break;
                case 11:
                    if (knight.getIsFullyEquipped())
                        System.out.println("Is fully equipped");
                    else {
                        System.out.println("Is not fully equipped\n");
                        List<String> neededEquip = knight.neededEquipment();
                        neededEquip.forEach(System.out::println);
                    }
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

    // printing the titles of the menus
    private void printHeaderLine(String itemName) {
        int totalLength = 94;
        String title = " " + itemName + " ";
        int remaining = totalLength - title.length();
        int left = remaining / 2;
        int right = remaining - left;

        String line = "=".repeat(left) + title + "=".repeat(right);
        System.out.println("\n" + line);
    }

    // ----------------------Show All
    // Items------------------------------------------------------------------
    private void showAllItemsMenu(Scanner scanner) {
        System.out.println("\n======= Show all items Menu =======");
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

    private <T extends KnightItem> void showAllItems(
            Scanner scanner,
            String filePath,
            Function<FileHandler, List<T>> loader,
            Supplier<List<T>> knightGetter,
            String itemName) {
        System.out.println("\n======= " + itemName + " Menu =======");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                List<T> items = loader.apply(fileHandler);
                printHeaderLine(itemName + " Inventory");
                if (!items.isEmpty()) {
                    System.out.println(items.get(0).getHeader());
                }
                items.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                printHeaderLine(itemName + " Knight");
                if (!knightItems.isEmpty()) {
                    System.out.println(knightItems.get(0).getHeader());
                }
                knightItems.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    // ----------------------Armor Hierarchy by
    // type------------------------------------------------------------------
    private void startArmorHierarchyTypeMenu(Scanner scanner) {
        FileHandler fileHandler = new FileHandler("data/armors.txt");
        List<Armor> armors = fileHandler.loadArmors();
        printHeaderLine("Armor Hierarchy by Type (INVENTORY)");
        if (!armors.isEmpty()) {
            System.out.println(armors.get(0).getHeader());
        }
        List<Armor> inventArmorHier = ItemUtils.getArmorHierarchy(armors);
        inventArmorHier.forEach(System.out::println);
    }

    // ----------------------Armor Hierarchy by
    // material------------------------------------------------------------------
    private <T extends KnightItem> void itemHierarchyByMaterial(
            Scanner scanner,
            String filePath,
            Function<FileHandler, List<T>> loader,
            String itemName) {
        FileHandler fileHandler = new FileHandler(filePath);
        List<T> items = loader.apply(fileHandler);
        List<KnightItem> itemList = new ArrayList<>(items);
        printHeaderLine(itemName + " Hierarchy by Material (INVENTORY)");
        if (!items.isEmpty()) {
            System.out.println(items.get(0).getHeader());
        }
        List<KnightItem> sortedItems = ItemUtils.getMaterialHierarchy(itemList);
        sortedItems.forEach(System.out::println);
    }

    private void itemHierarchyByMaterialMenu(Scanner scanner) {
        System.out.println("\n======= Item Hierarchy by Material Menu =======");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                itemHierarchyByMaterial(scanner, "data/armors.txt", FileHandler::loadArmors, "Armor");
                break;
            case 2:
                itemHierarchyByMaterial(scanner, "data/weapons.txt", FileHandler::loadWeapons, "Weapon");
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    // ----------------------Equip
    // Knight------------------------------------------------------------------
    private void equipKnightMenu(Scanner scanner) {
        System.out.println("\n======= Equip the Knight Menu =======");
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
                        },
                        "Armors");
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
                        item -> knight.countWeapons() < knight.getMaxWeapons(),
                        "Weapons");
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
                        item -> true,
                        "Accessories");
                break;

            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private <T extends KnightItem> void equipItem(
            Scanner scanner,
            String filePath,
            Function<FileHandler, List<T>> loader,
            Consumer<T> equipAction,
            BiConsumer<PrintWriter, T> writerAction,
            Predicate<T> condition,
            String itemName) {
        FileHandler fileHandler = new FileHandler(filePath);
        List<T> items = loader.apply(fileHandler);

        printHeaderLine(itemName + " Available");
        if (!items.isEmpty()) {
            System.out.println(items.get(0).getHeader());
        }
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

    // ----------------------Disarm
    // Knight------------------------------------------------------------------
    private void disarmKnightMenu(Scanner scanner) {
        System.out.println("\n======= Disarm the Knight Menu =======");
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
            String itemName) {
        List<T> items = knightGetter.get();

        printHeaderLine(itemName + " on the Knight");
        if (!items.isEmpty()) {
            System.out.println(items.get(0).getHeader());
        }
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

    // ----------------------Calculate Knight`s items
    // cost------------------------------------------------------------------
    private void calcCostMenu(Scanner scanner) {
        System.out.println("\n======= Calculate the cost Menu =======");
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

    // ----------------------Calculate Knight`s items
    // weight------------------------------------------------------------------
    private void calcWeightMenu(Scanner scanner) {
        System.out.println("\n======= Calculate the weight Menu =======");
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

    // ----------------------Sort Items by
    // Weight------------------------------------------------------------------
    private void sortByWeightMenu(Scanner scanner) {
        System.out.println("\n======= Sort by Weight Menu =======");
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
                sortItemsByWeight(scanner, knight::getAccessories, FileHandler::loadAccessories, "data/accessories.txt",
                        "Accessory");
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
            String itemName) {
        System.out.println("\n======= " + itemName + " Weight Sorting Menu =======");
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
                printHeaderLine(itemName + " sorted by Weight");
                if (!items.isEmpty()) {
                    System.out.println(items.get(0).getHeader());
                }
                sortList.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                List<KnightItem> sortKnightList = new ArrayList<>(knightItems);
                ItemUtils.sortItemsWeight(sortKnightList);
                printHeaderLine(itemName + " sorted by Weight");
                if (!sortKnightList.isEmpty()) {
                    System.out.println(sortKnightList.get(0).getHeader());
                }
                sortKnightList.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    // ----------------------Sort Items by
    // Weight------------------------------------------------------------------
    private void sortByPriceMenu(Scanner scanner) {
        System.out.println("\n======= Sort by Price Menu =======");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Accessory");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                sortItemsByPrice(scanner, knight::getArmors, FileHandler::loadArmors, "data/armors.txt", "Armor");
                break;
            case 2:
                sortItemsByPrice(scanner, knight::getWeapons, FileHandler::loadWeapons, "data/weapons.txt", "Weapon");
                break;
            case 3:
                sortItemsByPrice(scanner, knight::getAccessories, FileHandler::loadAccessories, "data/accessories.txt",
                        "Accessory");
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    private <T extends KnightItem> void sortItemsByPrice(
            Scanner scanner,
            Supplier<List<T>> knightGetter,
            Function<FileHandler, List<T>> loader,
            String filePath,
            String itemName) {
        System.out.println("\n======= " + itemName + " Price Sorting Menu =======");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");

        int subChoice = scanner.nextInt();
        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                List<T> items = loader.apply(fileHandler);
                List<KnightItem> sortList = new ArrayList<>(items);
                ItemUtils.sortItemsPrice(sortList);
                printHeaderLine(itemName + " sorted by Weight");
                if (!items.isEmpty()) {
                    System.out.println(items.get(0).getHeader());
                }
                sortList.forEach(System.out::println);
                break;
            case 2:
                List<T> knightItems = knightGetter.get();
                List<KnightItem> sortKnightList = new ArrayList<>(knightItems);
                ItemUtils.sortItemsPrice(sortKnightList);
                printHeaderLine(itemName + " sorted by Weight");
                if (!sortKnightList.isEmpty()) {
                    System.out.println(sortKnightList.get(0).getHeader());
                }
                sortKnightList.forEach(System.out::println);
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
        }
    }

    // ----------------------Find items by price
    // range------------------------------------------------------------------
    private void findByPriceRangeMenu(Scanner scanner) {
        System.out.println("\n======= Find by price range Menu =======");
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
                findItemsByPriceRange(scanner, knight::getWeapons, FileHandler::loadWeapons, "data/weapons.txt",
                        "Weapon");
                break;
            case 3:
                findItemsByPriceRange(scanner, knight::getAccessories, FileHandler::loadAccessories,
                        "data/accessories.txt", "Accessory");
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
            String itemName) {
        System.out.println("\n======= " + itemName + " Price Range Menu =======");
        System.out.println("1. Inventory");
        System.out.println("2. Knight");
        System.out.print("Choose an option: ");
        int subChoice = scanner.nextInt();

        System.out.print("Enter minimum price: ");
        double minChoice = scanner.nextDouble();

        System.out.print("Enter maximum price: ");
        double maxChoice = scanner.nextDouble();

        List<KnightItem> itemsToFilter;

        switch (subChoice) {
            case 1:
                FileHandler fileHandler = new FileHandler(filePath);
                itemsToFilter = new ArrayList<>(loader.apply(fileHandler));
                break;
            case 2:
                itemsToFilter = new ArrayList<>(knightGetter.get());
                break;
            default:
                System.out.println("Wrong choice. Returning to main menu...");
                return;
        }

        List<KnightItem> filteredItems = ItemUtils.filterItemsByPrice(itemsToFilter, minChoice, maxChoice);

        if (filteredItems.isEmpty()) {
            System.out.println("No items found in this price range.");
            return;
        }

        printHeaderLine(itemName + " fitting in the price range");
        System.out.println(filteredItems.get(0).getHeader());
        filteredItems.forEach(System.out::println);
    }
}
