package dto;

import java.io.Serializable;
import java.util.List;

import model.Player;

public class ClubAllPlayerRequestDTO implements Serializable {
    private String clubName;
    private List<Player> players = null;

    public ClubAllPlayerRequestDTO(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}