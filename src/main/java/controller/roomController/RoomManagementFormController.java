package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RoomManagementFormController {

    @FXML
    private Button btnRoomAdd;

    @FXML
    private Button btnRoomClear;

    @FXML
    private Button btnRoomDelete;

    @FXML
    private Button btnRoomUpdate;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colFloor;

    @FXML
    private TableColumn<?, ?> colMaxGuests;

    @FXML
    private TableColumn<?, ?> colPricePerNight;

    @FXML
    private TableColumn<?, ?> colRoomId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private ComboBox<?> comboType;

    @FXML
    private TableView<?> tblRomManagement;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtFloor;

    @FXML
    private TextField txtMaxGuests;

    @FXML
    private TextField txtPricePerNight;

    @FXML
    private TextField txtRoomId;

    @FXML
    void btnRoomAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnRoomClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnRoomDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnRoomUpdateOnAction(ActionEvent event) {

    }

}
