package dto;

import java.util.List;

import model.Player;

public class RequestUpdatedAdminList implements java.io.Serializable {
    private List<Player> players;

    public RequestUpdatedAdminList(List<Player> players) {
        this.players = players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
