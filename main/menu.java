package main;
import java.util.Scanner;

public class menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

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
                    System.out.println("exiting the program...");
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
    private static void determineHierarchy() {
        System.out.println("Armor hierarchy has been determined.");
    }
    
    private static void equipKnight() {
        System.out.println("The knight has been equipped.");
    }
    
    private static void calculateCost() {
        System.out.println("The cost of the armor has been calculated.");
    }
    
    private static void sortByWeight() {
        System.out.println("Armor has been sorted by weight.");
    }
    
    private static void findByPriceRange() {
        System.out.println("Armor pieces within the specified price range have been found.");
    }    

    public static void printMenu(){
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


