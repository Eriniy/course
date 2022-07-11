package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController extends Connect{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField tfPass;

    @FXML
    private Button returnButton;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfName;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == signUpButton){
            String login = tfLogin.getText().trim();
            String password = tfPass.getText().trim();
            String name = tfName.getText().trim();

            if (!login.equals("") && !password.equals("") && !name.equals("")){
                try {
                    parameterExecute();

                    signUpButton.getScene().getWindow().hide(); // убирает прошлое окно

                    Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Не заполнены обязательные поля!");
                alert.showAndWait();
            }
        }else if(event.getSource() == returnButton){
            try {
                returnButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void parameterExecute() throws SQLException {
        Connection conn = getConnect();
        String in = "INSERT INTO Users(name, login, password) VALUES (?,?,?)";
        PreparedStatement pr = conn.prepareStatement(in);
        pr.setString(1, tfName.getText());
        pr.setString(2, tfLogin.getText());
        pr.setString(3, tfPass.getText());

        pr.executeUpdate();
    }

    @FXML
    void initialize() {

    }
}
