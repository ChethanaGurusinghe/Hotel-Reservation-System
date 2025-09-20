package controller.customerController;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomerDetails;

import java.sql.*;

public class CustomerManagementController implements CustomerManagementService {

        @Override
        public void addCustomerDetails(String customerId,String customerName,String email,String phoneNO,String address,Integer numberOfGuests){
            try (Connection connection = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = connection.prepareStatement(
                         "INSERT INTO Customer (CustID, CustName, Email, PhoneNo, CustAddress, NumberOfGuests) VALUES (?, ?, ?, ?, ?, ?)"
                 )) {

                ps.setString(1,customerId);
                ps.setString(2,customerName);
                ps.setString(3,email);
                ps.setString(4,phoneNO);
                ps.setString(5,address);
                ps.setInt(6,numberOfGuests);

                int rows = ps.executeUpdate();
                if (rows > 0) {

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deleteCustomerDetails(String customerId){
            try (Connection connection = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = connection.prepareStatement("DELETE FROM Customer WHERE CustID=?")) {

                ps.setString(1,customerId);

                int rows = ps.executeUpdate();
                if (rows > 0) {

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void updateCustomerDetails(String customerName,String email,String phoneNO,String address,Integer numberOfGuests,String customerId){
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation_system", "root", "1234");
                 PreparedStatement ps = connection.prepareStatement(
                         "UPDATE Customer SET CustName=?, Email=?, PhoneNo=?, CustAddress=?, NumberOfGuests=? WHERE CustID=?"
                 )) {

                ps.setString(1,customerName);
                ps.setString(2,email);
                ps.setString(3,phoneNO);
                ps.setString(4,address);
                ps.setInt(5,numberOfGuests);
                ps.setString(6,customerId);

                int rows = ps.executeUpdate();
                if (rows > 0) {

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ObservableList<CustomerDetails> gettAllCustomerDetails(){

            ObservableList<CustomerDetails> customerInfo = FXCollections.observableArrayList();

            try (Connection connection = DBConnection.getInstance().getConnection();
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

                while (rs.next()) {
                    CustomerDetails customers = new CustomerDetails(
                            rs.getString("CustID"),
                            rs.getString("CustName"),
                            rs.getString("Email"),
                            rs.getString("PhoneNo"),
                            rs.getString("CustAddress"),
                            rs.getInt("NumberOfGuests")
                    );
                    customerInfo.add(customers);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customerInfo;
        }

}
