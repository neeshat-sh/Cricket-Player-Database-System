package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import network.client.Client;
import network.client.ReadThread;
import controllers.ClubLoginController;
import controllers.ClubHomeController;
import controllers.AdminHomeController;
import controllers.MainLoginController;
import controllers.AdminLoginController;
import dto.ClubMaxSalaryPlayersDTO;
import dto.CountryWisePlayerCountDTO;
import dto.PlayerRequestByCountryAndClub;
import dto.AllPlayerRequestDTO;
import dto.ClubAllPlayerRequestDTO;
import dto.ClubMaxAgePlayersDTO;
import dto.ClubMaxHeightPlayersDTO;
import dto.PlayerRequestByName;
import dto.PlayerRequestBySalaryRange;
import dto.PositionPlayerRequestDTO;
import dto.RequestAllPlayersOnSale;
import dto.RequestUpdatedAdminList;
import dto.RequestUpdatedClubList;
import dto.SellPlayerRequestDTO;
import dto.TotalYearlySalaryDTO;
import dto.BuyPlayerRequestDTO;
import dto.ClientName;

public class Main extends Application {

    private Stage stage;
    private Client client;
    private String clubName;
    private ClubHomeController clubHomeController;
    private AdminHomeController adminHomeController;

    public Stage getStage() {
        return stage;
    }

    public Client getClient() {
        return client;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
        if(client != null) {
            // System.out.println("Setting club name in client");
            client.setClubName(clubName);
        }
    }

    public AdminHomeController getAdminHomeController() {
        return adminHomeController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        new ReadThread(this);
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        client = new Client(serverAddress, serverPort);
    }

    public void showLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();

        MainLoginController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Main Page");
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setResizable(false); 
        stage.show();
    }

    public void showClubLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/clubLogin.fxml"));
        Parent root = loader.load();

        ClubLoginController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Login");
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.setResizable(false); 
        stage.show();
    }

    public void showAdminLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/adminLogin.fxml"));
        Parent root = loader.load();

        AdminLoginController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Admin Login");
        Scene scene = new Scene(root);
        stage.setScene(scene); 
        stage.setResizable(false); 
        stage.show();
    }

    public void showHomePage(List<Player> players) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/clubHome.fxml"));
        Parent root = loader.load();

        clubHomeController = loader.getController();
        clubHomeController.setMain(this);

        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        clubHomeController.initialize(playerList);

        stage.setTitle("Home");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showAdminHomePage(List<Player> players) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/adminHome.fxml"));
        Parent root = loader.load();

        adminHomeController = loader.getController();
        adminHomeController.setMain(this);

        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        adminHomeController.init(playerList);

        stage.setTitle("Admin Home");
        stage.setScene(new Scene(root));
        stage.show();
    }

    synchronized public void setClientName(String name) {
        ClientName clientName = new ClientName(name);
        try {
            client.getSocketWrapper().write(clientName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCountryWisePlayerCount(Map<String, Integer> countryCountMap) {
        adminHomeController.showCountryWisePlayerCount(countryCountMap);
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public void showNoSuchPlayerAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Player");
        alert.setHeaderText("No Such Player");
        alert.setContentText("There is no such player in the database.");
        alert.showAndWait();
    }

    public void updateFilteredPlayerList(List<Player> players) {
        if (clubHomeController != null) {
            clubHomeController.updateTableView(players);
        }
    }

    public void updateAdminFilteredPlayerList(List<Player> players) {
        if (adminHomeController != null) {
            adminHomeController.updateTableView(players);
        }
    }

    public void requestUpdatedClubList() throws IOException {
        RequestUpdatedClubList request = new RequestUpdatedClubList(clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestUpdatedAdminList() throws IOException {
        RequestUpdatedAdminList request = new RequestUpdatedAdminList(null);
        client.getSocketWrapper().write(request);
    }

    public void requestAllPlayers() throws IOException {
        AllPlayerRequestDTO request = new AllPlayerRequestDTO(null, null);
        client.getSocketWrapper().write(request);
    }

    public void requestSellPlayer(Player player) throws IOException {
        SellPlayerRequestDTO request = new SellPlayerRequestDTO(clubName, player.getName());
        request.setPlayer(player);
        client.getSocketWrapper().write(request);
    }

    public void requestBuyPlayer(Player player) throws IOException {
        BuyPlayerRequestDTO request = new BuyPlayerRequestDTO(player, clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestAllClubPlayers() throws IOException {
        ClubAllPlayerRequestDTO request = new ClubAllPlayerRequestDTO(clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestMaxSalaryPlayers() throws IOException {
        ClubMaxSalaryPlayersDTO request = new ClubMaxSalaryPlayersDTO(clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestMaxAgePlayers() throws IOException {
        ClubMaxAgePlayersDTO request = new ClubMaxAgePlayersDTO(clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestMaxHeightPlayers() throws IOException {
        ClubMaxHeightPlayersDTO request = new ClubMaxHeightPlayersDTO(clubName);
        client.getSocketWrapper().write(request);
    }

    public void requestPlayerByName(String name) throws IOException {
        PlayerRequestByName request = new PlayerRequestByName(name);
        client.getSocketWrapper().write(request);
    }

    public void requestPlayerByCountryAndClub(String country, String club) throws IOException {
        PlayerRequestByCountryAndClub request = new PlayerRequestByCountryAndClub(country, club);
        client.getSocketWrapper().write(request);
    }

    public void requestPlayerByPosition(String position) throws IOException {
        PositionPlayerRequestDTO request = new PositionPlayerRequestDTO(position);
        client.getSocketWrapper().write(request);
    }

    public void requestPlayerBySalaryRange(double minSalary, double maxSalary) throws IOException {
        PlayerRequestBySalaryRange request = new PlayerRequestBySalaryRange(minSalary, maxSalary);
        client.getSocketWrapper().write(request);
    }

    public void countryWisePlayerCount() throws IOException {
        CountryWisePlayerCountDTO request = new CountryWisePlayerCountDTO();
        client.getSocketWrapper().write(request);
    }

    public void requestALLPlayersOnSale() throws IOException {
        RequestAllPlayersOnSale request = new RequestAllPlayersOnSale();
        client.getSocketWrapper().write(request);
    }

    public void showPlayersOnSale(List<Player> players) {
        List<Player> filteredPlayers = new ArrayList<>();
        if (players != null) {
            for (Player p : players) {
                if (!p.getClubName().equalsIgnoreCase(clubName))
                    filteredPlayers.add(p);
            }
        }
        if (clubHomeController != null) {
            clubHomeController.showPlayersOnSale(filteredPlayers);
        }
    }

    public void requestTotalSalary() throws IOException {
        TotalYearlySalaryDTO request = new TotalYearlySalaryDTO(clubName);
        client.getSocketWrapper().write(request);
    }

    public void showTotalYearlySalary(long totalSalary) {
        if (clubHomeController != null) {
            clubHomeController.showTotalYearlySalary(totalSalary);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
