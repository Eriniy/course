package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LogInController extends Connect {
//    public String const_user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private TextField tfLogLogin;

    @FXML
    private PasswordField psPassword;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == logInButton){
            if(!tfLogLogin.getText().equals("") && !psPassword.getText().equals("")){
                try {
                    String check = "SELECT COUNT(*) as count FROM Users WHERE login=? AND password=?";

                    if (getCheckPassLogin(check) == 1){ //чекаем на совпадение пары в бд
                        User.actualLogin = tfLogLogin.getText(); // актуальный логин
                        User.actualId = getActualId(); // актулаьный id

                        logInButton.getScene().getWindow().hide(); // убирает прошлое окно

                        Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        psPassword.clear();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Не заполнены обязательные поля!");
                alert.showAndWait();
            }
        }

    }

    private int getCheckPassLogin(String str) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(str);
        pr.setString(1, tfLogLogin.getText());
        pr.setString(2, psPassword.getText());
        ResultSet resultSet = pr.executeQuery();
        int check = 0;

        if (resultSet.next()){
            check = resultSet.getInt("count");
        }

        return check;

    }

    private int getActualId() throws SQLException {
        Connection conn = getConnect();
        String str = "SELECT id FROM Users WHERE login=?";
        PreparedStatement pr = conn.prepareStatement(str);
        pr.setString(1, tfLogLogin.getText());

        ResultSet resultSet = pr.executeQuery();
        int actualId = 0;

        if (resultSet.next()){
            actualId = resultSet.getInt("id");
        }

        return actualId;

    }

    @FXML
    void initialize() {

    }
}
