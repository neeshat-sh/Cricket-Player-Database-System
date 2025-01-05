package database;

import model.Club;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Player> players;
    private List<Club> clubs;

    public Database() {
        players = new ArrayList<>();
        clubs = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Club> getClubs() {
        return clubs;
    }
}
