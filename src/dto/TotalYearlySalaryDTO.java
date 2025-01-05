package dto;

public class TotalYearlySalaryDTO implements java.io.Serializable {
    private String clubName;
    private long totalYearlySalary;

    public TotalYearlySalaryDTO(String clubName) {
        this.clubName = clubName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public long getTotalYearlySalary() {
        return totalYearlySalary;
    }

    public void setTotalYearlySalary(long totalYearlySalary) {
        this.totalYearlySalary = totalYearlySalary;
    }

}
