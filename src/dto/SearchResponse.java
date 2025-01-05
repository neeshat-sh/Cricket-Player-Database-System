package dto;

import java.io.Serializable;
import java.util.List;
import model.Player;

public class SearchResponse implements Serializable {
    Object response;
    public List<Player> players;

    public SearchResponse(Object response, List<Player> players) {
        this.response = response;
        this.players = players;
    }
}
