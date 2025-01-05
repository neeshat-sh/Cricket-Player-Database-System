package dto;

import java.io.Serializable;

public class ClubMaxAgePlayersDTO implements Serializable {
    private String clubName;

    public ClubMaxAgePlayersDTO(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
