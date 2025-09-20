package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormConrtoller {

    @FXML
    private Button btnCustomerManagemnt;

    @FXML
    private Button btnRoomManagement;

    Stage roomManagement = new Stage();
    Stage customerManagement = new Stage();

    @FXML
    void btnCustomerManagementOnAction(ActionEvent event) {
        try {
            customerManagement.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customerManagement.show();
    }

    @FXML
    void btnRoomManagemntOnAction(ActionEvent event) {
        try {
            roomManagement.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/RoomManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        roomManagement.show();
    }

}
