package service;

import database.Database;
import database.DatabaseOperations;
import model.Club;
import model.Player;
import search.SearchClubOptions;

import java.util.List;

public class ClubService {
    private Database database;
    private List<Player> PlayersOnSale;

    public ClubService(Database database, List<Player> PlayersOnSale) {
        this.database = database;
        this.PlayersOnSale = PlayersOnSale;
    }

    public List<Player> getPlayersOnSale() {
        // List<Player> players = new ArrayList<>();
        // for (Player p : PlayersOnSale) {
        //     if (!p.getClubName().equalsIgnoreCase(clubName))
        //         players.add(p);
        // }
        return PlayersOnSale;
    }

    public Club findClubByName(String clubName) {
        for (Club club : database.getClubs()) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club;
            }
        }
        return null;
    }

    public List<Player> getPlayersByClubName(String clubName) {
        Club club = findClubByName(clubName);
        if (club != null) {
            return club.getPlayers();
        }
        return null;
    }

    public List<Player> getClubMaxSalaryPlayers(String clubName) {
        SearchClubOptions searchClubOptions = new SearchClubOptions();
        return searchClubOptions.maximumSalary(clubName, database.getClubs());
    }

    public List<Player> getClubMaxAgePlayers(String clubName) {
        SearchClubOptions searchClubOptions = new SearchClubOptions();
        return searchClubOptions.maximumAge(clubName, database.getClubs());
    }

    public List<Player> getClubMaxHeightPlayers(String clubName) {
        SearchClubOptions searchClubOptions = new SearchClubOptions();
        return searchClubOptions.maximumHeight(clubName, database.getClubs());
    }

    public long getClubTotalSalary(String clubName) {
        SearchClubOptions searchClubOptions = new SearchClubOptions();
        return searchClubOptions.totalYearlySalary(clubName, database.getClubs());
    }

    public void sellPlayer(Player player) {
        PlayersOnSale.add(player);
        for(Player p : database.getPlayers()) {
            if(p.equals(player)) {
                p.setIsOnSale(true);
            }
        }
        // for (Player p : PlayersOnSale) {
        //     System.out.println(p);
        // }
    }

    public void buyPlayer(Player player, String clubName) {
        PlayersOnSale.remove(player);
        for(Player p : database.getPlayers()) {
            if(p.equals(player)) {
                p.setIsOnSale(false);
            }
        }
        // for (Player p : PlayersOnSale) {
        //     System.out.println(p);
        // }
        DatabaseOperations databaseOperations = new DatabaseOperations(database);
        databaseOperations.transferClub(player, clubName);
    }
}
