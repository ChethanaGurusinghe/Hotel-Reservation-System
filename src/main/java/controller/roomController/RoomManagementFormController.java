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

    RoomManagementService roomManagementService = new RoomManagementController();
    RoomManagementController roomManagementController = new RoomManagementController();

    public RadioButton radioAvailable;
    public RadioButton radioUnavailable;
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

    // ---------------- Load Rooms ----------------
    private void loadRoomDetails() {
        RoomManagementController roomManagementController1 = new RoomManagementController();
        ObservableList<RoomDetails> rooms = roomManagementController.getAllRoomDetails();

        tblRomManagement.setItems(rooms);
    }

    // ---------------- Add Room ----------------
    @FXML
    void btnRoomAddOnAction(ActionEvent event) {

        String roomId = txtRoomId.getText();
        String type = colType.getText();
        Double pricePerNight = Double.valueOf(txtPricePerNight.getText());
        Integer maxGuests = Integer.valueOf(txtMaxGuests.getText());
        Boolean availability =radioAvailable.isSelected();
        String description = txtDescription.getText();
        Integer floor = Integer.valueOf(txtFloor.getText());

        roomManagementService.addRoomDetails(roomId,type,pricePerNight,maxGuests,availability,description,floor);
        loadRoomDetails();
        clearFields();
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

        String roomId = txtRoomId.getText();

        roomManagementService.deleteRoomDetails(roomId);
        loadRoomDetails();
        clearFields();
    }

    // ---------------- Update Room ----------------
    @FXML
    void btnRoomUpdateOnAction(ActionEvent event) {

        String roomId = txtRoomId.getText();
        String type = comboType.getValue();
        Double pricePerNight = Double.valueOf(txtPricePerNight.getText());
        Integer maxGuests = Integer.valueOf(txtMaxGuests.getText());
        Boolean availability = radioAvailable.isSelected();
        String description = txtDescription.getText();
        Integer floor = Integer.valueOf(txtFloor.getText());

        roomManagementService.updateRoomDetails(type,pricePerNight,maxGuests,availability,description,floor,roomId);
        loadRoomDetails();
        clearFields();
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

                    if (Boolean.TRUE.equals(selected.getAvailability())) {
                        radioAvailable.setSelected(true);
                    } else {
                        radioUnavailable.setSelected(true);
                    }

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

        ToggleGroup availabilityGroup = new ToggleGroup();
        radioAvailable.setToggleGroup(availabilityGroup);
        radioUnavailable.setToggleGroup(availabilityGroup);

        radioAvailable.setSelected(true);

        loadRoomDetails();
        setRowClickEvent();
    }



}
