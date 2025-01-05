package dto;

import model.Player;

public class PlayerRequestByName implements java.io.Serializable {
    private String name;
    private Player player;

    public PlayerRequestByName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
