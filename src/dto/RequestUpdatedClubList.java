package dto;

import java.util.List;

import model.Player;

public class RequestUpdatedClubList implements java.io.Serializable {
    private String clubName;
    private List<Player> players;

    public RequestUpdatedClubList(String clubName) {
        this.clubName = clubName;
    }

    public RequestUpdatedClubList(List<Player> players) {
        this.players = players;
    }

    public String getClubName() {
        return clubName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
