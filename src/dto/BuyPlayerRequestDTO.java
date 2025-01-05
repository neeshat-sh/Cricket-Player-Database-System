package dto;

import model.Player;

public class BuyPlayerRequestDTO implements java.io.Serializable {
    private Player player;
    private String clubName;

    public BuyPlayerRequestDTO(Player player, String clubName) {
        this.player = player;
        this.clubName = clubName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
