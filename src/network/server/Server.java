package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.SocketWrapper;
import network.client.Client;
import database.Database;
import dto.RequestAllPlayersOnSale;
import dto.RequestUpdatedClubList;
import file.FileOperations;
import model.Player;
import service.AdminService;
import service.ClubService;

public class Server {

    private ServerSocket serverSocket;
    public Database database = new Database();
    public HashMap<String, String> userMap;
    private ClubService clubService;
    private AdminService adminService;
    private List<Player> PlayersOnSale = new ArrayList<>();

    private final List<Client> clients = new CopyOnWriteArrayList<>();

    public void addClient(Client client) {
        synchronized (clients) {
            clients.add(client);
        }
    }

    public void removeClient(Client client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    public List<Client> getClientSockets() {
        return clients;
    }

    synchronized public void broadcastPlayersOnSale() {
        for (Client client : clients) {
            try {
                SocketWrapper clientSocket = client.getSocketWrapper();
                RequestAllPlayersOnSale requestAllPlayersOnSale = new RequestAllPlayersOnSale();
                requestAllPlayersOnSale.setPlayers(PlayersOnSale);
                clientSocket.write(requestAllPlayersOnSale);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public void broadcastAllUpdatedClubPlayers() {
        for (Client client : clients) {
            SocketWrapper clientSocket = client.getSocketWrapper();
            RequestUpdatedClubList requestUpdatedClubList = new RequestUpdatedClubList(client.getClubName());
            requestUpdatedClubList.setPlayers(clubService.getPlayersByClubName(client.getClubName()));
            try {
                clientSocket.write(requestUpdatedClubList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playerBought() {
        broadcastPlayersOnSale();
        broadcastAllUpdatedClubPlayers();
    }

    Server() {
        userMap = new HashMap<>();
        userMap.put("kolkata knight riders".toLowerCase(), "a");
        userMap.put("delhi capitals".toLowerCase(), "b");
        userMap.put("mumbai indians".toLowerCase(), "c");
        userMap.put("chennai super kings".toLowerCase(), "d");
        userMap.put("royal challengers bangalore".toLowerCase(), "e");
        userMap.put("sunrisers hyderabad".toLowerCase(), "f");
        userMap.put("rajasthan royals".toLowerCase(), "g");
        userMap.put("punjab kings".toLowerCase(), "h");
        userMap.put("gujarat titans".toLowerCase(), "i");
        userMap.put("lucknow super giants".toLowerCase(), "j");
        userMap.put("admin".toLowerCase(), "123");

        clubService = new ClubService(database, PlayersOnSale);
        adminService = new AdminService(database);

        try {
            FileOperations.loadFileInput(database);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void setClientClubName(SocketWrapper socketWrapper, String clubName) {
        for (Client client : clients) {
            if (client.getSocketWrapper() == socketWrapper) {
                client.setClubName(clubName);
                break;
            }
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        Client client = new Client(socketWrapper);
        addClient(client);
        new ReadThreadServer(userMap, socketWrapper, clubService, adminService, this);
    }

    public static void main(String[] args) {
        new Server();
    }
}