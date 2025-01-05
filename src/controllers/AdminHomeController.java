package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dto.AddPlayerRequestDTO;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.Main;
import model.Detail;
import model.Player;

public class AdminHomeController {
    @FXML
    private Label message;

    @FXML
    private Button button;

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField clubField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField minSalaryField;

    @FXML
    private TextField maxSalaryField;

    @FXML
    private Button searchButton;

    @FXML
    private Button addPlayerButton;

    @FXML
    private TableView<Detail> playerDetailsTable;

    @FXML
    private TableColumn<Detail, String> detailNameColumn;

    @FXML
    private TableColumn<Detail, String> detailValueColumn;

    @FXML
    private TableView<Map.Entry<String, Integer>> countryTable;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> countryColumn;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> countColumn;

    @FXML
    private TextField playerNameField;

    @FXML
    private TextField playerCountryField;

    @FXML
    private TextField playerClubField;

    @FXML
    private TextField playerPositionField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField jerseyField;

    @FXML
    private Button save;

    private Main main;

    public void init(ObservableList<Player> players) {
        message.setText("ADMIN VIEW");

        playerTable.setStyle("-fx-background-color: #d2d4a7;");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        nameColumn.setCellFactory(new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {
            @Override
            public TableCell<Player, String> call(final TableColumn<Player, String> param) {
                final TableCell<Player, String> cell = new TableCell<Player, String>() {

                    private final Button btn = new Button("Details");
                    {
                        btn.setFont(Font.font("Monospaced", 12));
                        btn.setStyle("-fx-text-fill: #d15929;");
                        btn.setMinWidth(Region.USE_PREF_SIZE);
                        btn.setMaxWidth(Region.USE_PREF_SIZE);

                        btn.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerDetails(player);
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

                            labelBox.setSpacing(0);
                            labelBox.setPadding(new Insets(0));

                            Label label = new Label(item);
                            label.setFont(Font.font("Monospaced", 18));
                            label.setStyle("-fx-text-fill: #d15929; -fx-font-weight: bold;");
                            label.setAlignment(Pos.CENTER);

                            HBox.setHgrow(label, Priority.ALWAYS);
                            labelBox.getChildren().add(label);

                            HBox buttonBox = new HBox(0);
                            buttonBox.setAlignment(Pos.CENTER_RIGHT);
                            buttonBox.setSpacing(0);
                            buttonBox.setPadding(new Insets(0));

                            buttonBox.getChildren().add(btn);

                            HBox hbox = new HBox(0);
                            hbox.setAlignment(Pos.CENTER_LEFT);
                            hbox.setSpacing(0);
                            hbox.setPadding(new Insets(0));
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

        hideAllAddPlayerFields();

        save.setOnAction(event -> handleAddPlayer());
    }

    public void initializeCountryCountTable() {
        countryColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey()));
        countColumn.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        countryColumn.setCellFactory(
                new Callback<TableColumn<Map.Entry<String, Integer>, String>, TableCell<Map.Entry<String, Integer>, String>>() {
                    @Override
                    public TableCell<Map.Entry<String, Integer>, String> call(
                            TableColumn<Map.Entry<String, Integer>, String> param) {
                        return new TableCell<Map.Entry<String, Integer>, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    setStyle(
                                            "-fx-font-family: 'Monospaced'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #d15929;");
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });

        countColumn.setCellFactory(
                new Callback<TableColumn<Map.Entry<String, Integer>, Integer>, TableCell<Map.Entry<String, Integer>, Integer>>() {
                    @Override
                    public TableCell<Map.Entry<String, Integer>, Integer> call(
                            TableColumn<Map.Entry<String, Integer>, Integer> param) {
                        return new TableCell<Map.Entry<String, Integer>, Integer>() {
                            @Override
                            protected void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item.toString());
                                    setStyle(
                                            "-fx-font-family: 'Monospaced'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #d15929;");
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });
    }

    public void showCountryWisePlayerCount(Map<String, Integer> countryWisePlayerCount) {
        initializeCountryCountTable();
        setCountryCountMap(countryWisePlayerCount);
    }

    public void setCountryCountMap(Map<String, Integer> countryWisePlayerCount) {
        countryTable.getItems().setAll(countryWisePlayerCount.entrySet());
        countryTable.setVisible(true);
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
    public void defaultView(ActionEvent event) {
        try {
            main.requestUpdatedAdminList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTableView(List<Player> players) {
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        playerTable.setItems(playerList);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private void hideAllFields() {
        nameField.setVisible(false);
        countryField.setVisible(false);
        clubField.setVisible(false);
        positionField.setVisible(false);
        minSalaryField.setVisible(false);
        maxSalaryField.setVisible(false);
        searchButton.setVisible(false);
    }

    private void hideAllAddPlayerFields() {
        playerNameField.setVisible(false);
        playerCountryField.setVisible(false);
        playerClubField.setVisible(false);
        playerPositionField.setVisible(false);
        ageField.setVisible(false);
        salaryField.setVisible(false);
        jerseyField.setVisible(false);
        heightField.setVisible(false);
        save.setVisible(false);
    }

    private void searchByName(String name) {
        // System.out.println("Searching by name: " + name);
        try {
            main.requestPlayerByName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchByCountryAndClub(String country, String club) {
        // System.out.println("Searching by country: " + country + " and club: " +
        // club);
        try {
            main.requestPlayerByCountryAndClub(country, club);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchByPosition(String position) {
        // System.out.println("Searching by position: " + position);
        try {
            main.requestPlayerByPosition(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchBySalaryRange(double minSalary, double maxSalary) {
        // System.out.println("Searching by salary range: " + minSalary + " - " +
        // maxSalary);
        try {
            main.requestPlayerBySalaryRange(minSalary, maxSalary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showByNameFields() {
        hideAllFields();
        hideAllAddPlayerFields();
        nameField.setVisible(true);
        searchButton.setVisible(true);
    }

    @FXML
    public void showByCountryAndClubFields() {
        hideAllFields();
        hideAllAddPlayerFields();
        countryField.setVisible(true);
        clubField.setVisible(true);
        searchButton.setVisible(true);
    }

    @FXML
    public void showByPositionFields() {
        hideAllFields();
        positionField.setVisible(true);
        searchButton.setVisible(true);
    }

    @FXML
    public void showBySalaryRangeFields() {
        hideAllFields();
        hideAllAddPlayerFields();
        minSalaryField.setVisible(true);
        maxSalaryField.setVisible(true);
        searchButton.setVisible(true);
    }

    @FXML
    public void countryWisePlayerCount() {
        hideAllFields();
        hideAllAddPlayerFields();
        try {
            main.countryWisePlayerCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch() {
        if (nameField.isVisible()) {
            String name = nameField.getText();
            searchByName(name);
            nameField.clear();
        } else if (countryField.isVisible() && clubField.isVisible()) {
            String country = countryField.getText();
            String club = clubField.getText();
            searchByCountryAndClub(country, club);
            countryField.clear();
            clubField.clear();
        } else if (positionField.isVisible()) {
            String position = positionField.getText();
            searchByPosition(position);
            positionField.clear();
        } else if (minSalaryField.isVisible() && maxSalaryField.isVisible()) {
            double minSalary = Double.parseDouble(minSalaryField.getText());
            double maxSalary = Double.parseDouble(maxSalaryField.getText());
            searchBySalaryRange(minSalary, maxSalary);
            minSalaryField.clear();
            maxSalaryField.clear();
        }
        hideAllFields();
    }

    @FXML
    public void showAddPlayerFields() {
        hideAllFields();

        countryTable.setVisible(false);
        playerNameField.setVisible(true);
        playerCountryField.setVisible(true);
        playerClubField.setVisible(true);
        playerPositionField.setVisible(true);
        ageField.setVisible(true);
        salaryField.setVisible(true);
        jerseyField.setVisible(true);
        heightField.setVisible(true);
        save.setVisible(true);
    }

    public void addPlayerToTable(Player player) {
        playerTable.getItems().add(player);
    }

    public void handleAddPlayer() {
        String name = playerNameField.getText();
        String country = playerCountryField.getText();
        int age = Integer.parseInt(ageField.getText());
        double height = Double.parseDouble(heightField.getText());
        String club = playerClubField.getText();
        String position = playerPositionField.getText();
        int jerseyNumber = jerseyField.getText().equals("") ? -1 : Integer.parseInt(jerseyField.getText());
        int salary = Integer.parseInt(salaryField.getText());

        Player newPlayer = new Player(name, country, age, height, club, position, jerseyNumber, salary);
        try {
            AddPlayerRequestDTO addPlayerRequestDTO = new AddPlayerRequestDTO(newPlayer);
            main.getClient().getSocketWrapper().write(addPlayerRequestDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerNameField.clear();
        playerCountryField.clear();
        ageField.clear();
        heightField.clear();
        playerClubField.clear();
        playerPositionField.clear();
        jerseyField.clear();
        salaryField.clear();

        playerTable.getItems().add(newPlayer);

        hideAllAddPlayerFields();
    }
}
