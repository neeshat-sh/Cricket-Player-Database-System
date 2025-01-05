package dto;

import java.util.List;
import model.Player;

public class PlayerRequestBySalaryRange implements java.io.Serializable {
    private double minSalary;
    private double maxSalary;
    List<Player> players = null;

    public PlayerRequestBySalaryRange(double minSalary, double maxSalary) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
