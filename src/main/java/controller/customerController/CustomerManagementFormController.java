package controller.customerController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerManagementFormController {

    @FXML
    private Button btnCustomerAdd;

    @FXML
    private Button btnCustomerClear;

    @FXML
    private Button btnCustomerDelete;

    @FXML
    private Button btnCustomerUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private ComboBox<?> comboNumberOfGuests;

    @FXML
    private TableView<?> tblCustomerManagement;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    void btnCustomerAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {

    }

}
