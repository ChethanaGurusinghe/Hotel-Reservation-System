package controller.customerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerDetails;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {

    CustomerManagementController customerManagementController = new CustomerManagementController();
    CustomerManagementService customerManagementService = new CustomerManagementController();

    @FXML
    private Button btnCustomerAdd;

    @FXML
    private Button btnCustomerClear;

    @FXML
    private Button btnCustomerDelete;

    @FXML
    private Button btnCustomerUpdate;

    @FXML
    private TableColumn<CustomerDetails, String> colAddress;

    @FXML
    private TableColumn<CustomerDetails, String> colCustomerId;

    @FXML
    private TableColumn<CustomerDetails, String> colEmail;

    @FXML
    private TableColumn<CustomerDetails, String> colName;

    @FXML
    private TableColumn<CustomerDetails, String> colPhoneNumber;

    @FXML
    private TableColumn<CustomerDetails, Integer> colNumberOfGuests;

    @FXML
    private ComboBox<Integer> comboNumberOfGuests;

    @FXML
    private TableView<CustomerDetails> tblCustomerManagement;

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

    // ---------------- Load Customers ----------------
    private void loadCustomerDetails() {

        CustomerManagementController customerManagementController = new CustomerManagementController();
        ObservableList<CustomerDetails> customers = customerManagementController.gettAllCustomerDetails();

        tblCustomerManagement.setItems(customers);
    }

    // ---------------- Add Customer ----------------
    @FXML
    void btnCustomerAddOnAction(ActionEvent event) {

        String customerId = txtCustomerId.getText();
        String customerName = txtName.getText();
        String email = txtEmail.getText();
        String phoneNO = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        Integer numberOfGuests = comboNumberOfGuests.getValue();

        customerManagementService.addCustomerDetails(customerId,customerName,email,phoneNO,address,numberOfGuests);
        loadCustomerDetails();
        clearFields();
    }

    // ---------------- Clear Fields ----------------
    @FXML
    void btnCustomerClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtName.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        txtAddress.clear();
        comboNumberOfGuests.getSelectionModel().clearSelection();
    }

    // ---------------- Delete Customer ----------------
    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {

        String customerId = txtCustomerId.getText();

        customerManagementService.deleteCustomerDetails(customerId);
        loadCustomerDetails();
        clearFields();
    }

    // ---------------- Update Customer ----------------
    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {

        String customerName = txtName.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNumber.getText();
        String address = txtAddress.getText();
        Integer numberOfGuests = comboNumberOfGuests.getValue();
        String customerId = txtCustomerId.getText();

        customerManagementService.updateCustomerDetails(customerName,email,phoneNo,address,numberOfGuests,customerId);
        loadCustomerDetails();
        clearFields();

    }

    // ---------------- Row Click Event ----------------
    private void setRowClickEvent() {
        tblCustomerManagement.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                CustomerDetails selected = tblCustomerManagement.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    txtCustomerId.setText(selected.getCustomerId());
                    txtName.setText(selected.getCustomerName());
                    txtEmail.setText(selected.getEmail());
                    txtPhoneNumber.setText(selected.getPhoneNumber());
                    txtAddress.setText(selected.getAddress());
                    comboNumberOfGuests.setValue(selected.getNumberOfGuests());
                }
            }
        });
    }

    // ---------------- Initialize ----------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNumberOfGuests.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));

        comboNumberOfGuests.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        loadCustomerDetails();
        setRowClickEvent();
    }
}
