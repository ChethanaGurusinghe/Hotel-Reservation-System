package controller.roomController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RoomDetails;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RoomManagementFormController implements Initializable {

    @FXML
    private Button btnRoomAdd, btnRoomClear, btnRoomDelete, btnRoomUpdate;

    @FXML
    private TableColumn<RoomDetails, Boolean> colAvailability;

    @FXML
    private TableColumn<RoomDetails, String> colDescription, colRoomId, colType;

    @FXML
    private TableColumn<RoomDetails, Integer> colFloor, colMaxGuests;

    @FXML
    private TableColumn<RoomDetails, Double> colPricePerNight;

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private TableView<RoomDetails> tblRomManagement;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtFloor, txtMaxGuests, txtPricePerNight, txtRoomId;

    // ---------------- Database Connection ----------------
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel_reservation_system", "root", "1234"
        );
    }

    // ---------------- Load Rooms ----------------
    private void loadRoomDetails() {
        ObservableList<RoomDetails> rooms = FXCollections.observableArrayList();

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {

            while (rs.next()) {
                rooms.add(new RoomDetails(
                        rs.getString("room_id"),
                        rs.getString("type"),
                        rs.getDouble("price_per_night"),
                        rs.getInt("max_guests"),
                        rs.getBoolean("availability"),
                        rs.getString("description"),
                        rs.getInt("floor")
                ));
            }

            tblRomManagement.setItems(rooms);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Add Room ----------------
    @FXML
    void btnRoomAddOnAction(ActionEvent event) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO rooms (room_id, type, price_per_night, max_guests, availability, description, floor) VALUES (?, ?, ?, ?, ?, ?, ?)"
             )) {

            ps.setString(1, txtRoomId.getText());
            ps.setString(2, comboType.getValue());
            ps.setDouble(3, Double.parseDouble(txtPricePerNight.getText()));
            ps.setInt(4, Integer.parseInt(txtMaxGuests.getText()));
            ps.setBoolean(5, true); // default availability = true
            ps.setString(6, txtDescription.getText());
            ps.setInt(7, Integer.parseInt(txtFloor.getText()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadRoomDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Clear Fields ----------------
    @FXML
    void btnRoomClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtRoomId.clear();
        txtPricePerNight.clear();
        txtMaxGuests.clear();
        txtDescription.clear();
        txtFloor.clear();
        comboType.getSelectionModel().clearSelection();
    }

    // ---------------- Delete Room ----------------
    @FXML
    void btnRoomDeleteOnAction(ActionEvent event) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM rooms WHERE room_id=?")) {

            ps.setString(1, txtRoomId.getText());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadRoomDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Update Room ----------------
    @FXML
    void btnRoomUpdateOnAction(ActionEvent event) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE rooms SET type=?, price_per_night=?, max_guests=?, availability=?, description=?, floor=? WHERE room_id=?"
             )) {

            ps.setString(1, comboType.getValue());
            ps.setDouble(2, Double.parseDouble(txtPricePerNight.getText()));
            ps.setInt(3, Integer.parseInt(txtMaxGuests.getText()));
            ps.setBoolean(4, true); // can later make dynamic
            ps.setString(5, txtDescription.getText());
            ps.setInt(6, Integer.parseInt(txtFloor.getText()));
            ps.setString(7, txtRoomId.getText());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadRoomDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Row Click Event ----------------
    private void setRowClickEvent() {
        tblRomManagement.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                RoomDetails selected = tblRomManagement.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    txtRoomId.setText(selected.getRoomId());
                    comboType.setValue(selected.getType());
                    txtPricePerNight.setText(String.valueOf(selected.getPricePerNight()));
                    txtMaxGuests.setText(String.valueOf(selected.getMaxGuests()));
                    txtDescription.setText(selected.getDescription());
                    txtFloor.setText(String.valueOf(selected.getFloor()));
                }
            }
        });
    }

    // ---------------- Initialize ----------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        colMaxGuests.setCellValueFactory(new PropertyValueFactory<>("maxGuests"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));

        comboType.setItems(FXCollections.observableArrayList("Single", "Double", "Suite", "Deluxe"));

        loadRoomDetails();
        setRowClickEvent();
    }
}
