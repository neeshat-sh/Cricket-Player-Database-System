package menu;

import database.Database;
import model.Player;
import console.Input;
import console.Output;

import search.SearchClubOptions;

import java.util.List;

public class ClubSearchOptionsMenu {
    private final Database database;

    public ClubSearchOptionsMenu(Database database) {
        this.database = database;
    }

    public void display() {
        SearchClubOptions options = new SearchClubOptions();
        Boolean searchingClubs = true;
        while (searchingClubs) {
            Output.printNewLine();
            Output.println("Club Searching Options: ");
            Output.println("(1) Player(s) with the maximum salary of a club");
            Output.println("(2) Player(s) with the maximum age of a club");
            Output.println("(3) Player(s) with the maximum height of a club");
            Output.println("(4) Total yearly salary of a club");
            Output.println("(5) Back to Main Menu");

            Output.print("Enter Choice: ");

            try {
                int choice = Integer.parseInt(Input.getLine());

                Output.printNewLine();

                switch (choice) {
                    case 1: {
                        // Players with the maximum salary
                        Output.println("---Players with Maximum Salary---");
                        Output.print("Enter Club Name: ");
                        String clubName = Input.getString();

                        Output.printNewLine();

                        List<Player> players = options.maximumSalary(clubName, database.getClubs());
                        if (!players.isEmpty()) {
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such club with this name.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 2: {
                        // Players with the maximum age
                        Output.println("---Players with Maximum Age---");
                        Output.print("Enter Club Name: ");
                        String clubName = Input.getString();

                        Output.printNewLine();

                        List<Player> players = options.maximumAge(clubName, database.getClubs());
                        if (!players.isEmpty()) {
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such club with this name.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 3: {
                        // Players with the maximum height
                        Output.println("---Players with Maximum Height---");
                        Output.print("Enter Club Name: ");
                        String clubName = Input.getString();

                        Output.printNewLine();

                        List<Player> players = options.maximumHeight(clubName, database.getClubs());
                        if (!players.isEmpty()) {
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such club with this name.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 4: {
                        // Total Yearly Salary
                        Output.println("---Total Yearly Salary---");
                        Output.print("Enter Club Name: ");
                        String clubName = Input.getString();

                        Output.printNewLine();

                        long totalYearlySalary = options.totalYearlySalary(clubName, database.getClubs());
                        if (totalYearlySalary != 0) {
                            Output.println("Total Yearly Salary for " + clubName + ": " + totalYearlySalary);
                        } else {
                            Output.println("No such club with this name.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 5: {
                        // Back to Main Menu
                        Output.println("---Main Menu---");

                        searchingClubs = false;
                        break;
                    }
                    default: {
                        Output.println("Invalid input. Please enter a valid choice.");
                        break;
                    }
                }
            } catch (Exception e) {
                Output.printNewLine();
                Output.printError("Invalid input. Please enter a valid choice.");
            }
        }
    }
}
