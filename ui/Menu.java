package ui;
import java.util.Scanner;

public class Menu {
    private boolean exit = false;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        printMenu();
        while (!exit) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    determineHierarchy();
                    break;
                case 2:
                    equipKnight();
                    break;
                case 3:
                    calculateCost();
                    break;
                case 4:
                    sortByWeight();
                    break;
                case 5:
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

    // stub methods
    private void determineHierarchy() { System.out.println("Armor hierarchy determined."); }
    private void equipKnight() { System.out.println("Knight equipped."); }
    private void calculateCost() { System.out.println("Cost calculated."); }
    private void sortByWeight() { System.out.println("Sorted by weight."); }
    private void findByPriceRange() { System.out.println("Found by price range."); }

    private void printMenu() {
        System.out.println("\n=========== Knight menu ============");
        System.out.println("1. Define the hierarchy of the armor");
        System.out.println("2. Equip the knight");
        System.out.println("3. Calculate the cost of the armor");
        System.out.println("4. Sort the armor by weight");
        System.out.println("5. Find armor pieces by price range");
        System.out.println("0. Exit");
        System.out.print("Choose an action: ");
    }
}
