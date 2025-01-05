package dto;

import java.io.Serializable;

public class ClubMaxHeightPlayersDTO implements Serializable {
    private String clubName;

    public ClubMaxHeightPlayersDTO(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

}