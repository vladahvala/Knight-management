package ui;

import model.Knight;

public class Main {
    public static void main(String[] args) {
        Knight knight = new Knight("Sir Lancelot", 10, 100, 100, 4, 0, 0, false, false);

        Menu menu = new Menu(knight);
        menu.start(); // start menu
    }
}
