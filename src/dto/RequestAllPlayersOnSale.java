package dto;

import java.util.List;

import model.Player;

public class RequestAllPlayersOnSale implements java.io.Serializable {
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
