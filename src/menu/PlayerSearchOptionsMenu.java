package menu;

import database.Database;
import model.Player;
import search.SearchPlayerOptions;
import console.Input;
import console.Output;

import java.util.List;
import java.util.Map;

public class PlayerSearchOptionsMenu {
    private final Database database;

    public PlayerSearchOptionsMenu(Database database) {
        this.database = database;
    }

    public void display() {
        SearchPlayerOptions options = new SearchPlayerOptions();
        Boolean searchingPlayers = true;
        while (searchingPlayers) {
            Output.printNewLine();

            Output.println("Player Searching Options: ");
            Output.println("(1) By Player Name");
            Output.println("(2) By Club and Country");
            Output.println("(3) By Position");
            Output.println("(4) By Salary Range");
            Output.println("(5) Country Wise Count");
            Output.println("(6) Back to Main Menu");

            Output.print("Enter Choice: ");

            try {
                int choice = Integer.parseInt(Input.getLine());

                Output.printNewLine();

                switch (choice) {
                    case 1: {
                        // Search By Player Name
                        Output.println("---Search by Player Name---");
                        Output.print("Enter Player Name: ");
                        String playerName = Input.getString();

                        Output.printNewLine();

                        Player player = options.searchByName(playerName, database.getPlayers());

                        if (player != null) {
                            Output.println("---Player Found---");
                            Output.printPlayer(player);
                        } else {
                            Output.println("No Player Found with Name: " + playerName);
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 2: {
                        // Search By Club and Country
                        Output.println("---Search by Country and Club---");
                        Output.print("Enter Country Name: ");
                        String countryName = Input.getString();
                        Output.print("Enter Club Name (or 'Any'): ");
                        String clubName = Input.getString();

                        Output.printNewLine();

                        List<Player> players = options.searchByCountryAndClub(countryName, clubName,
                                database.getPlayers(), database.getClubs());
                        if (!players.isEmpty()) {
                            Output.println("---Players with provided Country and Club---");
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such player with this country and club.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 3: {
                        // Search By Position
                        Output.println("---Search By Position---");
                        Output.print("Enter Position: ");
                        String position = Input.getString();

                        Output.printNewLine();

                        List<Player> players = options.searchByPosition(position, database.getPlayers());
                        if (!players.isEmpty()) {
                            Output.println("---Players with given Position---");
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such player with this position.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 4: {
                        // Search By Weekly Salary
                        Boolean validInput;

                        Output.println("---Search Salary By Range (Provide Two Numbers)---");
                        
                        double lowerbound = 0;

                        validInput = false;
                        while(!validInput) {
                            Output.print("Enter Lower Bound: ");
                            try {
                                lowerbound = Double.parseDouble(Input.getLine());
                                validInput = true;
                            } catch (Exception e) {
                                Output.printNewLine();
                                Output.println("Invalid, please enter a valid input.");
                            }
                        }

                        double upperbound = 0;

                        validInput = false;
                        while(!validInput) {
                            Output.print("Enter Upper Bound: ");
                            try {
                                upperbound = Double.parseDouble(Input.getLine());
                                validInput = true;
                            } catch (Exception e) {
                                Output.printNewLine();
                                Output.println("Invalid, please enter a valid input.");
                            }
                        }

                        Output.printNewLine();

                        List<Player> players = options.searchBySalaryRange(lowerbound, upperbound,
                                database.getPlayers());
                        if (!players.isEmpty()) {
                            Output.println("---Players with given salary range---");
                            for (Player player : players) {
                                Output.printPlayer(player);
                            }
                        } else {
                            Output.println("No such player with this weekly salary range.");
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 5: {
                        Map<String, Integer> countryPlayerCount = options.countryWiseCount(database.getPlayers());
                        Output.println("---Country Player Count---");
                        for (String country : countryPlayerCount.keySet()) {
                            Output.println(country + ": " + countryPlayerCount.get(country));
                        }
                        Output.println("--------------------------------");
                        break;
                    }
                    case 6: {
                        // Back to Main Menu
                        Output.println("---Main Menu---");

                        searchingPlayers = false;
                        break;
                    }
                    default: {
                        Output.println("Invalid Input. Please enter a valid number.");
                        break;
                    }
                }
            } catch (Exception e) {
                Output.printNewLine();
                Output.printError("Invalid Input. Please enter a valid number.");
            }
        }
    }
}
