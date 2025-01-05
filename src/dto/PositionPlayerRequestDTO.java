package dto;

import java.util.List;

import model.Player;

public class PositionPlayerRequestDTO implements java.io.Serializable {
    private String position;
    List<Player> players = null;

    public PositionPlayerRequestDTO(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
