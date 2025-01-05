package model;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String clubName;
    private String position;
    private int jerseyNumber;
    private int weeklySalary;
    boolean isOnSale = false;

    public Player(String name, String country, int age, double height, String clubName, String position,
            int jerseyNumber,
            int weeklySalary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.clubName = clubName;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.weeklySalary = weeklySalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getWeeklySalary() {
        return weeklySalary;
    }

    public void setWeeklySalary(int weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    @Override
    public String toString() {
        return name + ":  " + country + ", " + age + " years, " + height + " meters, " + clubName
                + ", " + position + ", Jersey " + (jerseyNumber == -1 ? "N/A" : jerseyNumber) + ", Weekly Salary = "
                + weeklySalary;
    }

    public static Boolean validatePosition(String position) {
        String[] Positions = { "Batsman", "Bowler", "Wicketkeeper", "Allrounder" };
        for (var string : Positions) {
            if (string.equalsIgnoreCase(position))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player player = (Player) obj;
        return Objects.equals(name, player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setIsOnSale(boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public boolean getIsOnSale() {
        return isOnSale;
    }
}
