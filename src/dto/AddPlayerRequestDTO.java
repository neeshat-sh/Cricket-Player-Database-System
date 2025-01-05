package dto;

import model.Player;

public class AddPlayerRequestDTO implements java.io.Serializable {
    private Player player;

    public AddPlayerRequestDTO(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    void setPlayer(Player player) {
        this.player = player;
    }
}
