package menu;

import database.Database;
import console.Input;
import console.Output;

public class Menu {
    private final Database database;

    public Menu(Database database) {
        this.database = database;
    }

    public void runMenu() {
        Boolean programRunning = true;

        // Main Menu Loop
        while (programRunning) {

            Output.printNewLine();
            Output.println("--------------------------------");

            Output.println("Main Menu:");
            Output.println("(1) Search Players");
            Output.println("(2) Search Clubs");
            Output.println("(3) Add Player");
            Output.println("(4) Exit System");

            Output.print("Enter Choice: ");

            try {
                int choice = Integer.parseInt(Input.getLine());

                Output.printNewLine();

                switch (choice) {
                    case 1: {
                        // Search Players
                        Output.println("--------------------------------");
                        PlayerSearchOptionsMenu playerSearchOptionsMenu = new PlayerSearchOptionsMenu(database);
                        playerSearchOptionsMenu.display();
                        break;
                    }
                    case 2: {
                        // Search Clubs
                        Output.println("--------------------------------");
                        ClubSearchOptionsMenu clubSearchOptionsMenu = new ClubSearchOptionsMenu(database);
                        clubSearchOptionsMenu.display();
                        break;
                    }
                    case 3: {
                        // Add Player
                        Output.println("--------------------------------");
                        AddPlayerMenu addPlayerMenu = new AddPlayerMenu(database);
                        addPlayerMenu.display();
                        break;
                    }
                    case 4: {
                        programRunning = false;
                        Output.println("Exiting the System...");
                        Output.println("--------------------------------");
                        break;
                    }
                    default:
                        Output.println("Invalid Input. Please enter a valid choice.");
                }
            } catch (Exception e) {
                Output.printNewLine();
                Output.printError("Invalid Input. Please enter a valid choice.");
            }
        }
    }
}
