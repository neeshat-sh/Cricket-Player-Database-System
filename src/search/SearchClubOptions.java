package search;

import model.Club;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class SearchClubOptions {
    public List<Player> maximumSalary(String clubName, List<Club> clubs) {
        List<Player> maxSalaryPlayers = new ArrayList<>();
        for(Club club: clubs) {
            if(club.getName().equalsIgnoreCase(clubName))
            {
                List<Player> players = club.getPlayers();
                int maxSalary = 0;
                for(Player player: players) {
                    if(player.getWeeklySalary() > maxSalary) {
                        maxSalary = player.getWeeklySalary();
                        maxSalaryPlayers.clear();
                        maxSalaryPlayers.add(player);
                    } else if(player.getWeeklySalary() == maxSalary) {
                        maxSalaryPlayers.add(player);
                    }
                }
                break;
            }
        }
        return maxSalaryPlayers;
    }
    public List<Player> maximumAge(String clubName, List<Club> clubs) {
        List<Player> maxAgePlayers = new ArrayList<>();
        for(Club club: clubs) {
            if(club.getName().equalsIgnoreCase(clubName))
            {
                List<Player> players = club.getPlayers();
                int maxAge = 0;
                for(Player player: players) {
                    if(player.getAge() > maxAge) {
                        maxAge = player.getAge();
                        maxAgePlayers.clear();
                        maxAgePlayers.add(player);
                    } else if(player.getAge() == maxAge) {
                        maxAgePlayers.add(player);
                    }
                }
                break;
            }
        }
        return maxAgePlayers;
    }
    public List<Player> maximumHeight(String clubName, List<Club> clubs) {
        List<Player> maxHeightPlayers = new ArrayList<>();
        for(Club club: clubs) {
            if(club.getName().equalsIgnoreCase(clubName))
            {
                List<Player> players = club.getPlayers();
                double maxHeight = 0;
                for(Player player: players) {
                    if(player.getHeight() > maxHeight) {
                        maxHeight = player.getHeight();
                        maxHeightPlayers.clear();
                        maxHeightPlayers.add(player);
                    } else if(player.getHeight() == maxHeight) {
                        maxHeightPlayers.add(player);
                    }
                }
                break;
            }
        }
        return maxHeightPlayers;
    }
    public long totalYearlySalary(String clubName, List<Club> clubs) {
        long totalYearlySalary = 0, totalWeeklySalary = 0;
        for(Club club: clubs) {
            if(club.getName().equalsIgnoreCase(clubName))
            {
                List<Player> players = club.getPlayers();
                for(Player player: players) {
                    totalWeeklySalary += player.getWeeklySalary();
                }
                break;
            }
        }
        totalYearlySalary = totalWeeklySalary * 52; 
        return totalYearlySalary;
    }
}
