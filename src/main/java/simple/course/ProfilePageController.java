package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfilePageController extends Connect{

    private String actualLogin;
    private int actualId;

    @FXML
    private Label actual_login;

    @FXML
    private Button addLawButton;

    @FXML
    private Button changeLoginButton;

    @FXML
    private Button changeNameButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField tfIdPhrase;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfLoginNewUser;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPass;

    @FXML
    void handleButtonAction(ActionEvent event) {
//        actual_login.setText(const_user);
        if (event.getSource() == returnButton){
            try {
                returnButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if (event.getSource() == changeNameButton){
            if(!tfName.equals("")){
                updateUser("Users", "name", tfName);
            }
        }

        else if (event.getSource() == changeLoginButton){
            if(!tfLogin.equals("")) {
                updateUser("Users", "login", tfLogin);
            }
        }

        else if (event.getSource() == changePasswordButton){
            if(!tfName.equals("")){
                updateUser("Users", "password", tfPass);
            }
        }

        else if (event.getSource() == addLawButton){
            insertLaw();
        }
        //ТУТ ОСТАЛИСЬ ПРАВА
    }

    public void updateUser(String base, String column, TextField tf){
        String query = "UPDATE " + base + " SET " + column + " = '" + tf.getText() + "' WHERE login = " + actual_login;
        executeQuery(query);

    }

    public void insertLaw() {
        String query = "INSERT INTO Laws(user_id, phrase_id) VALUES('" + tfLoginNewUser.getText() + "','" + tfIdPhrase.getText() + "')";
        executeQuery(query);
    }

    private void executeQuery(String query) {
        Connection conn = getConnect();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        actualLogin = User.actualLogin;
        actualId = User.actualId;

        actual_login.setText(actualLogin);
    }

}
