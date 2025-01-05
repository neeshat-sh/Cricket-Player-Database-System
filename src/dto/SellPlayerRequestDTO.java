package dto;

import model.Player;

public class SellPlayerRequestDTO implements java.io.Serializable {
    private String clubName;
    private String playerName;
    private Player player = null;

    public SellPlayerRequestDTO(String clubName, String playerName) {
        this.clubName = clubName;
        this.playerName = playerName;
    }

    public String getClubName() {
        return clubName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
