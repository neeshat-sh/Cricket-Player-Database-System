package database;

import java.util.List;

import model.Club;
import model.Player;

public class DatabaseOperations {
    private Database database;

    public DatabaseOperations(Database database) {
        this.database = database;
    }

    public void addClub(String clubName) {
        database.getClubs().add(new Club(clubName));
    }

    public Club getClub(String clubName) {
        List<Club> clubs = database.getClubs();
        for (Club club : clubs) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club;
            }
        }
        return null;
    }

    public void addPlayer(String name, String country, int age, double height, String clubName, String position,
            int jerseyNumber,
            int weeklySalary) {
        Player player = new Player(name, country, age, height, clubName, position, jerseyNumber, weeklySalary);
        database.getPlayers().add(player);
        Club club = getClub(clubName);
        if (club == null) {
            addClub(clubName);
            club = getClub(clubName);
        }
        club.addPlayer(player);
    }

    public void addPlayer(Player player) {
        database.getPlayers().add(player);
        Club club = getClub(player.getClubName());
        if (club == null) {
            addClub(player.getClubName());
            club = getClub(player.getClubName());
        }
        club.addPlayer(player);
    }

    public void transferClub(Player player, String newClubName) {
        Club oldClub = getClub(player.getClubName());
        Club newClub = getClub(newClubName);
        if (oldClub != null && newClub != null) {
            Player playerToTransfer = oldClub.getPlayer(player.getName());
            if (playerToTransfer != null) {
                oldClub.removePlayer(playerToTransfer);
                newClub.addPlayer(playerToTransfer);
                playerToTransfer.setClubName(newClubName);
            }
        }
        // System.out.println("player transferred to" + player.getClubName());
    }
}
