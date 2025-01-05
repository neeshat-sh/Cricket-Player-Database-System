package menu;

import console.Input;
import console.Output;
import database.Database;
import database.DatabaseOperations;
import model.Player;
import search.SearchPlayerOptions;

public class AddPlayerMenu {
    private final Database database;
    private DatabaseOperations databaseOperations;

    public AddPlayerMenu(Database database) {
        this.database = database;
        this.databaseOperations = new DatabaseOperations(database);
    }

    public void display() {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        Boolean validInput;

        Output.println("---Add Player Menu: Enter Player Details---");

        Output.print("Name: ");
        String name = Input.getString();
        while (searchPlayerOptions.searchByName(name, database.getPlayers()) != null) {
            Output.printNewLine();
            Output.println("Player with this name already exists.");
            Output.print("Name: ");
            name = Input.getString();
        }

        Output.print("Country: ");
        String country = Input.getString();

        validInput = false;
        int age = -1;
        while (!validInput) {
            Output.print("Age: ");
            try {
                age = Integer.parseInt(Input.getLine());
                validInput = true;
            } catch (Exception e) {
                Output.printNewLine();
                Output.println("Invalid, please enter a valid input.");
            }
        }

        validInput = false;
        double height = -1;
        while (!validInput) {
            try {
                Output.print("Height: ");
                height = Double.parseDouble(Input.getLine());
                validInput = true;
            } catch (Exception e) {
                Output.printNewLine();
                Output.println("Invalid, please enter a valid input.");
            }
        }

        Output.print("Club: ");
        String club = Input.getString();

        Output.print("Position (Batsman, Bowler, Wicketkeeper, Allrounder): ");
        String position = Input.getString();
        while (!Player.validatePosition(position)) {
            Output.printNewLine();
            Output.println("Invalid, please enter a valid input.");
            Output.print("Position (Batsman, Bowler, Wicketkeeper, Allrounder): ");
            position = Input.getString();
        }

        validInput = false;
        int jerseyNumber = -1;
        while (!validInput) {
            Output.print("Jersey Number: ");
            String jerseyNumberString = Input.getLine();
            if (jerseyNumberString.equals("")) {
                validInput = true;
            } else {
                try {
                    jerseyNumber = Integer.parseInt(jerseyNumberString);
                    validInput = true;
                } catch (Exception e) {
                    Output.printNewLine();
                    Output.println("Invalid, please enter a valid input.");
                }
            }
        }

        validInput = false;
        int weeklySalary = -1;
        while (!validInput) {
            try {
                Output.print("Weekly Salary: ");
                weeklySalary = Integer.parseInt(Input.getLine());
                validInput = true;
            } catch (Exception e) {
                Output.printNewLine();
                Output.println("Invalid, please enter a valid input.");
            }
        }

        Output.printNewLine();

        databaseOperations.addPlayer(name, country, age, height, club, position, jerseyNumber, weeklySalary);
        Output.println("Player Added Successfully!");
    }
}
