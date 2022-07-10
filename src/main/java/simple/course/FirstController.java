//package simple.course;
//
//import java.net.URL;
//import java.sql.*;
//import java.util.ResourceBundle;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//
//
//public class FirstController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private Button OnButton;
//
//    @FXML
//    void initialize() {
//        Connect connect = new Connect();
//
//        OnButton.setOnAction(actionEvent -> {
//            try {
//                Statement statement = connect.getConnect().createStatement();
//                statement.execute("INSERT INTO " + USERS_TABLE + "(" + USERS_NAME +", " + USERS_LOGIN + ", " + USERS_PASSWORD + ") " +
//                        "VALUES ('orscl', 'admin', 'admin')");
//            }
//            catch (Exception e){
//                System.out.println(e);
//            }
//        });
//    }

//}