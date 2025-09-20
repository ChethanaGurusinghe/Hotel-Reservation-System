package controller.roomController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RoomDetails;

import java.sql.*;

public class RoomManagementController implements RoomManagementService{

    @Override
    public void addRoomDetails(String roomId,String type,Double pricePerNight,Integer maxGuests,Boolean available,String description,Integer floor){
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO rooms (room_id, type, price_per_night, max_guests, availability, description, floor) VALUES (?, ?, ?, ?, ?, ?, ?)"
             )) {

            ps.setString(1,roomId);
            ps.setString(2, type);
            ps.setDouble(3, pricePerNight);
            ps.setInt(4, maxGuests);
            ps.setBoolean(5, available);
            ps.setString(6, description);
            ps.setInt(7, floor);

            int rows = ps.executeUpdate();
            if (rows > 0) {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoomDetails(String roomId){

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM rooms WHERE room_id=?")) {

            ps.setString(1, roomId);

            int rows = ps.executeUpdate();
            if (rows > 0) {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateRoomDetails(String type,Double pricePerNight,Integer maxGuests,Boolean availability,String description,Integer floor,String roomId){

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE rooms SET type=?, price_per_night=?, max_guests=?, availability=?, description=?, floor=? WHERE room_id=?"
             )) {

            ps.setString(1,type);
            ps.setDouble(2, pricePerNight);
            ps.setInt(3, maxGuests);
            ps.setBoolean(4, availability); // can later make dynamic
            ps.setString(5, description);
            ps.setInt(6, floor);
            ps.setString(7, roomId);

            int rows = ps.executeUpdate();
            if (rows > 0) {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<RoomDetails> getAllRoomDetails(){

        ObservableList<RoomDetails> roomInfo = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation_system", "root", "1234");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {

            while (rs.next()) {
               RoomDetails rooms = new RoomDetails(
                        rs.getString("room_id"),
                        rs.getString("type"),
                        rs.getDouble("price_per_night"),
                        rs.getInt("max_guests"),
                        rs.getBoolean("availability"),
                        rs.getString("description"),
                        rs.getInt("floor")
                );
               roomInfo.add(rooms);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomInfo;
    }

}
