package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private String name;
    private List<Player> players;

    public Club(String name) {
        this.name = name;
        players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public String toString() {
        return "Club: " + name;
    }

    public void removePlayer(Player player) {
        players.removeIf(p -> p.equals(player));
    }
    public Player getPlayer(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }
}
