package network.server;

import network.SocketWrapper;
import dto.LoginDTO;
import dto.PlayerRequestByCountryAndClub;
import dto.PlayerRequestByName;
import dto.PlayerRequestBySalaryRange;
import dto.PositionPlayerRequestDTO;
import dto.RequestAllPlayersOnSale;
import dto.RequestUpdatedAdminList;
import dto.RequestUpdatedClubList;
import dto.AddPlayerRequestDTO;
import dto.AdminLoginDTO;
import dto.ClubAllPlayerRequestDTO;
import dto.AllPlayerRequestDTO;
import dto.BuyPlayerRequestDTO;
import dto.ClientName;
import dto.ClubMaxSalaryPlayersDTO;
import dto.CountryWisePlayerCountDTO;
import dto.ClubMaxAgePlayersDTO;
import dto.ClubMaxHeightPlayersDTO;
import dto.SearchResponse;
import dto.SellPlayerRequestDTO;
import dto.TotalYearlySalaryDTO;
import file.FileOperations;
import model.Player;
import service.AdminService;
import service.ClubService;
import network.client.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final Server server;
    private final SocketWrapper socketWrapper;
    private final Client client;
    private final ClubService clubService;
    private final AdminService adminService;
    private HashMap<String, String> userMap;

    public ReadThreadServer(HashMap<String, String> map, SocketWrapper socketWrapper, ClubService clubService,
            AdminService adminService, Server server) {
        this.userMap = map;
        this.socketWrapper = socketWrapper;
        this.client = new Client(socketWrapper);
        this.clubService = clubService;
        this.adminService = adminService;
        this.thr = new Thread(this);
        this.server = server;
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName().trim().toLowerCase());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        socketWrapper.write(loginDTO);
                    } else if (o instanceof ClientName) {
                        ClientName clientName = (ClientName) o;
                        server.setClientClubName(socketWrapper, clientName.getName());
                    } else if (o instanceof AdminLoginDTO) {
                        AdminLoginDTO adminLoginDTO = (AdminLoginDTO) o;
                        String password = "123";
                        adminLoginDTO.setStatus(adminLoginDTO.getPassword().equals(password));
                        socketWrapper.write(adminLoginDTO);
                    } else if (o instanceof AllPlayerRequestDTO) {
                        AllPlayerRequestDTO request = (AllPlayerRequestDTO) o;
                        List<Player> players = adminService.getPlayers();
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof ClubAllPlayerRequestDTO) {
                        ClubAllPlayerRequestDTO request = (ClubAllPlayerRequestDTO) o;
                        List<Player> players = clubService.getPlayersByClubName(request.getClubName());
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof ClubMaxSalaryPlayersDTO) {
                        ClubMaxSalaryPlayersDTO request = (ClubMaxSalaryPlayersDTO) o;
                        List<Player> players = clubService.getClubMaxSalaryPlayers(request.getClubName());
                        SearchResponse response = new SearchResponse(request, players);
                        socketWrapper.write(response);
                    } else if (o instanceof ClubMaxAgePlayersDTO) {
                        ClubMaxAgePlayersDTO request = (ClubMaxAgePlayersDTO) o;
                        List<Player> players = clubService.getClubMaxAgePlayers(request.getClubName());
                        SearchResponse response = new SearchResponse(request, players);
                        socketWrapper.write(response);
                    } else if (o instanceof ClubMaxHeightPlayersDTO) {
                        ClubMaxHeightPlayersDTO request = (ClubMaxHeightPlayersDTO) o;
                        List<Player> players = clubService.getClubMaxHeightPlayers(request.getClubName());
                        SearchResponse response = new SearchResponse(request, players);
                        socketWrapper.write(response);
                    } else if (o instanceof PlayerRequestByName) {
                        PlayerRequestByName request = (PlayerRequestByName) o;
                        Player player = adminService.getPlayerByName(request.getName());
                        request.setPlayer(player);
                        socketWrapper.write(request);
                    } else if (o instanceof PlayerRequestByCountryAndClub) {
                        PlayerRequestByCountryAndClub request = (PlayerRequestByCountryAndClub) o;
                        List<Player> players = adminService.getPlayersByCountryAndClub(request.getCountry(),
                                request.getClub());
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof PositionPlayerRequestDTO) {
                        PositionPlayerRequestDTO request = (PositionPlayerRequestDTO) o;
                        List<Player> players = adminService.getPlayersByPosition(request.getPosition());
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof PlayerRequestBySalaryRange) {
                        PlayerRequestBySalaryRange request = (PlayerRequestBySalaryRange) o;
                        List<Player> players = adminService.getPlayersBySalaryRange(request.getMinSalary(),
                                request.getMaxSalary());
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof AddPlayerRequestDTO) {
                        AddPlayerRequestDTO request = (AddPlayerRequestDTO) o;
                        Player newPlayer = request.getPlayer();
                        adminService.addPlayer(newPlayer);
                        socketWrapper.write(request);
                    } else if (o instanceof CountryWisePlayerCountDTO) {
                        CountryWisePlayerCountDTO request = (CountryWisePlayerCountDTO) o;
                        Map<String, Integer> countryCountMap = adminService.countryWisePlayerCount();
                        request.setCountryCountMap(countryCountMap);
                        socketWrapper.write(request);
                    } else if (o instanceof RequestUpdatedAdminList) {
                        RequestUpdatedAdminList request = (RequestUpdatedAdminList) o;
                        List<Player> players = adminService.getPlayers();
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof RequestUpdatedClubList) {
                        RequestUpdatedClubList request = (RequestUpdatedClubList) o;
                        List<Player> players = clubService.getPlayersByClubName(request.getClubName());
                        request.setPlayers(players);
                        socketWrapper.write(request);
                    } else if (o instanceof SellPlayerRequestDTO) {
                        SellPlayerRequestDTO request = (SellPlayerRequestDTO) o;
                        clubService.sellPlayer(request.getPlayer());
                        server.broadcastPlayersOnSale();
                        socketWrapper.write(request);
                    } else if (o instanceof RequestAllPlayersOnSale) {
                        RequestAllPlayersOnSale request = (RequestAllPlayersOnSale) o;
                        request.setPlayers(clubService.getPlayersOnSale());
                        socketWrapper.write(request);
                    } else if (o instanceof BuyPlayerRequestDTO) {
                        BuyPlayerRequestDTO request = (BuyPlayerRequestDTO) o;
                        clubService.buyPlayer(request.getPlayer(), request.getClubName());
                        server.playerBought();
                    } else if (o instanceof TotalYearlySalaryDTO) {
                        TotalYearlySalaryDTO request = (TotalYearlySalaryDTO) o;
                        long totalSalary = clubService.getClubTotalSalary(request.getClubName());
                        request.setTotalYearlySalary(totalSalary);
                        socketWrapper.write(request);
                    } else {
                        System.out.println("Unknown object received");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                FileOperations.saveToFile(adminService.getDatabase());
                server.removeClient(client);
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}