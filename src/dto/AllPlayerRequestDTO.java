package dto;

import java.io.Serializable;
import java.util.List;
import model.Player;

public class AllPlayerRequestDTO implements Serializable {
    Object response;
    private List<Player> players;

    public AllPlayerRequestDTO(Object response, List<Player> players) {
        this.response = response;
        this.players = players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
