package network.client;

import javafx.application.Platform;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dto.LoginDTO;
import dto.PlayerRequestByCountryAndClub;
import dto.PlayerRequestByName;
import dto.PlayerRequestBySalaryRange;
import dto.PositionPlayerRequestDTO;
import dto.RequestAllPlayersOnSale;
import dto.RequestUpdatedAdminList;
import dto.RequestUpdatedClubList;
import dto.AdminLoginDTO;
import dto.AllPlayerRequestDTO;
import dto.BuyPlayerRequestDTO;
import dto.ClubAllPlayerRequestDTO;
import dto.CountryWisePlayerCountDTO;
import dto.SearchResponse;
import dto.SellPlayerRequestDTO;
import dto.TotalYearlySalaryDTO;
import main.Main;
import model.Player;

public class ReadThread implements Runnable {
    private final Thread thr;
    private final Main main;

    public ReadThread(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getClient().getSocketWrapper().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        // System.out.println(loginDTO.getUserName());
                        // System.out.println(loginDTO.isStatus());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        ClubAllPlayerRequestDTO requestDTO = new ClubAllPlayerRequestDTO(
                                                loginDTO.getUserName());
                                        main.getClient().getSocketWrapper().write(requestDTO);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert();
                                }
                            }
                        });
                    } else if (o instanceof AdminLoginDTO) {
                        AdminLoginDTO adminLoginDTO = (AdminLoginDTO) o;
                        // System.out.println(adminLoginDTO.getUserName());
                        // System.out.println(adminLoginDTO.isStatus());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (adminLoginDTO.isStatus()) {
                                    try {
                                        AllPlayerRequestDTO requestDTO = new AllPlayerRequestDTO(adminLoginDTO, null);
                                        main.getClient().getSocketWrapper().write(requestDTO);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert();
                                }
                            }
                        });
                    } else if (o instanceof SearchResponse) {
                        SearchResponse response = (SearchResponse) o;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateFilteredPlayerList(response.players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof ClubAllPlayerRequestDTO) {
                        ClubAllPlayerRequestDTO request = (ClubAllPlayerRequestDTO) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showHomePage(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof AllPlayerRequestDTO) {
                        AllPlayerRequestDTO request = (AllPlayerRequestDTO) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showAdminHomePage(players);
                                    // main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof RequestUpdatedAdminList) {
                        RequestUpdatedAdminList request = (RequestUpdatedAdminList) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof RequestUpdatedClubList) {
                        RequestUpdatedClubList request = (RequestUpdatedClubList) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof RequestAllPlayersOnSale) {
                        RequestAllPlayersOnSale request = (RequestAllPlayersOnSale) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showPlayersOnSale(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof PlayerRequestByName) {
                        PlayerRequestByName request = (PlayerRequestByName) o;
                        Player player = request.getPlayer();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if(player == null) {
                                        main.showNoSuchPlayerAlert();
                                        return;
                                    }
                                    List<Player> players = List.of(player);
                                    main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof PlayerRequestByCountryAndClub) {
                        PlayerRequestByCountryAndClub request = (PlayerRequestByCountryAndClub) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof PositionPlayerRequestDTO) {
                        PositionPlayerRequestDTO request = (PositionPlayerRequestDTO) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof PlayerRequestBySalaryRange) {
                        PlayerRequestBySalaryRange request = (PlayerRequestBySalaryRange) o;
                        List<Player> players = request.getPlayers();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.updateAdminFilteredPlayerList(players);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof BuyPlayerRequestDTO) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // main.showBuyPlayerConfirmation(request.getPlayer(), request.getClubName());
                                    // main.requestUpdatedClubList();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof SellPlayerRequestDTO) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.requestUpdatedClubList();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (o instanceof TotalYearlySalaryDTO) {
                        TotalYearlySalaryDTO request = (TotalYearlySalaryDTO) o;
                        long totalSalary = request.getTotalYearlySalary();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showTotalYearlySalary(totalSalary);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else if (o instanceof CountryWisePlayerCountDTO) {
                        CountryWisePlayerCountDTO request = (CountryWisePlayerCountDTO) o;
                        Map<String, Integer> countryWisePlayerCount = request.getCountryCountMap();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showCountryWisePlayerCount(countryWisePlayerCount);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getClient().getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
