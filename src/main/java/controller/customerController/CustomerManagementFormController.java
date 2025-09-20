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

    // ---------------- Database Connection ----------------
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel_reservation_system", "root", "1234"
        );
    }

    // ---------------- Load Customers ----------------
    private void loadCustomerDetails() {
        ObservableList<CustomerDetails> customers = FXCollections.observableArrayList();

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

            while (rs.next()) {
                customers.add(new CustomerDetails(
                        rs.getString("CustID"),
                        rs.getString("CustName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNo"),
                        rs.getString("CustAddress"),
                        rs.getInt("NumberOfGuests")
                ));
            }

            tblCustomerManagement.setItems(customers);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Add Customer ----------------
    @FXML
    void btnCustomerAddOnAction(ActionEvent event) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO Customer (CustID, CustName, Email, PhoneNo, CustAddress, NumberOfGuests) VALUES (?, ?, ?, ?, ?, ?)"
             )) {

            ps.setString(1, txtCustomerId.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, txtEmail.getText());
            ps.setString(4, txtPhoneNumber.getText());
            ps.setString(5, txtAddress.getText());
            ps.setInt(6, comboNumberOfGuests.getValue());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadCustomerDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Customer WHERE CustID=?")) {

            ps.setString(1, txtCustomerId.getText());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadCustomerDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Update Customer ----------------
    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE Customer SET CustName=?, Email=?, PhoneNo=?, CustAddress=?, NumberOfGuests=? WHERE CustID=?"
             )) {

            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhoneNumber.getText());
            ps.setString(4, txtAddress.getText());
            ps.setInt(5, comboNumberOfGuests.getValue());
            ps.setString(6, txtCustomerId.getText());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                loadCustomerDetails();
                clearFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
