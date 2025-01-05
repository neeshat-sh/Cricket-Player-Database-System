package service;

import database.Database;
import database.DatabaseOperations;
import model.Player;
import search.SearchPlayerOptions;
import java.util.List;
import java.util.Map;

public class AdminService {
    private final Database database;

    public AdminService(Database database) {
        this.database = database;
    }

    public List<Player> getPlayers() {
        return database.getPlayers();
    }

    public List<Player> getPlayersByCountryAndClub(String country, String club) {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        return searchPlayerOptions.searchByCountryAndClub(country, club, database.getPlayers(), database.getClubs());
    }

    public Player getPlayerByName(String name) {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        return searchPlayerOptions.searchByName(name, database.getPlayers());
    }

    public List<Player> getPlayersByPosition(String position) {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        return searchPlayerOptions.searchByPosition(position, database.getPlayers());
    }

    public List<Player> getPlayersBySalaryRange(double leftBound, double rightBound) {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        return searchPlayerOptions.searchBySalaryRange(leftBound, rightBound, database.getPlayers());
    }

    public Map<String, Integer> countryWisePlayerCount() {
        SearchPlayerOptions searchPlayerOptions = new SearchPlayerOptions();
        return searchPlayerOptions.countryWiseCount(database.getPlayers());
    }

    public void addPlayer(Player player) {
        DatabaseOperations databaseOperations = new DatabaseOperations(database);
        databaseOperations.addPlayer(player);
    }

    public Database getDatabase() {
        return database;
    }
}
