package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.Main;
import model.Detail;
import model.Player;

public class ClubHomeController {

    @FXML
    private Label message;

    @FXML
    private ImageView logo;

    @FXML
    private Button playersOnSaleButton;

    @FXML
    private Button defaultViewButton;

    @FXML
    private TableView<Detail> playerDetailsTable;

    @FXML
    private TableColumn<Detail, String> detailNameColumn;

    @FXML
    private TableColumn<Detail, String> detailValueColumn;

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TextField totalYearlySalaryField;

    @FXML
    private Label salaryLabel;

    @FXML
    private TableView<Player> playersOnSaleTable;

    @FXML
    private TableColumn<Player, String> onSaleNameColumn;

    private Main main;

    public void initialize(ObservableList<Player> players) {
        playersOnSaleButton.setOnAction(event -> getPlayersOnSale());
        defaultViewButton.setOnAction(event -> defaultView(event));
        message.setText(main.getClubName().toUpperCase());

        String imagePath = "/views/images/" + main.getClubName().toLowerCase() + ".png";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.err.println("Image not found: " + imagePath);
        } else {
            logo.setImage(new Image(imageStream));
        }

        playerTable.setStyle("-fx-background-color: #d2d4a7;");

        nameColumn.setText("Club Players");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        nameColumn.setCellFactory(new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {
            @Override
            public TableCell<Player, String> call(final TableColumn<Player, String> param) {
                final TableCell<Player, String> cell = new TableCell<Player, String>() {

                    private final Button detailsButton = new Button("Details");
                    private final Button sellButton = new Button("Sell");
                    private final Button onSaleButton = new Button("On Sale");
                    {
                        detailsButton.setFont(Font.font("Monospaced", 12));
                        detailsButton.setStyle("-fx-text-fill: #d15929;");

                        detailsButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerDetails(player);
                        });

                        sellButton.setFont(Font.font("Monospaced", 12));
                        sellButton.setStyle("-fx-text-fill: #d15929;");

                        sellButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            sellPlayer(player);
                        });

                        onSaleButton.setFont(Font.font("Monospaced", 12));
                        onSaleButton.setStyle("-fx-text-fill: #d15929;");
                        onSaleButton.setDisable(true);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox labelBox = new HBox();
                            labelBox.setAlignment(Pos.CENTER_LEFT);

                            Label label = new Label(item);
                            label.setFont(Font.font("Monospaced", 18));
                            label.setStyle("-fx-text-fill: #d15929; -fx-font-weight: bold;");
                            label.setAlignment(Pos.CENTER);

                            HBox.setHgrow(label, Priority.ALWAYS);
                            labelBox.getChildren().add(label);

                            HBox buttonBox = new HBox(5);
                            buttonBox.setAlignment(Pos.CENTER_RIGHT);

                            Player player = getTableView().getItems().get(getIndex());
                            Button button = player.getIsOnSale() ? onSaleButton : sellButton;

                            buttonBox.getChildren().addAll(detailsButton, button);

                            HBox hbox = new HBox(10);
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            HBox.setHgrow(labelBox, Priority.ALWAYS);
                            hbox.getChildren().addAll(labelBox, buttonBox);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        });
        playerTable.setItems(players);

        detailNameColumn.setCellValueFactory(new PropertyValueFactory<>("detailName"));
        detailValueColumn.setCellValueFactory(new PropertyValueFactory<>("detailValue"));

        onSaleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    private void getPlayersOnSale() {
        try {
            main.requestALLPlayersOnSale();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sellPlayer(Player player) {
        try {
            main.requestSellPlayer(player);
        } catch (Exception e) {
            System.out.println("Sell Request Method Call Failed");
        }
    }

    @FXML
    public void showPlayersOnSale(List<Player> playersOnSale) {
        String currentClubName = main.getClubName();
        playersOnSale = playersOnSale.stream()
                .filter(player -> !player.getClubName().equalsIgnoreCase(currentClubName))
                .collect(Collectors.toList());
        updateSaleTableView(playersOnSale);
    }

    private void showPlayerDetails(Player player) {
        ObservableList<Detail> playerDetails = FXCollections.observableArrayList(
                new Detail("Name", player.getName()),
                new Detail("Country", player.getCountry()),
                new Detail("Age", String.valueOf(player.getAge())),
                new Detail("Height", String.valueOf(player.getHeight()) + " m"),
                new Detail("Club Name", player.getClubName()),
                new Detail("Position", player.getPosition()),
                new Detail("Jersey Number",
                        (player.getJerseyNumber() == -1 ? "Not Assigned" : String.valueOf(player.getJerseyNumber()))),
                new Detail("Weekly Salary", String.valueOf(player.getWeeklySalary())));
        playerDetailsTable.setItems(playerDetails);
    }

    @FXML
    public void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showMaxSalaryPlayers() {
        try {
            main.requestMaxSalaryPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showMaxAgePlayers() {
        try {
            main.requestMaxAgePlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showMaxHeightPlayers() {
        try {
            main.requestMaxHeightPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void defaultView(ActionEvent event) {
        try {
            main.requestUpdatedClubList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableView(List<Player> players) {
        if (players == null) {
            players = new ArrayList<>();
        }

        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        playerTable.setItems(playerList);

        nameColumn.setText("Club Players");

        nameColumn.setCellFactory(new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {
            @Override
            public TableCell<Player, String> call(final TableColumn<Player, String> param) {
                final TableCell<Player, String> cell = new TableCell<Player, String>() {

                    private final Button detailsButton = new Button("Details");
                    private final Button sellButton = new Button("Sell");
                    private final Button onSaleButton = new Button("On Sale");
                    {
                        detailsButton.setFont(Font.font("Monospaced", 12));
                        detailsButton.setStyle("-fx-text-fill: #d15929;");

                        detailsButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerDetails(player);
                        });

                        sellButton.setFont(Font.font("Monospaced", 12));
                        sellButton.setStyle("-fx-text-fill: #d15929;");

                        sellButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            sellPlayer(player);
                        });

                        onSaleButton.setFont(Font.font("Monospaced", 12));
                        onSaleButton.setStyle("-fx-text-fill: #d15929;");
                        onSaleButton.setDisable(true);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox labelBox = new HBox();
                            labelBox.setAlignment(Pos.CENTER_LEFT);

                            Label label = new Label(item);
                            label.setFont(Font.font("Monospaced", 18));
                            label.setStyle("-fx-text-fill: #d15929; -fx-font-weight: bold;");
                            label.setAlignment(Pos.CENTER);

                            HBox.setHgrow(label, Priority.ALWAYS);
                            labelBox.getChildren().add(label);

                            HBox buttonBox = new HBox(5);
                            buttonBox.setAlignment(Pos.CENTER_RIGHT);

                            Player player = getTableView().getItems().get(getIndex());
                            Button button = player.getIsOnSale() ? onSaleButton : sellButton;

                            buttonBox.getChildren().addAll(detailsButton, button);

                            HBox hbox = new HBox(10);
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            HBox.setHgrow(labelBox, Priority.ALWAYS);
                            hbox.getChildren().addAll(labelBox, buttonBox);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void updateSaleTableView(List<Player> playersOnSale) {
        ObservableList<Player> playersOnSaleList = FXCollections.observableArrayList(playersOnSale);
        playersOnSaleTable.setItems(playersOnSaleList);
        playersOnSaleTable.setVisible(true);

        onSaleNameColumn.setText("Players On Sale");

        onSaleNameColumn.setCellFactory(new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {
            @Override
            public TableCell<Player, String> call(final TableColumn<Player, String> param) {
                final TableCell<Player, String> cell = new TableCell<Player, String>() {

                    private final Button detailsButton = new Button("Details");
                    private final Button buyButton = new Button("Buy");
                    {
                        detailsButton.setFont(Font.font("Monospaced", 12));
                        detailsButton.setStyle("-fx-text-fill: #d15929;");

                        detailsButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerDetails(player);
                        });

                        buyButton.setFont(Font.font("Monospaced", 12));
                        buyButton.setStyle("-fx-text-fill: #d15929;");

                        buyButton.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            buyPlayer(player);
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox labelBox = new HBox();
                            labelBox.setAlignment(Pos.CENTER_LEFT);

                            Label label = new Label(item);
                            label.setFont(Font.font("Monospaced", 18));
                            label.setStyle("-fx-text-fill: #d15929; -fx-font-weight: bold;");
                            label.setAlignment(Pos.CENTER);

                            HBox.setHgrow(label, Priority.ALWAYS);
                            labelBox.getChildren().add(label);

                            HBox buttonBox = new HBox(5);
                            buttonBox.setAlignment(Pos.CENTER_RIGHT);
                            buttonBox.getChildren().addAll(detailsButton, buyButton);

                            HBox hbox = new HBox(10);
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            HBox.setHgrow(labelBox, Priority.ALWAYS);
                            hbox.getChildren().addAll(labelBox, buttonBox);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void buyPlayer(Player player) {
        try {
            main.requestBuyPlayer(player);
        } catch (Exception e) {
            System.out.println("Buy Failed");
        }
    }

    @FXML
    public void showTotalSalary() {
        try {
            main.requestTotalSalary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTotalYearlySalary(long totalSalary) {

        totalYearlySalaryField.setText(String.valueOf(totalSalary));
        salaryLabel.setVisible(true);
        totalYearlySalaryField.setVisible(true);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}