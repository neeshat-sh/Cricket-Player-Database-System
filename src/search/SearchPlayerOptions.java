package search;

import model.Club;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SearchPlayerOptions {

    public Player searchByName(String name, List<Player> players) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> searchByCountryAndClub(String countryName, String clubName, List<Player> players, List<Club> clubs) {
        List<Player> playersFound = new ArrayList<Player>();
        if (clubName.equalsIgnoreCase("any")) {
            for (Player p : players) {
                if (p.getCountry().equalsIgnoreCase(countryName)) {
                    playersFound.add(p);
                }
            }
        } else {
            for (Club club : clubs) {
                if (club.getName().equalsIgnoreCase(clubName)) {
                    List<Player> clubPlayers = club.getPlayers();
                    for (Player p : clubPlayers) {
                        if (p.getCountry().equalsIgnoreCase(countryName)) {
                            playersFound.add(p);
                        }
                    }
                    break;
                }
            }
        }
        return playersFound;
    }

    public List<Player> searchByPosition(String position, List<Player> players) {
        List<Player> playersFound = new ArrayList<Player>();
        for (Player p : players) {
            if (p.getPosition().equalsIgnoreCase(position)) {
                playersFound.add(p);
            }
        }
        return playersFound;
    }

    public List<Player> searchBySalaryRange(double leftBound, double rightBound, List<Player> players) {
        List<Player> playersFound = new ArrayList<Player>();
        for (Player p : players) {
            if (p.getWeeklySalary() >= leftBound && p.getWeeklySalary() <= rightBound) {
                playersFound.add(p);
            }
        }
        return playersFound;
    }

    public Map<String, Integer> countryWiseCount(List<Player> players) {
        Map<String, Integer> countryCountMap = new HashMap<String, Integer>();

        for (Player p : players) {
            String country = p.getCountry();
            if (countryCountMap.containsKey(country.trim().toLowerCase())) {
                countryCountMap.put(country.trim().toLowerCase(), countryCountMap.get(country.trim().toLowerCase()) + 1);
            } else {
                countryCountMap.put(country.trim().toLowerCase(), 1);
            }
        }
        return countryCountMap;
    }
}

