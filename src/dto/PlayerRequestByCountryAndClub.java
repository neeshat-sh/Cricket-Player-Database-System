package dto;

import java.io.Serializable;
import java.util.List;
import model.Player;

public class PlayerRequestByCountryAndClub implements Serializable {
    private String country;
    private String club;
    List<Player> players = null;

    public PlayerRequestByCountryAndClub(String country, String club) {
        this.country = country;
        this.club = club;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
