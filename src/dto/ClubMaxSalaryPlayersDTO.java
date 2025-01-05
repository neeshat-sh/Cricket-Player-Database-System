package dto;

import java.io.Serializable;

public class ClubMaxSalaryPlayersDTO implements Serializable {
    private String clubName;

    public ClubMaxSalaryPlayersDTO(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
