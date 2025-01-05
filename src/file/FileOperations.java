package file;

import database.Database;
import database.DatabaseOperations;
import model.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class FileOperations {

    public static final String INPUT_FILE_NAME = "players.txt";
    public static final String OUTPUT_FILE_NAME = "players.txt";

    public static void loadFileInput(Database database) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        DatabaseOperations databaseOperations = new DatabaseOperations(database);

        while (true) {
            String line = br.readLine();

            if (line == null)
                break;

            String[] playerData = line.split(",", 8);

            String name = playerData[0].trim();
            String country = playerData[1].trim();
            int age = Integer.parseInt(playerData[2].trim());
            double height = Double.parseDouble(playerData[3].trim());
            String clubName = playerData[4].trim();
            String position = playerData[5].trim();
            int jerseyNumber = (playerData[6].trim().isEmpty() ? -1 : Integer.parseInt(playerData[6].trim()));
            int weeklySalary = Integer.parseInt(playerData[7].trim());

            databaseOperations.addPlayer(name, country, age, height, clubName, position, jerseyNumber, weeklySalary);
            // System.out.println(line);
        }
        br.close();
    }

    public static void saveToFile(Database IPLDatabase) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Player player : IPLDatabase.getPlayers()) {
            bw.write(player.getName() + "," + player.getCountry() + "," + player.getAge() + "," + player.getHeight()
                    + ","
                    + player.getClubName() + "," + player.getPosition() + "," + player.getJerseyNumber() + ","
                    + player.getWeeklySalary());
            bw.write(System.lineSeparator());
        }
        bw.close();
    }
}
